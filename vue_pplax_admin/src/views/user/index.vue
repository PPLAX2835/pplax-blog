<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="用户名">
        <el-input style="width: 150px" size="small" v-model="params.username" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input style="width: 150px" size="small" v-model="params.nickname" placeholder="请输入用户昵称"/>
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
    <div>
      <el-table border :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" align="center" />
        <el-table-column align="center" prop="avatar" label="头像"  width="120">
          <template slot-scope="scope">
            <div class="block">
              <el-avatar v-if="scope.row.userInfo.avatar && scope.row.userInfo.avatar.fileUrl" :size="50" :src="scope.row.userInfo.avatar.fileUrl"></el-avatar>
              <el-avatar v-else :size="50"></el-avatar>
            </div>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="username" label="用户名" width="120" />
        <el-table-column align="center" prop="userInfo.nickname" label="昵称" width="180" />
        <el-table-column align="center" prop="email" label="邮箱" width="150" />
        <el-table-column align="center" label="邮箱是否激活" width="120">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.isEmailActivated">是</el-tag>
            <el-tag type="warning" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="用户角色" width="120">
          <template slot-scope="scope">
            <el-tag type="info">
              {{ scope.row.role.roleName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="isOnline" label="在线状态" width="80">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isOnline" type="success">在线</el-tag>
            <el-tag v-else>下线</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="status" label="状态" width="80">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === statusList.ENABLE" type="success">正常</el-tag>
            <el-tag v-else-if="scope.row.status === statusList.FREEZE">冻结</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="lastLoginIp" label="上次登录ip" width="150" />
        <el-table-column align="center"  label="上次登录地址"  width="150" >
          <template slot-scope="scope">
            {{ getCity(scope.row.lastLoginAddress) }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="loginCount" label="登录次数" width="80" />
        <el-table-column align="center"  label="上次登录时间"  width="180" >
          <template slot-scope="scope">
            {{ timeFormat(scope.row.lastLoginTime) }}
          </template>
        </el-table-column>
        <el-table-column align="center"  label="创建时间"  width="180" >
          <template slot-scope="scope">
            {{ timeFormat(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column align="center"  label="修改时间"  width="180" >
          <template slot-scope="scope">
            {{ timeFormat(scope.row.updateTime) }}
          </template>
        </el-table-column>
        <el-table-column fixed="right" align="center" label="操作" width="250">
          <template slot-scope="scope">
            <el-button v-if="canKick && scope.row.isOnline" type="warning" size="mini" @click="handleKick(scope)">强制下线</el-button>
            <el-button v-if="canUpdate" type="primary" size="mini" @click="handleUpdate(scope)">编辑</el-button>
            <el-button v-if="canDelete" type="danger" size="mini" @click="handleDelete(scope)">删除</el-button>
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
        <el-form-item v-if="editingUserUid" prop="spaceBackgroundPicture" label="空间背景图" :label-width="formLabelWidth">
          <el-upload action="" class="avatar-uploader" :show-file-list="false"
                     :before-upload="uploadBefore" :http-request="uploadSectionSpaceBackgroundPicture">
            <img v-if="spaceBackgroundPictureUrl" :src="spaceBackgroundPictureUrl" align="left" width="70%">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item v-if="editingUserUid" prop="avatar" label="头像" :label-width="formLabelWidth">
          <el-upload action="" class="avatar-uploader" :show-file-list="false"
                     :before-upload="uploadBefore" :http-request="uploadSectionAvatar">
            <img v-if="avatarUrl" :src="avatarUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item prop="username" label="用户名" :label-width="formLabelWidth">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="status" label="是否激活邮箱" :label-width="formLabelWidth">
          <div>
            <el-radio v-model="form.isEmailActivated" :label="true" border>是</el-radio>
            <el-radio v-model="form.isEmailActivated" :label="false" border>否</el-radio>
          </div>
        </el-form-item>
        <el-form-item v-if="!isEditForm" prop="password" label="密码" :label-width="formLabelWidth">
          <el-input v-model="form.password" type="password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-if="!isEditForm" prop="confirmPassword" label="确认密码" :label-width="formLabelWidth">
          <el-input v-model="form.confirmPassword" type="password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="nickname" label="昵称" :label-width="formLabelWidth">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="birthday" label="生日" :label-width="formLabelWidth">
          <el-date-picker
            v-model="form.birthday"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item prop="summary" label="个性签名" :label-width="formLabelWidth">
          <el-input v-model="form.summary" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="status" label="状态" :label-width="formLabelWidth">
          <div>
            <el-radio v-model="form.status" :label="1" border>正常</el-radio>
            <el-radio v-model="form.status" :label="2" border>冻结</el-radio>
          </div>
        </el-form-item>
        <el-form-item prop="roleUid" label="角色" :label-width="formLabelWidth">
          <div>
            <el-radio v-for="item in roleList" v-model="form.roleUid" :label="item.uid" border>{{ item.roleName }}</el-radio>
          </div>
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
import {
  getUserList,
  updateUserInfo,
  addUser,
  deleteUser,
  deleteUserBatch,
  isUsernameExist,
  kickUser
} from '../../api/user'
import { avatarUpload, spaceBackgroundPictureUpload } from "../../api/fileStorage";
import { EStatus } from "../../base/EStatus"
import { hasAuth } from "../../utils/auth";
import { checkImgType } from "../../utils/validate"
import { parseTime } from "../../utils";
import {mapGetters} from "vuex";

export default {

  data() {
    return {
      multipleSelection: [],
      showSearch: true,
      params: {
        username: '',
        nickname: '',
        currentPage: 1,
        pageSize: 10
      },
      // 编辑/添加表单用到的
      formLabelWidth: '120px',
      isEditForm: false,
      dialogFormVisible: false,
      editingUserUid: '',
      editingUserUsername: '',
      title: '',
      avatarUrl: '',
      spaceBackgroundPictureUrl: '',
      confirmPassword: '',
      form: {
        isEmailActivated: '',
        avatarPictureUid: '',
        spaceBackgroundPictureUid: '',
        password: '',
        nickname: '',
        roleUid: '',
        status: '',
        username: '',
        birthday: '',
        summary: '',
        email: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { validator: this.isExist, trigger: 'change'},
          { min: 3, max: 30, message: '长度在3到30个字符' },
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { pattern: /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/, message: '请输入合法的邮箱', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1到20个字符' },
        ],
        birthday: [
          { required: false, message: '请输选择生日', trigger: 'blur' },
        ],
        summary: [
          { required: false, message: '请输入个性签名', trigger: 'blur' },
          { max: 100, message: '最多输入100个字符' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 8, max: 16, message: '长度在8到16个字符' },
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { validator: this.passwordConfirm, trigger: 'blur'},
          { min: 8, max: 16, message: '长度在8到16个字符' },
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'change' },
        ],
        roleUid: [
          { required: true, message: '请选择角色', trigger: 'change' },
        ]
      },
      // 数据总数
      total:0,
      statusList: [],
      // 加载层信息
      loading: [],
      tableData: []
    }
  },
  computed: {
    ...mapGetters([
      'menu',
      'roleList'
    ]),
    /**
     * 检查是否有批量删除的权限
     * @returns {boolean|*}
     */
    canDeleteBatch: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/user')
    },
    /**
     * 检查是否有踢人下线的权限
     * @returns {boolean|*}
     */
    canKick: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/user/*/kick')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/user/*')
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
      return hasAuth(this.menu, 'PUT:/api/admin/user/*/userInfo')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchUserList();
  },
  methods: {
    fetchUserList: function (){
      getUserList(this.params).then(res =>{
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
      this.fetchUserList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.username=''
      this.params.nickname=''
      this.fetchUserList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchUserList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchUserList()
    },
    /**
     * 时间戳格式化
     */
    timeFormat(timestamp) {
      return parseTime(timestamp);
    },
    /**
     * 从给出的ip信息中获取地址
     */
    getCity(ipInfo) {
      if (ipInfo !== '未知') {
        let res = ''

        let arr = ipInfo.split("|")

        for (let i = 0; i < arr.length - 1; i++) {
          console.log(arr[i] )
          if (arr[i] !== '0') {
            res += (arr[i] + '-')
          }
        }

        return res.substring(0, res.length - 1);
      }
      return '未知'
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
      this.form.avatarPictureUid = ''
      this.form.nickname = ''
      this.form.roleUid = ''
      this.form.status = ''
      this.form.username = ''
      this.form.password = ''
      this.form.birthday = ''
      this.form.summary = ''
      this.form.email = ''
      this.confirmPassword = ''
      this.avatarUrl = ''
      this.spaceBackgroundPictureUrl = ''
      this.editingUserUid = ''
      this.editingUserUsername = ''

      this.beforeShow("添加用户", 0)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /**
     * 踢人下线按钮点击事件
     * @param scope
     */
    handleKick: function (scope) {
      this.$confirm('是否确定强制下线？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        kickUser(scope.row.uid).then(res => {
          this.fetchUserList()
          this.$message.success('已强制下线');
          this.loading.close()
        }).catch(() => {
          this.loading.close()
        });
      })
    },
    /**
     * 编辑按钮点击事件
     * @param scope
     */
    handleUpdate: function (scope) {
      if (scope.row.userInfo.avatar && scope.row.userInfo.avatar.fileUrl) {
        this.form.avatarPictureUid = scope.row.userInfo.avatar.uid
        this.avatarUrl = scope.row.userInfo.avatar.fileUrl
      } else {
        this.form.avatarPictureUid = ''
        this.avatarUrl = ''
      }
      this.form.nickname = scope.row.userInfo.nickname
      this.form.roleUid = scope.row.roleUid
      this.form.status = scope.row.status
      this.form.username = scope.row.username
      this.form.birthday = scope.row.userInfo.birthday
      this.form.summary = scope.row.userInfo.summary
      this.form.email = scope.row.email
      this.form.password = ''
      if (scope.row.userInfo.spaceBackgroundPicture && scope.row.userInfo.spaceBackgroundPicture.fileUrl) {
        this.spaceBackgroundPictureUrl = scope.row.userInfo.spaceBackgroundPicture.fileUrl
        this.form.spaceBackgroundPictureUid = scope.row.userInfo.spaceBackgroundPicture.uid
      } else {
        this.spaceBackgroundPictureUrl = ''
        this.form.spaceBackgroundPictureUid = ''
      }
      this.editingUserUid = scope.row.uid
      this.editingUserUsername = scope.row.username
      this.form.email = scope.row.email
      this.form.isEmailActivated = scope.row.isEmailActivated

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
            deleteUserBatch(uids).then(res => {
              this.fetchUserList()
              this.$message.success('删除成功');
              this.loading.close()
            }).catch(() => {
              this.loading.close()
            });
          }
        } else {
          // 走单独删除
          deleteUser(scope.row.uid).then(res => {
            this.fetchUserList()
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
     * 头像上传
     * @param param
     */
    uploadSectionAvatar: function (param) {
      this.openLoading();
      let file = param.file
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', file)
      avatarUpload(formData).then(res => {
        this.form.avatarPictureUid = res.data.uid
        this.avatarUrl = res.data.fileUrl
        this.fetchUserList()
        this.loading.close()
      })
    },
    /**
     * 上传空间背景图
     * @param param
     */
    uploadSectionSpaceBackgroundPicture: function (param) {
      this.openLoading();
      let file = param.file
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', file)
      spaceBackgroundPictureUpload(formData).then(res => {
        this.form.spaceBackgroundPictureUid = res.data.uid
        this.spaceBackgroundPictureUrl = res.data.fileUrl
        this.loading.close()
      })
    },

    /**
     * 检查用户名是否存在
     * @param rule
     * @param value
     * @param callback
     */
    isExist(rule, value, callback) {
      if (this.editingUserUsername === '' || this.editingUserUsername !== value) {
        isUsernameExist(value).then(res => {

          if (res.data) {
            callback(new Error('该用户名已存在'))
          } else {
            callback()
          }

        })
      } else {
        callback()
      }
    },

    /**
     * 检查两次密码输入是否相同
     * @param rule
     * @param value
     * @param callback
     */
    passwordConfirm(rule, value, callback) {
      if (this.form.password !== '' && value !== this.form.password) {
        callback(new Error('两次密码输入不一致'))
      } else {
        callback()
      }
    },

    submit: function () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.isEditForm) {
            updateUserInfo(this.editingUserUid, this.form).then(res => {
              this.$message.success("修改成功")
              this.editingUserUid = ''
              this.editingUserUsername = ''
              this.fetchUserList()
              this.dialogFormVisible = false;
              this.close()
            }).catch(err => {
              console.error(err)
            })
          } else {
            addUser(this.form).then(res => {
              this.$message.success("添加成功")
              this.fetchUserList()
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

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
</style>
