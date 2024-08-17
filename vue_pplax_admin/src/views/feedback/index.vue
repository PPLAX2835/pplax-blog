<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="类型">
        <el-select style="width: 130px" size="small" v-model="params.type" @change="fetchFeedbackList" placeholder="请选择类型">
          <el-option label="全部" value=""/>
          <el-option label="反馈" :value="0" />
          <el-option label="需求" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select style="width: 130px" size="small" v-model="params.status" @change="fetchFeedbackList" placeholder="请选择状态">
          <el-option label="全部" value=""/>
          <el-option label="未解决" :value="statusList.UNRESOLVED" />
          <el-option label="进行中" :value="statusList.IN_PROGRESS" />
          <el-option label="已拒绝" :value="statusList.REFUSED" />
          <el-option label="已解决" :value="statusList.RESOLVED" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
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

        <el-table-column width="120" align="center" label="反馈人">
          <template slot-scope="scope">
            {{ scope.row.user.userInfo.nickname }}
          </template>
        </el-table-column>
        <el-table-column width="150" align="center" prop="title" label="标题"></el-table-column>
        <el-table-column width="250" align="center" prop="content" label="内容"></el-table-column>
        <el-table-column width="170" align="center" label="附件图">
          <template slot-scope="scope">
            <el-image v-if="scope.row.picture && scope.row.picture.fileUrl" :size="50" :src="scope.row.picture.fileUrl" class="article-cover" />
            <i id="imgIcon" v-else class="el-icon-warning-outline" />
          </template>
        </el-table-column>
        <el-table-column width="100" align="center" label="类型">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type === 0" type="danger">反馈</el-tag>
            <el-tag v-else-if="scope.row.type === 1" type="warning">需求</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="150" align="center" label="状态">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === statusList.UNRESOLVED" type="danger">未解决</el-tag>
            <el-tag v-else-if="scope.row.status === statusList.IN_PROGRESS" type="warning">进行中</el-tag>
            <el-tag v-else-if="scope.row.status === statusList.REFUSED" type="info">已拒绝</el-tag>
            <el-tag v-else-if="scope.row.status === statusList.RESOLVED" type="success">已解决</el-tag>
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
        <el-table-column width="200" fixed="right" align="center" label="操作" class-name="small-padding fixed-width">
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
        <el-form-item prop="type" label="类型">
          <el-select style="width: 130px" size="small" v-model="form.type" placeholder="请选择类型">
            <el-option label="反馈" :value="0" />
            <el-option label="需求" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item prop="status" label="状态">
          <el-select style="width: 130px" size="small" v-model="form.status" placeholder="请选择状态">
            <el-option label="未解决" :value="statusList.UNRESOLVED" />
            <el-option label="进行中" :value="statusList.IN_PROGRESS" />
            <el-option label="已拒绝" :value="statusList.REFUSED" />
            <el-option label="已解决" :value="statusList.RESOLVED" />
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
import { getFeedbackList, editFeedback, deleteFeedbackBatch, deleteFeedback} from "../../api/feedback";
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
        status: '',
        type: '',
        currentPage: 1,
        pageSize: 10
      },
      statusList: [],
      // 编辑/添加表单用到的
      formLabelWidth: '120px',
      isEditForm: false,
      dialogFormVisible: false,
      title: '',
      editingFeedbackUid: '',
      form: {
        status: '',
        type: ''
      },
      rules: {
        status: [
          { required: true, message: '请选择状态', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请选择类型', trigger: 'blur' },
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
      return hasAuth(this.menu, 'DELETE:/api/admin/feedback')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/feedback/{uid}')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/feedback/{uid}')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchFeedbackList();
  },
  methods: {
    fetchFeedbackList: function (){
      getFeedbackList(this.params).then(res =>{
        this.tableData = res.data
        this.total = res.total
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.status = ''
      this.params.type = ''
      this.fetchFeedbackList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchFeedbackList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchFeedbackList()
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
     * 编辑按钮点击事件
     * @param scope
     */
    handleUpdate: function (scope) {
      this.editingFeedbackUid = scope.row.uid
      this.form.type = scope.row.type
      this.form.status = scope.row.status


      this.beforeShow("修改反馈", 1)
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
            deleteFeedbackBatch(uids).then(res => {
              this.fetchFeedbackList()
              this.$message.success('删除成功');
              this.loading.close()
            }).catch(() => {
              this.loading.close()
            });
          }
        } else {
          // 走单独删除
          deleteFeedback(scope.row.uid).then(res => {
            this.fetchFeedbackList()
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
          if (this.isEditForm) {
            editFeedback(this.editingFeedbackUid, this.form).then(res => {
              this.$message.success("修改成功")
              this.editingFeedbackUid = ''
              this.fetchFeedbackList()
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
