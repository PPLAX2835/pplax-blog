<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form :inline="true" ref="form" label-width="68px">
      <el-form-item>
        <el-button v-if="canAdd" type="primary" icon="el-icon-plus" size="small" @click="handleCreate">新增一级菜单</el-button>
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

        <el-table-column prop="title" label="菜单名" width="250"></el-table-column>

        <el-table-column width="50" align="center" label="图标">
          <template slot-scope="scope">
            <i :class="scope.row.icon"></i>
          </template>
        </el-table-column>
        <el-table-column width="100" align="center" label="类型">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.type === 'menu'">菜单</el-tag>
            <el-tag type="warning" v-else-if="scope.row.type === 'button'">按钮</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="路由/请求" width="250">
          <template slot-scope="scope">
            <span v-if="scope.row.route">{{ scope.row.route }}</span>
            <span v-else>
              <el-tag>
                {{ scope.row.endpoint.split(':')[0] }}
              </el-tag>
                {{ scope.row.endpoint.split(':')[1] }}
            </span>
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

        <el-table-column width="300" fixed="right" align="center" label="操作" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button v-if="canAdd" type="primary" size="mini" @click="handleCreate(scope)">添加子菜单</el-button>
            <el-button v-if="canUpdate" type="primary" size="mini" @click="handleUpdate(scope)">编辑</el-button>
            <el-button v-if="canDelete" size="mini" type="danger" @click="handleDelete(scope)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <!-- 编辑弹窗 -->
    <el-dialog center :title="title" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" ref="dataForm" :model="form">
        <el-form-item prop="icon" label="图标">
          <IconPicker v-model="form.icon"></IconPicker>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select style="width: 130px" size="small" v-model="form.type" placeholder="请选择分类">
            <el-option label="菜单" value="menu">菜单</el-option>
            <el-option label="按钮" value="button">按钮</el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.type === 'menu'" label="路由" prop="route">
          <el-input auto-complete="off" v-model="form.route" placeholder="请输入路由"></el-input>
        </el-form-item>
        <el-form-item v-else-if="form.type === 'button'" label="请求" prop="endpoint">
          <el-input auto-complete="off" v-model="path" @change="covertToEndpoint" class="input-with-select" placeholder="请输入路径">
            <el-select v-model="method" slot="prepend" @change="covertToEndpoint" placeholder="请求方式">
              <el-option label="GET" value="GET"></el-option>
              <el-option label="POST" value="POST"></el-option>
              <el-option label="PUT" value="PUT"></el-option>
              <el-option label="DELETE" value="DELETE"></el-option>
              <el-option label="HEAD" value="HEAD"></el-option>
              <el-option label="CONNECT" value="CONNECT"></el-option>
              <el-option label="OPTIONS" value="OPTIONS"></el-option>
              <el-option label="TRACE" value="TRACE"></el-option>
              <el-option label="PATCH" value="PATCH"></el-option>
            </el-select>
          </el-input>
        </el-form-item>
        <el-form-item label="菜单名" prop="title">
          <el-input auto-complete="off" v-model="form.title" placeholder="请输入菜单名"></el-input>
        </el-form-item>
        <el-form-item prop="sortNo" label="排序">
          <el-input @input="convertToNum()" v-model="form.sortNo" autocomplete="off" placeholder="请输入排序"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input auto-complete="off" v-model="form.remarks" placeholder="请输入备注"></el-input>
        </el-form-item>
        <el-form-item label="是否隐藏" prop="hidden">
            <el-select v-model="form.hidden" placeholder="是否隐藏">
              <el-option label="是" :value="true"></el-option>
              <el-option label="否" :value="false"></el-option>
            </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { getMenuTree, updateMenu, addMenu, deleteMenu } from "../../api/menu";
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import { EStatus } from "../../base/EStatus"
import IconPicker from "../../components/IconPicker"
import { mapGetters } from "vuex";

