<template>
  <div>
    <div @click="handleOpen">
      <el-icon class="el-icon-key" style="height: 2.5em;font-size: 18px;cursor: pointer" ></el-icon>
    </div>
    <div>
      <el-dialog width="500px" title="修改密码" :visible.sync="dialogFormVisible">
        <el-form ref="passwordForm" label-width="130px" :rules="rule" :model="form">
          <el-form-item prop="email" label="邮箱" :label-width="formLabelWidth">
            <el-input style="width: 250px" v-model="form.email" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="password" label="新密码" :label-width="formLabelWidth">
            <el-input type="password" style="width: 250px" v-model="form.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="code" label="邮箱验证码" :label-width="formLabelWidth">
            <el-row>
              <el-col :span="8">
                <el-input v-model="form.code" autocomplete="off"></el-input>
              </el-col>
              <el-col :span="4">
                <el-button v-if="countdown === 0" @click="handleSendEmailCode" type="primary">发送邮箱验证码</el-button>
                <el-button v-else disabled>{{ countdown }}秒后可重新发送</el-button>
              </el-col>
            </el-row>
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
import {editPassword, getEmailCaptcha, logout} from "../../../../api/auth";
import { removeToken, removeUserUid } from '../../../../utils/auth'
import {isUsernameExist} from "../../../../api/user";
export default {
  name: 'Password',
  data() {
    return {
      formLabelWidth: '120px',
      dialogFormVisible: false,
      form: {
        email: null,
        password: null,
        code: null
      },
      countdown: 0,
      countdownTimer: null,
      rule: {
        password: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^[0-9a-zA-Z_]+$/, message: '请輸入字码、数字、下划线格式', trigger: 'blur' },
          { min: 8, max: 16, message: '长度在8到16个字符' },
        ],
        code: [
          { required: true, message: '请输入', trigger: 'blur' },
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { pattern: /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/, message: '请输入合法的邮箱', trigger: 'blur' },
          { validator: this.isEmailCorrect, trigger: 'blur' },
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
    handleSendEmailCode() {
      getEmailCaptcha(this.$store.state.user.email).then(res => {
        this.$message.success("邮件已发送，请注意查收");
        this.countdown = 60
        let that = this
        this.countdownTimer = setInterval(function () {
          that.countdown--
          if (that.countdown === 0) {
            clearInterval(that.countdownTimer)
          }
        }, 1000)
      })
    },

    /**
     * 检查邮箱是否是自己的邮箱
     * @param rule
     * @param value
     * @param callback
     */
    isEmailCorrect(rule, value, callback) {
      if (this.$store.state.user.email !== value) {
          callback(new Error('请输入自己的邮箱'))
      } else {
        callback()
      }
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
