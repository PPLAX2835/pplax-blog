<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="角色名">
        <el-input style="width: 150px" size="small" v-model="params.keyword" placeholder="请输入角色名"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleFind">查找</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
        <el-button v-if="canAdd" type="primary" icon="el-icon-plus" size="small" @click="handleCreate">新增</el-button>
        <el-button v-if="canDeleteBatch" :disabled="!multipleSelection.length" type="danger" icon="el-icon-delete" size="small"
                   @click="handleDelete">批量删除
        </el-button>
      </el-form-item>

    </el-form>

    <!-- 表格区域 -->
    <div style="margin-top: 5px">
      <el-table border :data="tableData" style="width: 100%" :default-sort="{ prop: 'sort', order: 'descending' }"
                @selection-change="handleSelectionChange">
        <el-table-column align="center" type="selection" />
        <el-table-column width="180" align="center" prop="roleName" label="角色名称"></el-table-column>
        <el-table-column width="180" align="center" prop="summary" label="介绍"></el-table-column>

        <el-table-column width="180" align="center" label="添加时间">
          <template slot-scope="scope">
            <span>{{ timeFormat(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column width="180" align="center" label="更新时间">
          <template slot-scope="scope">
            <span>{{ timeFormat(scope.row.updateTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column width="250" fixed="right" align="center" label="操作" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button v-if="canUpdate" type="primary" size="mini" @click="handleUpdate(scope)">编辑</el-button>
            <el-button v-if="canUpdate" type="warning" size="mini" @click="handlePermission(scope)">权限</el-button>
            <el-button v-if="canDelete" size="mini" type="danger" @click="handleDelete(scope)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--分页区域-->
    <div class="pagination-container" style="float: right;margin-bottom: 1.25rem;margin-top: 1.25rem;">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="params.currentPage" :page-size="params.pageSize" :page-sizes="[10, 20, 30]"
                     layout="total, sizes,prev, pager, next,jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 编辑弹窗 -->
    <el-dialog center :title="title" :visible.sync="dialogFormVisible" :fullscreen="isFullScreen">
      <el-form :rules="rules" ref="dataForm" :model="form">
        <div v-if="!isEditMenu">
          <el-form-item prop="roleName" label="角色名" :label-width="formLabelWidth">
            <el-input  v-model="form.roleName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="summary" label="介绍" :label-width="formLabelWidth">
            <el-input  v-model="form.summary" autocomplete="off"></el-input>
          </el-form-item>
        </div>
        <div v-else >
          <el-table
            :data="menuTree"
            style="width: 100%"
            row-key="uid"
            ref="menuTable"
            @selection-change="handleMenuSelectionChange"
            @select="handleMenuSelection"
            @select-all="handleMenuSelectAll"
          >
            <el-table-column
              type="selection"
              width="55"></el-table-column>

            <el-table-column prop="title" label="菜单名" width="250"></el-table-column>

            <el-table-column width="50" align="center" label="图标">
              <template slot-scope="scope">
                <i :class="scope.row.icon"></i>
              </template>
            </el-table-column>
            <el-table-column width="180" align="center" label="类型">
              <template slot-scope="scope">
                <el-tag type="success" v-if="scope.row.type === 'menu'">菜单</el-tag>
                <el-tag type="warning" v-else-if="scope.row.type === 'button'">按钮</el-tag>
              </template>
            </el-table-column>

            <el-table-column prop="route" label="路由" width="250"></el-table-column>
            <el-table-column prop="endpoint" label="请求" width="250"></el-table-column>
          </el-table>
        </div>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { getRoleList, updateRole, addRole, deleteRoleBatch, deleteRole } from "../../api/role";
import { getMenuTree } from "../../api/menu";
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import IconPicker from "../../components/IconPicker"
import { EStatus } from "../../base/EStatus"
import { mapGetters } from "vuex";

export default {
  components: {
    IconPicker
  },

  data() {
    return {
      multipleSelection: [],
      multipleMenuSelection: [],
      showSearch: true,
      isFullScreen: false,
      params: {
        keyword: '',
        currentPage: 1,
        pageSize: 10
      },
      statusList: [],
      // 编辑/添加表单用到的
      formLabelWidth: '120px',
      isEditForm: false,
      isEditMenu: false,
      dialogFormVisible: false,
      title: '',
      editingRoleUid: '',
      form: {
        menuUids: '',
        roleName: '',
        summary: ''
      },
      rules: {
        roleName: [
          { required: true, message: '请输入角色名', trigger: 'blur' },
          { min: 1, max: 20, message: '长度限制在1到20之间', trigger: 'change' }
        ],
        summary: [
          { min: 1, max: 50, message: '长度限制在1到50之间', trigger: 'change' }
        ]
      },
      // 数据总数
      total:0,
      // 加载层信息
      loading: [],
      tableData: [],
      menuTree: [],
      isMenuFullSelect: false
    }
  },
  computed: {
    ...mapGetters([
      'menu'
    ]),
    /**
     * 检查是否有批量删除的权限
     * @returns {boolean|*}
     */
    canDeleteBatch: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/role')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/role/{uid}')
    },
    /**
     * 检查是否有添加的权限
     * @returns {boolean|*}
     */
    canAdd: function () {
      return hasAuth(this.menu, 'POST:/api/admin/role')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/role/{uid}')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchRoleList();
    this.fetchMenuTree();
  },
  methods: {
    fetchRoleList: function (){
      getRoleList(this.params).then(res =>{
        this.tableData = res.data
        this.total = res.total
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    fetchMenuTree: function (){
      getMenuTree().then(res =>{
        this.menuTree = res.data
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    /**
     * 查找按钮点击事件
     */
    handleFind: function () {
      this.params.currentPage = 1;
      this.fetchRoleList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.keyword=''
      this.fetchRoleList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchRoleList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchRoleList()
    },
    /**
     * 时间戳格式化
     */
    timeFormat(timestamp) {
      return parseTime(timestamp);
    },
    /**
     * 处理复选框选择事件
     * @param val
     */
    handleSelectionChange: function (val) {
      this.multipleSelection = val;
    },



    /**
     * 处理菜单复选框选择事件
     * @param val
     */
    handleMenuSelectionChange: function (val) {
      this.multipleMenuSelection = val;
    },
    /**
     * 处理用户手动选中事件
     * @param selection
     * @param row
     */
    handleMenuSelection: function (selection, row) {
      let selected = (selection.length > 0) && selection.indexOf(row) !== -1; //为true时选中，为false未选中
      if (!selected && this.isMenuFullSelect) {
        this.isMenuFullSelect = false
      }
      this.menuRecursionSelection(row, selected)
    },
    /**
     * 处理菜单全选事件
     * @param selection
     */
    handleMenuSelectAll: function (selection) {

      if (!this.isMenuFullSelect) {
        this.$refs['menuTable'].clearSelection()
        for (let i = 0; i < this.menuTree.length; i++) {
          this.menuRecursionSelection(this.menuTree[i], true)
        }
        this.isMenuFullSelect = true
      } else {
        this.$refs['menuTable'].clearSelection()
        this.isMenuFullSelect = false
      }

    },
    /**
     * 递归向子菜单选择
     * @param row
     * @param flag
     */
    menuRecursionSelection: function (row, flag) {

      // 如果是数组，循环递归
      if (row instanceof Array) {
        for (let i = 0; i < row.length; i++) {
          this.menuRecursionSelection(row[i], flag)
        }
      }

      // 选中或反选
      this.$refs['menuTable'].toggleRowSelection(row, flag)

      // 判断是否还有子树
      if (!(row.children === undefined || row.children.length <= 0)) {
        this.menuRecursionSelection(row.children, flag)
      }

    },





    /**
     * 打开加载层
     */
    openLoading: function () {
      this.loading = this.$loading({
        lock: true,
        text: "正在加载中~",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
    },
    /**
     * 设置编辑框 是编辑还是添加
     * @param title
     * @param isEditForm
     */
    beforeShow: function (title, isEditForm) {
      this.title = title
      this.isEditForm = isEditForm

      this.dialogFormVisible = true
    },
    /**
     * 添加按钮的点击事件
     */
    handleCreate: function () {
      this.editingRoleUid = ''
      this.form.summary = ''
      this.form.roleName = ''
      this.form.menuUids = ''

      this.isFullScreen = false;
      this.isEditMenu = false;
      this.beforeShow("添加角色", 0)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /**
     * 权限按钮点击事件
     * @param scope
     */
    handlePermission: function (scope) {
      this.editingRoleUid = scope.row.uid
      this.form.summary = scope.row.summary
      this.form.roleName = scope.row.roleName
      this.form.menuUids = scope.row.menuUids

      this.isFullScreen = true;
      this.isEditMenu = true;
      this.beforeShow("权限管理", 1)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })

      // 设置默认选中的选项，这里这样写
      setTimeout(() => {
        this.$refs['menuTable'].clearSelection()
        if (scope.row.menuUids !== undefined) {

          let menuUidArray = scope.row.menuUids.split(',')

          let result = [];

          function traverse(menu, uidArray) {
            if (uidArray.includes(menu.uid)) {
              result.push(menu);
            }

            if (menu.children && menu.children.length > 0) {
              menu.children.forEach(child => traverse(child, uidArray));
            }
          }

          this.menuTree.forEach(menu => traverse(menu, menuUidArray));

          result.forEach(menu => this.$refs['menuTable'].toggleRowSelection(menu))
        }
      }, 0)
    },

    /**
     * 编辑按钮点击事件
     * @param scope
     */
    handleUpdate: function (scope) {
      this.editingRoleUid = scope.row.uid
      this.form.summary = scope.row.summary
      this.form.roleName = scope.row.roleName
      this.form.menuUids = scope.row.menuUids

      this.isFullScreen = false;
      this.isEditMenu = false;
      this.beforeShow("编辑角色", 1)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /**
     * 处理删除按钮的点击事件
     * @param scope
     */
    handleDelete: function (scope) {
      this.$confirm('是否确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.openLoading();

        if (scope.row === undefined) {

          // 走的是批量删除
          if (this.multipleSelection.length) {
            let selections = this.multipleSelection;
            let uids = [selections.length]
            for (let i = 0; i < selections.length; i++) {
              uids[i] = selections[i].uid
            }
            deleteRoleBatch(uids).then(res => {
              this.fetchRoleList()
              this.$message.success('删除成功');
              this.loading.close()
            }).catch(() => {
              this.loading.close()
            });
          }
        } else {
          // 走单独删除
          deleteRole(scope.row.uid).then(res => {
            this.fetchRoleList()
            this.$message.success('删除成功');
            this.loading.close()
          }).catch(() => {
            this.loading.close()
          });

        }
      }).catch(() => {
        this.loading.close()
      });

    },

    /**
     * 获得选中项的uid
     */
    getMenuSelectionUids: function (node) {
      // 初始化一个空数组来存储uid
      let uids = [];

      // 检查节点是否存在uid属性
      if (node.uid) {
        // 如果存在，添加到数组中
        uids.push(node.uid);
      }

      // 检查节点是否存在children数组
      if (Array.isArray(node.children)) {
        // 对每个子节点递归调用extractUIDs函数
        // 并将返回的uid数组合并到当前数组的末尾
        node.children.forEach(child => {
          uids = uids.concat(this.getMenuSelectionUids(child));
        });
      }

      // 返回存储了所有uid的数组
      return uids;
    },

    submit: function () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          let menuUids = []
          this.multipleMenuSelection = this.$refs['menuTable'].selection
          for (let i = 0; i < this.multipleMenuSelection.length; i++) {
            menuUids = menuUids.concat(this.getMenuSelectionUids(this.multipleMenuSelection[i]))
          }
          this.form.menuUids = menuUids.toString()

          if (this.isEditForm) {
            updateRole(this.editingRoleUid, this.form).then(res => {
              this.$message.success("修改成功")
              this.editingRoleUid = ''
              this.fetchRoleList()
              this.dialogFormVisible = false;
              this.close()
            }).catch(err => {
              console.error(err)
            })
          } else {
            addRole(this.form).then(res => {
              this.$message.success("添加成功")
              this.fetchRoleList()
              this.dialogFormVisible = false;
              this.close()
            }).catch(err => {
              console.error(err)
            })
          }

        } else {
          console.log('error submit!!');
          return false;
        }
      })
    }
  }
}
</script>