export default {
  components: {
    IconPicker
  },

  data() {
    return {
      multipleSelection: [],
      statusList: [],
      // 编辑/添加表单用到的
      formLabelWidth: '120px',
      isEditForm: false,
      dialogFormVisible: false,
      title: '',
      editingMenuUid: '',
      path: '',
      method: '',
      form: {
        parentUid: '',
        type: '',
        route: '',
        endpoint: '',
        title: '',
        level: '',
        sortNo: '',
        icon: '',
        remarks: '',
        hidden: ''
      },
      rules: {
        type: [
          { required: true, message: '请选择类型', trigger: 'blur' },
        ],
        title: [
          { required: true, message: '请输入菜单名', trigger: 'blur' },
          { min: 1, max: 20, message: '菜单名长度限制在1到20之间', trigger: 'change' }
        ],
        route: [
          { required: true, validator: this.menuValidator, trigger: 'blur' },
        ],
        endpoint: [
          { required: true, validator: this.menuValidator, trigger: 'blur' },
        ],
        remarks: [
          { min: 0, max: 20, message: '备注长度限制在1到20之间', trigger: 'change' }
        ],
        hidden: [
          { required: true, message: '请选择是否隐藏', trigger: 'blur' },
        ],

      },
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
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/menu/*')
    },
    /**
     * 检查是否有添加的权限
     * @returns {boolean|*}
     */
    canAdd: function () {
      return hasAuth(this.menu, 'POST:/api/admin/menu')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/menu/*')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchMenuTree();
  },
  methods: {
    fetchMenuTree: function (){
      getMenuTree().then(res =>{
        this.tableData = res.data
        this.total = res.total
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },

    menuValidator: function (rule, value, callback) {
      if (this.form.type === 'menu') {
        if (this.form.route.trim() === '') {
          callback(new Error('请输入路由'))
        } else if (this.form.route.length < 0 || this.form.route.length > 255) {
          callback(new Error('路由过长'))
        } else {
          callback()
        }
      } else if (this.form.type === 'button') {
        if (this.method === '' || this.path.trim() === '') {
          callback(new Error('请输入请求地址'))
        } else if (this.path.length < 0 || this.path.length > 255) {
          callback(new Error('地址过长'))
        } else {
          callback()
        }
      }
    },
    /**
     * 时间戳格式化
     */
    timeFormat(timestamp) {
      return parseTime(timestamp);
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
     * 将path 和 method 的值放到endpoint中
     */
    covertToEndpoint: function () {
      if (this.method !== '' && this.path !== '') {
        this.form.endpoint = this.method + ':' + this.path
      }
    },
    /**
     * 剔除非数字的值
     */
    convertToNum() {
      this.form.sortNo = this.form.sortNo.replace(/[^\d]/g,'')
      this.form.sortNo = this.form.sortNo.trim() === '' ? 0: parseInt(this.form.sortNo)
    },
    /**
     * 添加按钮的点击事件
     */
    handleCreate: function (scope) {
      this.editingMenuUid = ''
      this.form.parentUid = (scope.row === undefined ? '' : scope.row.uid)
      this.form.type = ''
      this.form.route = ''
      this.form.endpoint = ''
      this.form.title = ''
      this.form.level = (scope.row === undefined ? 1 : scope.row.level + 1)
      this.form.sortNo = ''
      this.form.icon = ''
      this.form.remarks = ''
      this.form.hidden = ''
      this.form.name = ''

      console.log(scope.row)
      console.log(this.form.parentUid)

      this.method = ''
      this.path = ''

      this.beforeShow("添加菜单", 0)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /**
     * 编辑按钮点击事件
     * @param scope
     */
    handleUpdate: function (scope) {
      this.editingMenuUid = scope.row.uid
      this.form.parentUid = scope.row.parentUid
      this.form.type = scope.row.type
      this.form.route = scope.row.route
      this.form.endpoint = scope.row.endpoint
      this.form.title = scope.row.title
      this.form.level = scope.row.level
      this.form.sortNo = scope.row.sortNo
      this.form.icon = scope.row.icon
      this.form.remarks = scope.row.remarks
      this.form.hidden = scope.row.hidden
      this.form.name = scope.row.name

      if (scope.row.type === 'button') {
        let temp = scope.row.endpoint.split(":")
        this.method = temp[0]
        this.path = temp[1]
      }

      this.beforeShow("修改菜单", 1)
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
        // 走单独删除
        deleteMenu(scope.row.uid).then(res => {
          this.fetchMenuTree()
          this.$message.success('删除成功');
          this.loading.close()
        }).catch(() => {
          this.loading.close()
        });
      }).catch(() => {
        this.loading.close()
      });

    },

    submit: function () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.isEditForm) {
            updateMenu(this.editingMenuUid, this.form).then(res => {
              this.$message.success("修改成功")
              this.editingMenuUid = ''
              this.fetchMenuTree()
              this.dialogFormVisible = false;
              this.close()
            }).catch(err => {
              console.error(err)
            })
          } else {
            addMenu(this.form).then(res => {
              this.$message.success("添加成功")
              this.fetchMenuTree()
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

<style>
.el-select .el-input {
  width: 130px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>
