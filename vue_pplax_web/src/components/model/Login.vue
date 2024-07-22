<template>
    <div>
        <el-dialog :lock-scroll="false" :close-on-click-modal="false" class="dialog" center :title="title"
            :visible.sync="dialogFormVisible">
            <!-- 账号登录 -->
            <div v-if="index === 1">
                <el-form :model="form" :rules="rules" ref="ruleForm">
                    <el-form-item label="账号" :label-width="formLabelWidth" prop="username">
                        <el-input placeholder="请输入账号/邮箱" @keyup.enter.native="handleLogin" v-model="form.username"
                            autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
                        <el-input placeholder="请输入密码" @keyup.enter.native="handleLogin" v-model="form.password" autocomplete="off"
                            show-password></el-input>
                    </el-form-item>
                </el-form>
                <el-button type="success" class="loginBtn" @click="handleLogin" round>登录</el-button>

                <!-- 滑块验证 -->
                <el-dialog title="请拖动滑块完成拼图" width="360px" :visible.sync="isShowSliderVerify"
                           :close-on-click-modal="false" @closed="refresh" append-to-body>
                  <slider-verify ref="sliderVerify" @success="onSuccess" @fail="onFail" @again="onAgain" />
                </el-dialog>

                <div class="regitstBtn">
                    <a class="regist hand-style" @click="handleChangeLoginMethod(2)">账号注册</a>
                    <a class="forget hand-style" @click="handleChangeLoginMethod(3)">忘记密码</a>
                </div>

            </div>

            <!-- 注册 -->
            <div v-if="index === 2">
                <el-form :model="form" :rules="rules" ref="ruleForm" label-position="left">
                    <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
                        <el-input class="input" placeholder="请输入邮箱" v-model="form.email" autocomplete="off"></el-input>
                    </el-form-item>
                  <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
                    <el-input class="input" placeholder="请输入用户名" v-model="form.username" autocomplete="off"></el-input>
                  </el-form-item>
                    <el-form-item label="昵称" :label-width="formLabelWidth" prop="nickname">
                        <el-input class="input" placeholder="请输入昵称" v-model="form.nickname" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
                        <el-input class="input" placeholder="请输入密码" v-model="form.password" autocomplete="off"
                            show-password></el-input>
                    </el-form-item>
                    <el-form-item label="验证码" :label-width="formLabelWidth" prop="code">
                        <div style="display: flex;">
                            <el-input class="input" placeholder="请输入验证码" v-model="form.code" autocomplete="off"></el-input>
                            <a v-if="showSendBtnFlag" class="send hand-style" @click="handleSendEmailCode">发送</a>
                            <a v-else class="send hand-style">{{ countdown }}s</a>
                        </div>
                    </el-form-item>
                </el-form>

                <el-button type="danger" class="loginBtn hand-style" @click="register" round>注册</el-button>

                <div class="goLoginBtn">
                    已有账号，<a @click="handleChangeLoginMethod(1)" class="hand-style">去登录</a>
                </div>
            </div>

          <!-- 重置密码 -->
            <div v-if="index === 3">
                <el-form :model="form" :rules="rules" ref="ruleForm" label-position="left">
                    <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
                        <el-input class="input" placeholder="请输入邮箱" v-model="form.email" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" :label-width="formLabelWidth" prop="password">
                        <el-input class="input" placeholder="请输入密码" v-model="form.password" autocomplete="off"
                            show-password></el-input>
                    </el-form-item>
                    <el-form-item label="验证码" :label-width="formLabelWidth" prop="code">
                        <div style="display: flex;">
                            <el-input class="input" placeholder="请输入验证码" v-model="form.code" autocomplete="off"></el-input>
                            <a v-if="showSendBtnFlag" class="send hand-style" @click="handleSendEmailCode">发送</a>
                            <a v-else class="send">{{ countdown }}s</a>
                        </div>
                    </el-form-item>
                </el-form>

                <el-button type="primary" class="loginBtn hand-style" @click="forgetPassword" round>修改</el-button>

                <div class="goLoginBtn">
                    已有账号，<a @click="handleChangeLoginMethod(1)" class="hand-style">去登录</a>
                </div>
            </div>

        </el-dialog>

    </div>
</template>

<script>
import { wxIsLogin, getWechatLoginCode } from "@/api";
import { login, getEmailCaptcha, register, forgetPassword} from "@/api/auth";
import { isUsernameExist } from "@/api/user";
import { setUrl, setToken, setUserUid } from '@/utils/cookieUtil'
import sliderVerify from "@/components/sliderVerify/sliderVerify";

