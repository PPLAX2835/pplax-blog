<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="分类名">
        <el-input style="width: 150px" size="small" v-model="params.keyword" placeholder="请输入分类名"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleFind">查找</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
<!--        <el-button v-if="canAdd()" type="primary" icon="el-icon-plus" size="small" @click="handleCreate">新增</el-button>-->
<!--        <el-button v-if="canDeleteBatch()" :disabled="!multipleSelection.length" type="danger" icon="el-icon-delete" size="small"-->
<!--                   @click="handleDelete">批量删除-->
<!--        </el-button>-->
      </el-form-item>

    </el-form>

    <!-- 表格区域 -->
    <div style="margin-top: 5px">
      <el-table border :data="tableData" style="width: 100%" :default-sort="{ prop: 'sort', order: 'descending' }"
                @selection-change="handleSelectionChange">
        <el-table-column align="center" type="selection" />
        <el-table-column align="center" prop="sort" sortable width="80" label="排序">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.sortNo }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="50" align="center" label="图标">
          <template slot-scope="scope">
            <i :class="scope.row.icon"></i>
          </template>
        </el-table-column>
        <el-table-column prop="sortName" align="center" label="分类名" width="130" />
        <el-table-column prop="summary" align="center" label="介绍" width="180" />
        <el-table-column prop="cites" align="center" label="引用量" width="120" />
        <el-table-column align="center" prop="clickCount" label="点击量" width="120">
          <template slot-scope="scope">
            <el-tag type="warning">{{ scope.row.clickCount }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="status" label="状态" width="80">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === statusList.ENABLE" type="success">正常</el-tag>
            <el-tag v-else-if="scope.row.status === statusList.LOCKED" type="warning">锁定</el-tag>
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
        <el-table-column width="220" align="center" label="操作" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button v-if="canPromote()" type="warning" size="mini" @click="handlePromote(scope)">置顶</el-button>
<!--            <el-button v-if="canUpdate" type="primary" size="mini" @click="handleEdit(scope)">编辑</el-button>-->
<!--            <el-button v-if="canDel" size="mini" type="danger" @click="remove(scope)">删除-->
<!--            </el-button>-->
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


  </div>
</template>

<script>
import { getBlogSortList } from "../../api/blogSort";
import { getRoleList } from "../../api/role"
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import { EStatus } from "../../base/EStatus"
import { mapGetters } from "vuex";

export default {

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
      form: {
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { validator: this.isExist, trigger: 'change'},
          { min: 3, max: 30, message: '长度在3到30个字符' },
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
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchBlogSortList();
    this.fetchRoleList();
  },
  methods: {
    fetchBlogSortList: function (){
      getBlogSortList(this.params).then(res =>{
        this.tableData = res.data
        this.total = res.total
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    fetchRoleList: function () {
      getRoleList(this.params).then(res =>{
        this.roleList = res.data
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    /**
     * 判断是否可以使用指定按钮
     * @returns {boolean|*}
     */
    canPromote: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/blog/{uid}/promote')
    },
    /**
     * 查找按钮点击事件
     */
    handleFind: function () {
      this.params.currentPage = 1;
      this.fetchBlogSortList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.keyword=''
      this.fetchBlogSortList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchBlogSortList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchBlogSortList()
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
     * 检查是否有批量删除的权限
     * @returns {boolean|*}
     */
    canDeleteBatch: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/user')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/user/{uid}')
    },
    /**
     * 检查是否有添加的权限
     * @returns {boolean|*}
     */
    canAdd: function () {
      return hasAuth(this.menu, 'POST:/api/admin/user')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/user/{uid}/userInfo')
    },
    /**
     * 添加按钮的点击事件
     */
    handleCreate: function () {
      this.form.avatarPictureUid = ''

      this.beforeShow("添加用户", 0)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handlePromote: function (scope) {
      this.$confirm('此操作将会把该分类放到首位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消置顶'
        })
      })
    },
    /**
     * 编辑按钮点击事件
     * @param scope
     */
    handleUpdate: function (scope) {



      this.beforeShow("修改用户", 1)
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



          }
        } else {
          // 走单独删除

        }
      }).catch(() => {
        this.loading.close()
      });

    },

    submit: function () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.isEditForm) {


          } else {


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
