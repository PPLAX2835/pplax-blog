<template>
  <!--
    description t_exception_log表 管理页面
    author PPLAX
    date Thu Aug 29 11:06:16 CST 2024
  -->
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="开始时间">
        <el-date-picker v-model="params.startTime" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker v-model="params.endTime" type="datetime" placeholder="选择结束时间" value-format="yyyy-MM-dd HH:mm:ss">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleFind">查找</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
        <el-button v-if="canDeleteBatch" :disabled="!multipleSelection.length" type="danger" icon="el-icon-delete" size="small"
                   @click="handleDelete">批量删除
        </el-button>
      </el-form-item>

    </el-form>

    <!-- 表格区域 -->
    <div style="margin-top: 5px">
      <el-table border :data="tableData" style="width: 100%"
                @selection-change="handleSelectionChange"
      >

        <el-table-column align="center" type="expand" >
          <template slot-scope="scope">
            <el-descriptions title="更多" :column="1">

              <el-descriptions-item label="异常详情">
                <json-viewer
                  :value="JSON.parse(scope.row.exceptionJson)"
                  :expand-depth=5
                  copyable
                  boxed
                  sort
                ></json-viewer>
              </el-descriptions-item>

              <el-descriptions-item label="异常方法名">
                {{scope.row.className}}
              </el-descriptions-item>

              <el-descriptions-item label="异常信息">
                {{scope.row.exceptionMessage}}
              </el-descriptions-item>

              <el-descriptions-item label="请求参数">
                <json-viewer
                  :value="JSON.parse(scope.row.paramsJson)"
                  :expand-depth=5
                  copyable
                  boxed
                  sort
                ></json-viewer>
              </el-descriptions-item>
            </el-descriptions>
          </template>
        </el-table-column>

        <el-table-column align="center" type="selection" />

        <el-table-column width="130" align="center" label="请求用户头像">
          <template slot-scope="scope">
            <el-avatar v-if="scope.row.user" :src="scope.row.user.userInfo.avatar ? scope.row.user.userInfo.avatar.fileUrl : ''"></el-avatar>
            <span v-else>无</span>
          </template>
        </el-table-column>
        <el-table-column width="120" align="center" prop="methodName" label="异常方法名"></el-table-column>

        <el-table-column width="110" align="center" label="请求用户昵称">
          <template slot-scope="scope">
            <span>{{scope.row.user ? scope.row.user.userInfo.nickname : '无'}}</span>
          </template>
        </el-table-column>

        <el-table-column width="80" align="center" label="请求方法">
          <template slot-scope="scope">
            <el-tag>{{scope.row.endpoint.split(':')[0]}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column width="250" align="center" label="请求路径">
          <template slot-scope="scope">
            <span>{{scope.row.endpoint.split(':')[1]}}</span>
          </template>
        </el-table-column>

        <el-table-column width="250" align="center" label="请求名">
          <template slot-scope="scope">
            <span>{{scope.row.menu ? scope.row.menu.title : '无'}}</span>
          </template>
        </el-table-column>

        <el-table-column width="120" align="center" prop="ip" label="ip"></el-table-column>
        <el-table-column width="120" align="center" prop="address" label="地址"></el-table-column>

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

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { getExceptionLogList, updateExceptionLog, addExceptionLog, deleteExceptionLogBatch, deleteExceptionLog } from "../../api/exceptionLog";
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import { EStatus } from "../../base/EStatus"
import {mapGetters} from "vuex";
import JsonViewer from 'vue-json-viewer'

export default {
  data() {
    return {
      multipleSelection: [],
      multipleMenuSelection: [],
      showSearch: true,
      isFullScreen: false,
      params: {
        currentPage: 1,
        pageSize: 10
      },
      statusList: [],
      // 编辑/添加表单用到的
      isEditForm: false,
      dialogFormVisible: false,
      title: '',
      editingExceptionLogUid: '',
      form: {
      },
      rules: {
      },
      // 数据总数
      total:0,
      // 加载层信息
      loading: [],
      tableData: [],
    }
  },
  components: {
    JsonViewer
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
      return hasAuth(this.menu, 'DELETE:/api/admin/exceptionLog')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/exceptionLog/*')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchExceptionLogList();
  },
  methods: {
    fetchExceptionLogList: function (){
      getExceptionLogList(this.params).then(res =>{
        this.tableData = res.data
        this.total = res.total
        this.loading.close()
      })
    },
    /**
     * 查找按钮点击事件
     */
    handleFind: function () {
      this.params.currentPage = 1;
      this.fetchExceptionLogList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.keyword=''
      this.fetchExceptionLogList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchExceptionLogList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchExceptionLogList()
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
      this.editingExceptionLogUid = scope.row.uid

      this.isFullScreen = false;
      this.beforeShow("编辑", 1)
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
            deleteExceptionLogBatch(uids).then(res => {
              this.fetchExceptionLogList()
              this.$message.success('删除成功');
              this.loading.close()
            }).catch(() => {
              this.loading.close()
            });
          }
        } else {
          // 走单独删除
          deleteExceptionLog(scope.row.uid).then(res => {
            this.fetchExceptionLogList()
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
            updateExceptionLog(this.editingExceptionLogUid, this.form).then(res => {
              this.$message.success("修改成功")
              this.editingExceptionLogUid = ''
              this.fetchExceptionLogList()
              this.dialogFormVisible = false;
              this.close()
            })
          } else {
            addExceptionLog(this.form).then(res => {
              this.$message.success("添加成功")
              this.fetchExceptionLogList()
              this.dialogFormVisible = false;
              this.close()
            })
          }
        }
      })
    }
  }
}
</script>
