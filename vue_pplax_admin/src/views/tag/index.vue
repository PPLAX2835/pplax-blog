<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="标签名">
        <el-input style="width: 150px" size="small" v-model="params.keyword" placeholder="请输入标签名"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleFind">查找</el-button>
        <el-button type="info" icon="el-icon-document" size="small" @click="handleSortByClickCount">点击量排序</el-button>
        <el-button type="info" icon="el-icon-document" size="small" @click="handleSortByCites">引用量排序</el-button>
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
        <el-table-column prop="name" align="center" label="标签名" width="130" />
        <el-table-column prop="clickCount" align="center" label="点击量" width="130" />
        <el-table-column prop="cites" align="center" label="引用量" width="130" />
        <el-table-column align="center" prop="sort" sortable width="80" label="推荐等级">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.level }}</el-tag>
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
        <el-table-column width="200" align="center" label="操作" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button v-if="canUpdate" type="primary" size="mini" @click="handleUpdate(scope)">编辑</el-button>
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
    <el-dialog center :title="title" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" ref="dataForm" :model="form">
        <el-form-item prop="sortNo" label="排序" :label-width="formLabelWidth">
          <el-input onkeyup="value=(value.trim()===''?0:parseInt(value.replace(/[^\d]/g,0)).toString())" v-model="form.sortNo" autocomplete="off"></el-input>
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
import {addBlogSort, getBlogSortList, promote, promoteCancel, updateBlogSort, deleteBlogSort, deleteBlogSortBatch } from "../../api/blogSort";
import { getTagList } from "../../api/tag"
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import IconPicker from "../../components/IconPicker"
import { EStatus } from "../../base/EStatus"
import { mapGetters } from "vuex";
import {addUser, deleteUserBatch, updateUserInfo} from "../../api/user";

export default {
  components: {
    IconPicker
  },

  data() {
    return {
      multipleSelection: [],
      showSearch: true,
      params: {
        sortByClickCount: false,
        sortByCites: false,
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
      editingTagUid: '',
      form: {
        sortNo: '',
        icon: '',
        sortName: '',
        content: '',
        status: ''
      },
      rules: {
        sortNo: [
          { required: true, message: '请输入排序', trigger: 'blur' },
          { validator: this.sortNoValidator, trigger: 'change' },
        ],
        icon: [
          { required: false, message: '请选择图标', trigger: 'blur' }
        ],
        sortName: [
          { required: true, message: '请输入分类名', trigger: 'blur' },
          { min: 1, max: 10, message: '长度限制在1到10' }
        ],
        content: [
          { required: false, message: '请输入介绍', trigger: 'blur' },
          { min: 0, max: 100, message: '最大输入100个字符' }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'change' },
        ],
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
      return hasAuth(this.menu, 'DELETE:/api/admin/blogSort')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/blogSort/{uid}')
    },
    /**
     * 检查是否有添加的权限
     * @returns {boolean|*}
     */
    canAdd: function () {
      return hasAuth(this.menu, 'POST:/api/admin/blogSort')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/blogSort/{uid}')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchTagList();
  },
  methods: {
    fetchTagList: function (){
      getTagList(this.params).then(res =>{
        this.tableData = res.data
        this.total = res.total
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
      this.fetchTagList()
    },
    /**
     * 根据点击量排序按钮点击事件
     */
    handleSortByClickCount: function () {
      this.params.sortByClickCount = true
      this.params.sortByCites = false
      this.fetchTagList()
    },
    /**
     * 根据引用量排序按钮点击事件
     */
    handleSortByCites: function () {
      this.params.sortByClickCount = false
      this.params.sortByCites = true
      this.fetchTagList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.keyword=''
      this.fetchTagList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchTagList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchTagList()
    },
    /**
     * 时间戳格式化
     */
    timeFormat(timestamp) {
      return parseTime(timestamp);
    },
    sortNoValidator: function (rule, value, callback) {
      let num = parseInt(value)
      if (0 <= num && num <= 100) {
        callback()
      } else {
        callback(new Error('排序限制在0到100之间'))
      }
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
      this.editingTagUid = ''
      this.form.status = ''
      this.form.icon = ''
      this.form.sortName = ''
      this.form.sortNo = ''
      this.form.content = ''

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
      this.form.sortNo = scope.row.sortNo
      this.form.icon = scope.row.icon
      this.form.sortName = scope.row.sortName
      this.form.content = scope.row.content
      this.form.status = scope.row.status
      this.editingTagUid = scope.row.uid


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
            deleteBlogSortBatch(uids).then(res => {
              this.fetchTagList()
              this.$message.success('删除成功');
              this.loading.close()
            }).catch(() => {
              this.loading.close()
            });
          }
        } else {
          // 走单独删除
          deleteBlogSort(scope.row.uid).then(res => {
            this.fetchTagList()
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

    submit: function () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // if (this.isEditForm) {
          //   updateBlogSort(this.editingTagUid, this.form).then(res => {
          //     this.$message.success("修改成功")
          //     this.editingTagUid = ''
          //     this.fetchTagList()
          //     this.dialogFormVisible = false;
          //     this.close()
          //   }).catch(err => {
          //     console.error(err)
          //   })
          // } else {
          //   addBlogSort(this.form).then(res => {
          //     this.$message.success("添加成功")
          //     this.fetchTagList()
          //     this.dialogFormVisible = false;
          //     this.close()
          //   }).catch(err => {
          //     console.error(err)
          //   })
          // }

        } else {
          console.log('error submit!!');
          return false;
        }
      })
    }
  }
}
</script>
