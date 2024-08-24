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
        <el-table-column width="250" align="center" label="访问地址">
          <template slot-scope="scope">
            <el-link target="_blank" :href="scope.row.fileUrl">
              <el-image v-if="/(jpg|jpeg|png|GIF|JPG|PNG)$/.test(scope.row.suffix)" :src="scope.row.fileUrl"></el-image>
              <span v-else>
                {{ scope.row.fileUrl }}
              </span>
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="fileName" align="center" label="文件名" width="130" />
        <el-table-column prop="originalName" align="center" label="原文件名" width="130" />
        <el-table-column prop="suffix" align="center" label="文件后缀" width="80" />
        <el-table-column prop="filePath" align="center" label="存储路径" width="180" />
        <el-table-column prop="fileSize" align="center" label="文件大小" width="100" />
        <el-table-column width="80" align="center" label="是否是目录">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isDirectory === 1">是</el-tag>
            <el-tag v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="80" align="center" label="是否是图片">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isImage === 1">是</el-tag>
            <el-tag v-else>否</el-tag>
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

  </div>
</template>

<script>
import { getFileStorageList, deleteFileStorageBatch, deleteFileStorage } from "../../api/fileStorage";
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
      return hasAuth(this.menu, 'DELETE:/api/file')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/file/*')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchFileStorageList();
  },
  methods: {
    fetchFileStorageList: function (){
      getFileStorageList(this.params).then(res =>{
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
      this.fetchFileStorageList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.keyword=''
      this.fetchFileStorageList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchFileStorageList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchFileStorageList()
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
            deleteFileStorageBatch(uids).then(res => {
              this.fetchFileStorageList()
              this.$message.success('删除成功');
              this.loading.close()
            }).catch(() => {
              this.loading.close()
            });
          }
        } else {
          // 走单独删除
          deleteFileStorage(scope.row.uid).then(res => {
            this.fetchFileStorageList()
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

  }
}
</script>
