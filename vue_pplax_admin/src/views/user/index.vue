<template>
  <div class="app-container">
    <!--查询or添加-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="昵称">
        <el-input style="width: 150px" size="small" v-model="params.keyword" placeholder="请输入用户昵称"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleFind">查找</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格区域 -->
    <div>
      <el-table border :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" align="center" />
        <el-table-column align="center" prop="avatar" label="头像"  width="120">
          <template slot-scope="scope">
            <div class="block"><el-avatar :size="50" :src="scope.row.userInfo.avatar.fileUrl"></el-avatar></div>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="username" label="用户名" width="120" />
        <el-table-column align="center" prop="userInfo.nickname" label="昵称" width="180" />
        <el-table-column align="center" label="用户角色" width="120">
          <template slot-scope="scope">
            <el-tag>
              {{ scope.row.role.roleName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="lastLoginIp" label="上次登录ip" />
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
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button v-if="canKick()" type="danger" size="mini" @click="kick(scope)">强制下线</el-button>
            <el-button v-if="canUpdate()" type="primary" size="mini" @click="handleUpdate(scope)">编辑</el-button>
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
        <el-form-item prop="avatar" label="头像" :label-width="formLabelWidth">
          <el-upload action="" class="avatar-uploader" :show-file-list="false"
                     :before-upload="uploadBefore" :http-request="uploadSectionFile">
            <img v-if="avatarUrl" :src="avatarUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item prop="username" label="用户名" :label-width="formLabelWidth">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-if="!isEditForm" prop="password" label="密码" :label-width="formLabelWidth">
          <el-input v-model="form.password" autocomplete="off"></el-input>
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
import { getUserList, updateUserInfo } from '@/api/user'
import { avatarUpload } from "../../api/fileStorage";
import { getRoleList } from "../../api/role"
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import {mapGetters} from "vuex";

export default {

  data() {
    return {
      showSearch: true,
      params: {
        keyword: '',
        currentPage: 1,
        pageSize: 10
      },
      // 编辑/添加表单用到的
      formLabelWidth: '120px',
      isEditForm: false,
      dialogFormVisible: false,
      editingUserUid: '',
      title: '',
      avatarUrl: '',
      roleList: '',
      form: {
        avatarPictureUid: '',
        nickname: '',
        roleUid: '',
        status: '',
        username: '',
        birthday: '',
        summary: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在1到50个字符' },
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在1到50个字符' },
        ],
        bitthday: [
          { required: false, message: '请输选择生日', trigger: 'blur' },
        ],
        summary: [
          { required: false, message: '请输入个性签名', trigger: 'blur' },
          { max: 100, message: '最多输入100个字符' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
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
    this.openLoading();
    this.fetchUserList();
    this.fetchRoleList();
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
    fetchRoleList: function () {
      getRoleList(this.params).then(res =>{
        this.roleList = res.data
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
      this.params.keyword=null
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
     * 检查是否有踢人下线的权限
     * @returns {boolean|*}
     */
    canKick: function () {
      return hasAuth(this.menu, 'GET:/api/admin/user/{uid}/kick')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/user/{uid}/userInfo')
    },
    /**
     * 强制下线按钮点击事件
     * @param scope
     */
    kick(scope) {
      console.log(this.menu)
      console.log(hasAuth(this.menu, 'GET:/api/admin/user/{uid}/kick'))
    },
    /**
     * 编辑按钮点击事件
     * @param scope
     */
    handleUpdate: function (scope) {
      this.form.avatarPictureUid = scope.row.userInfo.avatar.uid
      this.form.nickname = scope.row.userInfo.nickname
      this.form.roleUid = scope.row.roleUid
      this.form.status = scope.row.status
      this.form.username = scope.row.username
      this.form.birthday = scope.row.userInfo.birthday
      this.form.summary = scope.row.userInfo.summary
      this.avatarUrl = scope.row.userInfo.avatar.fileUrl
      this.editingUserUid = scope.row.uid

      this.beforeShow("修改用户", 1)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /**
     * 图片上传之前的验证
     * @param file
     * @returns {boolean}
     */
    uploadBefore: function (file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    /**
     * 图片上传
     * @param param
     */
    uploadSectionFile: function (param) {
      this.$confirm('会直接修改头像，是否确定？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.openLoading();
        let file = param.file
        // FormData 对象
        var formData = new FormData()
        // 文件对象
        formData.append('file', file)
        avatarUpload(this.editingUserUid, formData).then(res => {
          this.form.avatarPictureUid = res.data.uid
          this.avatarUrl = res.data.fileUrl
          this.fetchUserList()
          this.loading.close()
        })
      }).catch(() => {});

    },

    submit: function () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.isEditForm) {
            updateUserInfo(this.editingUserUid, this.form).then(res => {
              this.$message.success("修改成功")
              this.fetchUserList()
              this.dialogFormVisible = false;
              this.close()
            }).catch(err => {
              console.error(err)
            })
          } else {
            // create(this.form).then(res => {
            //   this.$message.success("添加成功")
            //   this.userData.unshift(res.data)
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