export default {
    data: function () {
        return {
            // 是否显示滑块验证
            isShowSliderVerify: false,
            form: {
              username: '',
              password: '',
              nonceStr: '',
              value: '',
              rememberMe: false
            },
            code: null,
            timer: null,
            index: 1,
            wechatLoginFlag: false,
            title: "账号密码登录",
            showSendBtnFlag: true,
            formLabelWidth: '80px',
            wechatLoginCode: null,
            countdown: 60, // 倒计时初始值为 60 秒
            rules: {
                username: [
                  { required: true, message: '请输入用户名', trigger: 'blur' },
                  { validator: this.isExist, trigger: 'change'},
                  { min: 3, max: 30, message: '长度在3到30个字符' },
                ],
                email: [
                    { required: true, message: '请输入邮箱', trigger: 'blur' },
                    { pattern: /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/, message: '请输入合法的邮箱', trigger: 'blur' },
                ],
                nickname: [
                    { required: true, message: '请输入昵称', trigger: 'blur' },
                    { min: 1, max: 20, message: '长度在1到20个字符' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 8, max: 16, message: '长度在8到16个字符' },
                ],
                code: [
                    { required: true, message: '请输入验证码', trigger: 'blur' },
                ]
            }
        };
    },

    components: { sliderVerify },
    computed: {
        dialogFormVisible: {
            set(value) {
                clearInterval(this.timer);
                this.$store.state.loginFlag = value;
            },
            get() {
                this.index = 1
                return this.$store.state.loginFlag;
            }
        },
    },
    methods: {
        handleChangeLoginMethod(condition) {
            this.form = {}
            if (condition === 1) {
                clearInterval(this.timer);
                this.title = "账号密码登录"
            } else if (condition === 2) {
                this.title = "邮箱注册"
            } else if (condition === 3) {
                this.title = "忘记密码"
            }
            this.index = condition;

        },
        forgetPassword() {
            this.$refs['ruleForm'].validate((valid) => {
                if (valid) {
                    forgetPassword(this.form).then(res => {

                        this.$toast.success('修改成功');
                        this.$store.state.loginFlag = true;
                        this.forgetFlag = false

                        this.index = 1
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        /**
         * 检查用户名是否存在
         * @param rule
         * @param value
         * @param callback
         */
        isExist(rule, value, callback) {
          if (this.index === 2 && value.trim() !== '') {
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
       * 注册
       */
        register() {
            this.$refs['ruleForm'].validate((valid) => {
                if (valid) {
                    register(this.form).then(res => {

                        this.$toast.success('注册成功');
                        this.$store.state.loginFlag = true;
                        this.index = 1
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
      /**
       * 获取邮箱验证码，即发送邮箱
       */
        handleSendEmailCode() {
            if (this.form.email == null || this.form.email === '') {

                this.$toast.error('请输入邮箱');
                return
            }
            getEmailCaptcha(this.form.email).then(res => {
                this.timer = setInterval(() => {
                    if (this.countdown > 0) {
                        this.showSendBtnFlag = false
                        this.countdown--;
                    } else {
                        this.showSendBtnFlag = true
                        clearInterval(this.timer);
                        this.timer = null;
                        this.countdown = 60
                    }
                }, 1000);

                this.$toast.success('验证码发送成功');
            })
        },
        handleRegister(type) {
            this.form = {}
            this.$store.state.loginFlag = false;
            if (type == 1) {
                this.emailRegistFlag = true
            } else {
                this.forgetFlag = true
            }
        },
        close() {
            clearInterval(this.timer);
            this.$store.state.loginFlag = false;
            this.wechatLoginFlag = false
        },
        /* 提交*/
        handleLogin() {
          let self = this;
          self.$refs['ruleForm'].validate((flag) => {
            self.isShowSliderVerify = flag;
          });
        },
        /* 刷新验证码*/
        refresh() {
          this.$refs.sliderVerify.refresh();
        },
        /* 滑动验证成功*/
        onSuccess(captcha) {
          Object.assign(this.form, captcha);
          this.form.nonceStr = captcha.nonceStr
          this.form.value = captcha.value
          this.login();
        },
        /* 滑动验证失败*/
        onFail(msg) {
          console.log(msg)
          //this.message('error', msg || '验证失败，请控制拼图对齐缺口');
        },
        /* 滑动验证异常*/
        onAgain() {
          this.$toast.error('滑动操作异常，请重试');
        },
        login() {
            let that = this
            this.$refs['ruleForm'].validate((valid) => {
                if (valid) {
                    login(this.form).then(res => {
                      console.log(res)
                        if (res.code !== 200) {
                          that.$toast.error(res.message);
                        } else {
                          setToken('Bearer ' + res.extra.access_token)
                          setUserUid(res.extra.uid)
                          that.$toast.success("登录成功");
                          setTimeout(function (){
                            location.reload()
                          }, 2000)
                        }
                      that.isShowSliderVerify = false
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
    }
};
</script>

<style lang="scss" scoped>
/deep/ .el-dialog__wrapper {
    background-image: url(../../../public/assets/img/background/1.png) !important;
}

.dialog {

    @media screen and (min-width: 1119px) {
        /deep/ .el-dialog {
            width: 25%;
            border-radius: 10px;
        }
    }



    .wxImg {
        width: 50%;
        display: block;
        margin: 0 auto;
    }


    .loginBtn {
        margin-top: 30px;
        text-align: center;
        display: block;
        margin: 0 auto;
        width: 50%;
    }

    .regitstBtn {

        .regist,
        .forget {


            &:hover {
                color: var(--theme-color);
            }
        }

        .forget {
            float: right;
        }
    }

    /deep/ .input input {
        border-top: none !important;
        border-left: none !important;
        border-right: none !important;
    }

    .send {
        width: 18%;
        color: var(--text-color);
        font-size: 14px;
        border: none;
        background-color: var(--background-color);

        &:hover {
            color: var(--theme-color)
        }
    }

    .goLoginBtn {
        margin-top: 20px;

        a {
            color: red;
        }
    }

    .social-login-title {
        margin-top: 1.5rem;
        color: #b5b5b5;
        font-size: 0.75rem;
        text-align: center;

        &::before {
            content: "";
            display: inline-block;
            background-color: #d8d8d8;
            width: 60px;
            height: 1px;
            margin: 0 12px;
            vertical-align: middle;
        }

        &::after {
            content: "";
            display: inline-block;
            background-color: #d8d8d8;
            width: 60px;
            height: 1px;
            margin: 0 12px;
            vertical-align: middle;
        }
    }

    .social-login-wrapper {
        margin-top: 1rem;
        font-size: 2rem;
        text-align: center;

        a {
            text-decoration: none;
            margin-left: 20px;

            svg {
                width: 30px;
                height: 30px;
            }
        }
    }
}
</style>
