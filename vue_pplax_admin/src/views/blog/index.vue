<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="博客名">
        <el-input style="width: 150px" size="small" v-model="params.blogTitle" placeholder="请输入博客名"/>
      </el-form-item>
      <el-form-item label="状态">
        <el-select @change="fetchBlogList" style="width: 130px" size="small" v-model="params.status" placeholder="请选择状态">
          <el-option label="全部" value=""/>
          <el-option label="发布" :value="statusList.ENABLE" />
          <el-option label="下架" :value="statusList.OFF_SHELF" />
          <el-option label="待审批" :value="statusList.PENDING_APPROVAL" />
          <el-option label="草稿" :value="statusList.DRAFT" />
        </el-select>
      </el-form-item>
      <el-form-item label="分类">
        <el-select @change="fetchBlogList" clearable filterable remote :remote-method="fetchBlogSortList" style="width: 130px" size="small" v-model="params.blogSortUid" placeholder="请选择分类">
          <el-option
            v-for="item in options"
            :key="item.sortName"
            :label="item.sortName"
            :value="item.uid"></el-option>
        </el-select>
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
        <el-table-column width="170" align="center" label="文章封面">
          <template slot-scope="scope">
            <el-image v-if="scope.row.coverImage !== undefined && scope.row.coverImage.fileUrl !== undefined" :size="50" :src="scope.row.coverImage.fileUrl" class="article-cover" />
            <i id="imgIcon" v-else class="el-icon-warning-outline" />
          </template>
        </el-table-column>
        <el-table-column width="180" align="center" prop="title" label="标题"></el-table-column>
        <el-table-column width="120" align="center" label="作者">
          <template slot-scope="scope">
            {{ scope.row.user.userInfo.nickname }}
          </template>
        </el-table-column>
        <el-table-column width="200" align="center" prop="summary" label="摘要"></el-table-column>
        <el-table-column width="120" align="center" label="分类">
          <template slot-scope="scope">
            <el-tag type="warning">{{ scope.row.blogSort.sortName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="200" align="center" label="标签">
          <template slot-scope="scope">
            <el-tag v-for="item in scope.row.tagList">{{ item.name }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="80" align="center" prop="level" label="推荐等级"></el-table-column>
        <el-table-column width="80" align="center" prop="clickCount" label="点击量"></el-table-column>
        <el-table-column width="80" align="center" prop="collectCount" label="收藏数"></el-table-column>
        <el-table-column width="80" align="center" label="是否原创">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.isOriginal">是</el-tag>
            <a :href="scope.row.articlesPart" target="_blank" v-else>
              <el-tooltip content="查看原文">
                <el-tag type="info">否</el-tag>
              </el-tooltip>
            </a>
          </template>
        </el-table-column>
        <el-table-column width="80" align="center" label="状态">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status === statusList.ENABLE">发布</el-tag>
            <el-tag type="info" v-else-if="scope.row.status === statusList.PENDING_APPROVAL">待审批</el-tag>
            <el-tag type="danger" v-else-if="scope.row.status === statusList.OFF_SHELF">下架</el-tag>
            <el-tag type="warning" v-else-if="scope.row.status === statusList.DRAFT">草稿</el-tag>
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { getBlogSortList } from "../../api/blogSort";
import {addTag, getBlogList, updateTag, deleteTag, deleteTagBatch } from "../../api/blog";
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
      showSearch: true,
      options: [],
      params: {
        blogTitle: '',
        blogSortUid: '',
        tagUid: '',
        status: '',
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
      return hasAuth(this.menu, 'DELETE:/api/admin/blog')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/blog/{uid}')
    },
    /**
     * 检查是否有添加的权限
     * @returns {boolean|*}
     */
    canAdd: function () {
      return hasAuth(this.menu, 'POST:/api/admin/blog')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/blog/{uid}')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchBlogList();
  },
  methods: {
    fetchBlogSortList: function (key){
      this.loading = true
      getBlogSortList({
        sortByClickCount: false,
        sortByCites: false,
        keyword: key,
        currentPage: 1,
        pageSize: 5
      }).then(res =>{
        this.loading = false
        this.options = res.data
      }).catch(err =>{
        console.log(err)
      })
    },
    fetchBlogList: function (){
      getBlogList(this.params).then(res =>{
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
      this.fetchBlogList()
    },
    /**
     * 根据点击量排序按钮点击事件
     */
    handleSortByClickCount: function () {
      this.params.sortByClickCount = true
      this.params.sortByCites = false
      this.fetchBlogList()
    },
    /**
     * 根据引用量排序按钮点击事件
     */
    handleSortByCites: function () {
      this.params.sortByClickCount = false
      this.params.sortByCites = true
      this.fetchBlogList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.blogTitle=''
      this.params.blogSortUid = ''
      this.params.tagUid = ''
      this.params.status = ''
      this.fetchBlogList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchBlogList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchBlogList()
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
            //   this.fetchBlogList()
            //   this.$message.success('删除成功');
            //   this.loading.close()
            // }).catch(() => {
            //   this.loading.close()
            // });
          }
        } else {
          // 走单独删除
          // deleteTag(scope.row.uid).then(res => {
          //   this.fetchBlogList()
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
            //   this.fetchBlogList()
            //   this.dialogFormVisible = false;
            //   this.close()
            // }).catch(err => {
            //   console.error(err)
            // })
          } else {
            // addTag(this.form).then(res => {
            //   this.$message.success("添加成功")
            //   this.fetchBlogList()
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
