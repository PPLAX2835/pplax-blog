<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="角色名">
        <el-input style="width: 150px" size="small" v-model="params.keyword" placeholder="请输入友链名"/>
      </el-form-item>
      <el-form-item label="状态">
        <el-select style="width: 130px" size="small" v-model="params.status" @change="fetchLinkList" placeholder="请选择状态">
          <el-option label="全部" value=""/>
          <el-option label="上架" :value="statusList.ENABLE" />
          <el-option label="下架" :value="statusList.OFF_SHELF" />
          <el-option label="申请" :value="statusList.APPLYING" />
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
        <el-table-column align="center" prop="avatar" label="友链logo"  width="120">
          <template slot-scope="scope">
            <div class="block">
              <el-avatar v-if="scope.row.iconImage !== undefined && scope.row.iconImage.fileUrl !== undefined" :size="50" :src="scope.row.iconImage.fileUrl"></el-avatar>
              <el-avatar v-else :size="50"></el-avatar>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="title" align="center" label="友链名" width="130" />
        <el-table-column prop="summary" align="center" label="介绍" width="200" />
        <el-table-column width="180" align="center" label="添加时间">
          <template slot-scope="scope">
            <el-link target="_blank" :href="scope.row.url">
              {{ scope.row.url }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="clickCount" align="center" label="点击量" width="80" />
        <el-table-column width="80" align="center" label="状态">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status === statusList.ENABLE">上架</el-tag>
            <el-tag type="danger" v-else-if="scope.row.status === statusList.OFF_SHELF">下架</el-tag>
            <el-tag type="warning" v-else-if="scope.row.status === statusList.APPLYING">申请</el-tag>
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

        <el-form-item v-if="editingLinkUid" prop="avatar" label="logo图片" :label-width="formLabelWidth">
          <el-upload action="" class="avatar-uploader" :show-file-list="false"
                     :before-upload="uploadBefore" :http-request="uploadSectionIconImage">
            <img v-if="logoImageUrl" :src="logoImageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <el-form-item prop="title" label="友链名" :label-width="formLabelWidth">
          <el-input  v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="summary" label="介绍" :label-width="formLabelWidth">
          <el-input  v-model="form.summary" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="url" label="url" :label-width="formLabelWidth">
          <el-input  v-model="form.url" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="status" label="状态" :label-width="formLabelWidth">
          <el-select size="small" v-model="form.status" placeholder="请选择状态">
            <el-option label="上架" :value="statusList.ENABLE" />
            <el-option label="下架" :value="statusList.OFF_SHELF" />
            <el-option label="申请" :value="statusList.APPLYING" />
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
import { getLinkList, addLink, editLink, deleteLink, deleteLinkBatch } from "../../api/link";
import { linkIconImageUpload } from "../../api/fileStorage";
import { hasAuth } from "../../utils/auth";
import { validateURL, checkImgType } from "../../utils/validate";
import { parseTime } from "../../utils";
import IconPicker from "../../components/IconPicker"
import { EStatus } from "../../base/EStatus"
import { mapGetters } from "vuex";
import {isTagNameExist} from "../../api/tag";

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
      editingLinkUid: '',
      logoImageUrl: '',
      form: {
        title: '',
        summary: '',
        url: '',
        iconImageUid: '',
        status: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入友链名', trigger: 'blur' },
          { min: 1, max: 20, message: '友链名长度限制在1到20之间', trigger: 'change' }
        ],
        summary: [
          { required: false, message: '请输入介绍', trigger: 'blur' },
          { min: 1, max: 50, message: '标签名长度限制在1到50之间', trigger: 'change' }
        ],
        url: [
          { required: true , message: '请输入url', trigger: 'blur' },
          { validator: this.urlValidator, trigger: 'change' },
        ],
        status: [
          { required: true , message: '请选择状态', trigger: 'blur' }
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
      return hasAuth(this.menu, 'DELETE:/api/admin/link')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/link/{uid}')
    },
    /**
     * 检查是否有添加的权限
     * @returns {boolean|*}
     */
    canAdd: function () {
      return hasAuth(this.menu, 'POST:/api/admin/link')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/link/{uid}')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchLinkList();
  },
  methods: {
    fetchLinkList: function (){
      getLinkList(this.params).then(res =>{
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
      this.fetchLinkList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.keyword = ''
      this.params.status = ''
      this.fetchLinkList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchLinkList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchLinkList()
    },
    /**
     * 时间戳格式化
     */
    timeFormat(timestamp) {
      return parseTime(timestamp);
    },
    urlValidator (rule, value, callback) {
      if (validateURL(value)) {
        callback()
      } else {
        callback(new Error('url不合法'))
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
     * 图片上传之前的验证
     * @param file
     * @returns {boolean}
     */
    uploadBefore: function (file) {
      const isImage = checkImgType(file);
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        this.$message.error('文件格式错误');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return isImage && isLt2M;
    },

    /**
     * logo上传
     * @param param
     */
    uploadSectionIconImage: function (param) {
      this.openLoading();
      let file = param.file
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', file)
      linkIconImageUpload(formData).then(res => {
        this.form.iconImageUid = res.data.uid
        this.logoImageUrl = res.data.fileUrl
        this.fetchLinkList()
        this.loading.close()
      })
    },
    /**
     * 添加按钮的点击事件
     */
    handleCreate: function () {
      this.editingLinkUid = ''
      this.logoImageUrl = ''
      this.form.title = ''
      this.form.summary = ''
      this.form.url = ''
      this.form.iconImageUid = ''
      this.form.status = ''

      this.beforeShow("添加友链", 0)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /**
     * 编辑按钮点击事件
     * @param scope
     */
    handleUpdate: function (scope) {
      this.editingLinkUid = scope.row.uid
      this.logoImageUrl = (scope.row.iconImage === undefined ? '' : scope.row.iconImage.fileUrl)
      this.form.title = scope.row.title
      this.form.summary = scope.row.summary
      this.form.url = scope.row.url
      this.form.iconImageUid = scope.row.iconImageUid
      this.form.status = scope.row.status


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
            deleteLinkBatch(uids).then(res => {
              this.fetchLinkList()
              this.$message.success('删除成功');
              this.loading.close()
            }).catch(() => {
              this.loading.close()
            });
          }
        } else {
          // 走单独删除
          deleteLink(scope.row.uid).then(res => {
            this.fetchLinkList()
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
            editLink(this.editingLinkUid, this.form).then(res => {
              this.$message.success("修改成功")
              this.editingLinkUid = ''
              this.fetchLinkList()
              this.dialogFormVisible = false;
              this.close()
            }).catch(err => {
              console.error(err)
            })
          } else {
            addLink(this.form).then(res => {
              this.$message.success("添加成功")
              this.fetchLinkList()
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
