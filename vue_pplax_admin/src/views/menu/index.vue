<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="角色名">
        <el-input style="width: 150px" size="small" v-model="params.keyword" placeholder="请输入角色名"/>
      </el-form-item>
      <el-form-item>
        <el-button v-if="canAdd" type="primary" icon="el-icon-plus" size="small" @click="handleCreate">新增</el-button>
        <el-button v-if="canDeleteBatch" :disabled="!multipleSelection.length" type="danger" icon="el-icon-delete" size="small"
                   @click="handleDelete">批量删除
        </el-button>
      </el-form-item>

    </el-form>

    <!-- 表格区域 -->
    <div style="margin-top: 5px">

      <el-table
        :data="tableData"
        style="width: 100%"
        row-key="uid"
      >

        <el-table-column prop="sortNo" label="排序" width="80" sortable></el-table-column>
        <el-table-column width="50" align="center" label="图标">
          <template slot-scope="scope">
            <i :class="scope.row.icon"></i>
          </template>
        </el-table-column>

        <el-table-column prop="title" label="菜单名" width="250"></el-table-column>

        <el-table-column width="180" align="center" label="类型">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.type === 'menu'">菜单</el-tag>
            <el-tag type="warning" v-else-if="scope.row.type === 'button'">按钮</el-tag>
          </template>
        </el-table-column>

        <el-table-column width="180" align="center" label="菜单级别">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.level === 1">一级菜单</el-tag>
            <el-tag type="warning" v-else-if="scope.row.level === 2">二级菜单</el-tag>
            <el-tag type="danger" v-else-if="scope.row.level === 3">三级菜单</el-tag>
            <el-tag type="info" v-else-if="scope.row.level === 4">四级菜单</el-tag>
          </template>
        </el-table-column>

        <el-table-column width="180" align="center" label="是否隐藏">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.hidden">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="route" label="路由" width="250"></el-table-column>
        <el-table-column prop="endpoint" label="请求" width="250"></el-table-column>
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
    <el-dialog center :title="title" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" ref="dataForm" :model="form">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { getMenuTree } from "../../api/menu";
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import { EStatus } from "../../base/EStatus"
import { mapGetters } from "vuex";

export default {
  components: {
  },

  data() {
    return {
      multipleSelection: [],
      showSearch: true,
      params: {
        keyword: '',
        currentPage: 1,
        pageSize: 10
      },
      statusList: [],
      // 编辑/添加表单用到的
      formLabelWidth: '120px',
      isEditForm: false,
      dialogFormVisible: false,
      title: '',
      editingRoleUid: '',
      form: {
        level: '',
        name: ''
      },
      rules: {
        level: [
          { required: true, message: '请输入推荐等级', trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入标签名', trigger: 'blur' },
          { min: 1, max: 20, message: '标签名长度限制在1到20之间', trigger: 'change' }
        ]
      },
      // 数据总数
      total:0,
      // 加载层信息
      loading: [],
      tableData: []
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
      return hasAuth(this.menu, 'DELETE:/api/admin/tag')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/tag/{uid}')
    },
    /**
     * 检查是否有添加的权限
     * @returns {boolean|*}
     */
    canAdd: function () {
      return hasAuth(this.menu, 'POST:/api/admin/tag')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/tag/{uid}')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchMenuTree();
  },
  methods: {
    fetchMenuTree: function (){
      getMenuTree(this.params).then(res =>{
        this.tableData = res.data
        this.total = res.total
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchMenuTree()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchMenuTree()
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

      this.beforeShow("添加用户", 0)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /**
     * 编辑按钮点击事件
     * @param scope
     */
    handleUpdate: function (scope) {
      this.editingRoleUid = scope.row.uid


      this.beforeShow("修改分类", 1)
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
            // deleteTagBatch(uids).then(res => {
            //   this.fetchMenuTree()
            //   this.$message.success('删除成功');
            //   this.loading.close()
            // }).catch(() => {
            //   this.loading.close()
            // });
          }
        } else {
          // 走单独删除
          // deleteTag(scope.row.uid).then(res => {
          //   this.fetchMenuTree()
          //   this.$message.success('删除成功');
          //   this.loading.close()
          // }).catch(() => {
          //   this.loading.close()
          // });

        }
      }).catch(() => {
        this.loading.close()
      });

    },

    submit: function () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.isEditForm) {
            // updateTag(this.editingTagUid, this.form).then(res => {
            //   this.$message.success("修改成功")
            //   this.editingTagUid = ''
            //   this.fetchMenuTree()
            //   this.dialogFormVisible = false;
            //   this.close()
            // }).catch(err => {
            //   console.error(err)
            // })
          } else {
            // addTag(this.form).then(res => {
            //   this.$message.success("添加成功")
            //   this.fetchMenuTree()
            //   this.dialogFormVisible = false;
            //   this.close()
            // }).catch(err => {
            //   console.error(err)
            // })
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
