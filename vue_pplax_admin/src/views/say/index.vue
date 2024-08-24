<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="关键词">
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

        <el-table-column width="120" align="center" label="作者">
          <template slot-scope="scope">
            {{ scope.row.user.userInfo.nickname }}
          </template>
        </el-table-column>
        <el-table-column width="200" align="center" prop="content" label="内容"></el-table-column>

        <el-table-column width="300" align="center" label="图片">
          <template slot-scope="scope">
            <el-col v-for="item in scope.row.imageList" :span="5">
              <el-image style="border-radius: 4px;" :src="item.fileUrl"/>
            </el-col>
          </template>
        </el-table-column>

        <el-table-column width="150" align="center" prop="address" label="发布地址"></el-table-column>

        <el-table-column width="80" align="center" label="是否开放">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isPublic" type="success">是</el-tag>
            <el-tag v-else type="danger">否</el-tag>
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
    <el-dialog center :title="title" :visible.sync="dialogFormVisible" fullscreen>
      <el-form :rules="rules" ref="dataForm" :model="form">
        <el-row>
          <el-form-item label="内容" prop="content" :label-width="formLabelWidth">
            <el-input type="textarea" :rows="8" v-model="form.content"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="图片" prop="imageUids" :label-width="formLabelWidth">
            <el-row >
              <el-col v-for="item in editingImageList" :span="3">
                <el-row style="text-align: center">
                  <el-button @click="handleImageDelete(item)" class="el-icon-delete" style="width: 100%"></el-button>
                </el-row>
                <el-row>
                  <el-image style="border-radius: 4px; width: 100%" :src="item.fileUrl"></el-image>
                </el-row>
              </el-col>
              <el-col v-if="editingImageList.length < 9">
                <el-upload action="" class="avatar-uploader" :show-file-list="false"
                           :before-upload="uploadBefore" :http-request="uploadSelectionImage">
                  <el-button class="el-icon-plus avatar-img-icon"></el-button>
                </el-upload>
              </el-col>
            </el-row>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item prop="isPublic" label="是否公开" :label-width="formLabelWidth" >
              <el-radio v-model="form.isPublic" :label="true" border>是</el-radio>
              <el-radio v-model="form.isPublic" :label="false" border>否</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item prop="address" label="地址" :label-width="formLabelWidth" >
              <el-button @click="fetchAddress" icon="el-icon-location-outline">{{ form.address }}</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {addSay, getSayList, updateSay, deleteSayBatch, deleteSay, getAddress } from "../../api/say";
import {sayImageUpload} from "../../api/fileStorage";
import { hasAuth} from "../../utils/auth";
import { parseTime } from "../../utils";
import { EStatus } from "../../base/EStatus"
import { mapGetters } from "vuex";
import {checkImgType} from "../../utils/validate";

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
      editingSayUid: '',
      editingImageList: [],
      form: {
        imageUids: '',
        isPublic: '',
        content: '',
        address: ''
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
      return hasAuth(this.menu, 'DELETE:/api/admin/say')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/say/*')
    },
    /**
     * 检查是否有添加的权限
     * @returns {boolean|*}
     */
    canAdd: function () {
      return hasAuth(this.menu, 'POST:/api/admin/say')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/say/*')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchSayList();
  },
  methods: {
    fetchSayList: function (){
      getSayList(this.params).then(res =>{
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
      this.fetchSayList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.keyword=''
      this.fetchSayList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchSayList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchSayList()
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
      this.editingSayUid = ''
      this.editingImageList = []
      this.form.imageUids = ''
      this.form.isPublic = ''
      this.form.content = ''
      this.form.address = ''

      this.beforeShow("添加说说", 0)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /**
     * 编辑按钮点击事件
     * @param scope
     */
    handleUpdate: function (scope) {
      this.editingSayUid = scope.row.uid
      this.editingImageList = scope.row.imageList
      this.form.imageUids = scope.row.imageUids
      this.form.isPublic = scope.row.isPublic
      this.form.content = scope.row.content
      this.form.address = scope.row.address


      this.beforeShow("修改说说", 1)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /**
     * 处理删除图片按钮点击事件
     * @param target
     */
    handleImageDelete: function (target) {
      this.editingImageList = this.editingImageList.filter(item => item.uid !== target.uid);
      if (this.editingImageList.length <= 0) {
        this.form.imageUids = ''
      } else {
        let uids = this.editingImageList[0].uid
        for (let i = 1; i < this.editingImageList.length; i++) {
          uids += ',' + this.editingImageList[i].uid
        }
        this.form.imageUids = uids
      }
    },
    /**
     * 图片上传之前的准备
     */
    uploadBefore: function (file) {
      const isImage = checkImgType(file);
      if (!isImage) {
        this.$message.error('文件格式错误');
      }
      return isImage
    },
    /**
     * 上传博客封面
     * @param param
     */
    uploadSelectionImage: function (param) {
      this.openLoading();
      let file = param.file
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', file)
      sayImageUpload(formData).then(res => {
        this.editingImageList.push(res.data)
        let uids = this.editingImageList[0].uid
        for (let i = 1; i < this.editingImageList.length; i++) {
          uids += ',' + this.editingImageList[i].uid
        }
        this.form.imageUids = uids
        this.loading.close()
      })
    },
    /**
     * 获得地址
     */
    fetchAddress() {
      this.form.address = '正在定位...'
      getAddress().then(resp => {
        this.form.address = resp.data
      }).catch(error => {
        this.form.address = '定位失败'
        let that = this
        setTimeout(function () {
          that.form.address = ''
        }, 1000)
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
            deleteSayBatch(uids).then(res => {
              this.fetchSayList()
              this.$message.success('删除成功');
              this.loading.close()
            }).catch(() => {
              this.loading.close()
            });
          }
        } else {
          // 走单独删除
          deleteSay(scope.row.uid).then(res => {
            this.fetchSayList()
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
            updateSay(this.editingSayUid, this.form).then(res => {
              this.$message.success("修改成功")
              this.editingSayUid = ''
              this.fetchSayList()
              this.dialogFormVisible = false;
              this.close()
            }).catch(err => {
              console.error(err)
            })
          } else {
            addSay(this.form).then(res => {
              this.$message.success("添加成功")
              this.fetchSayList()
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
