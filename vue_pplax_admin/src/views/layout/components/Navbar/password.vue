<template>
  <div>
    <div @click="handleOpen">
      <el-icon class="el-icon-key" style="height: 2.5em;font-size: 18px;cursor: pointer" ></el-icon>
    </div>
    <div>
      <el-dialog width="500px" title="修改密码" :visible.sync="dialogFormVisible">
        <el-form ref="passwordForm" label-width="130px" :rules="rule" :model="form">
          <el-form-item prop="oldPassword" label="旧密码" :label-width="formLabelWidth">
            <el-input type="password" style="width: 250px" v-model="form.oldPassword" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="newPassword" label="新密码" :label-width="formLabelWidth">
            <el-input type="password" style="width: 250px" v-model="form.newPassword" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword" label="确认密码" :label-width="formLabelWidth">
            <el-input type="password" style="width: 250px" v-model="form.confirmPassword" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="edit">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { editPassword, logout } from "../../../../api/auth";
import { getToken, removeToken, removeUserUid } from '../../../../utils/auth'
export default {
  name: 'Password',
  data() {
    return {
      formLabelWidth: '120px',
      dialogFormVisible: false,
      form: {
        oldPassword: null,
        newPassword: null,
        confirmPassword: null,
      },
      rule: {
        oldPassword: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^[0-9a-zA-Z_]+$/, message: '请輸入字码、数字、下划线格式', trigger: 'blur' },
          // { min: 8, max: 16, message: '长度在8到16个字符' },
        ],
        newPassword: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^[0-9a-zA-Z_]+$/, message: '请輸入字码、数字、下划线格式', trigger: 'blur' },
          { min: 8, max: 16, message: '长度在8到16个字符' },
        ],
        confirmPassword: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^[0-9a-zA-Z_]+$/, message: '请輸入字码、数字、下划线格式', trigger: 'blur' },
          { min: 8, max: 16, message: '长度在8到16个字符' },
        ],
      }
    }
  },
  methods: {
    handleOpen() {
      console.log('open')
      this.dialogFormVisible = true
      this.form = {}
    },
    edit() {
      this.$refs['passwordForm'].validate((valid) => {
        if (valid) {
          if (this.form.newPassword !== this.form.confirmPassword) {
            this.$message.error("俩次密码输入不一致");
            return false;
          }
          editPassword(this.form).then(res => {
            this.$notify({
              title: '修改成功，请重新登陆',
              message: res.message,
              type: 'success'
            });
            this.dialogFormVisible = false
            logout().then(resp => {
              if (resp.code) {
                removeToken();
                removeUserUid();
                setTimeout(function () {
                  location.reload();
                }, 2000)
              }
            })
          }).catch(err => {
            console.log(err)
          })
        } else {
          console.log('no');
          return false;
        }
      });
    }
  }
}
</script>
<style scoped>
svg {
  transition: all .5s;
}

svg:hover {
  transform: scale(1.1);
}
</style>
