<template>

  <div>
    <el-menu class="navbar" mode="horizontal">
      <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/><breadcrumb />

      <div class="right-menu">
        <el-tooltip effect="dark" content="修改密码" placement="bottom">
          <password class=" right-menu-item"></password>
        </el-tooltip>

        <el-dropdown class="avatar-container">
          <div class="avatar-wrapper">
            <img :src="avatar" class="user-avatar">
            <i class="el-icon-caret-bottom" />
          </div>
          <el-dropdown-menu slot="dropdown" class="user-dropdown">
            <el-dropdown-item>
              <router-link to="/">
                <i class="el-icon-s-home"></i>
                首页
              </router-link>
            </el-dropdown-item>
            <el-dropdown-item divided>
              <span style="display:block;" @click="logout">
                <i class="el-icon-switch-button"></i>
                退出登录
              </span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>


    </el-menu>

  </div>



</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import Password from './password'

export default {
  data() {
    return {
      formLabelWidth: '120px',
      dialogFormVisible: false,
      rules: {
        level: [
          { required: true, message: '请输入推荐等级', trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入标签名', trigger: 'blur' },
          { min: 1, max: 20, message: '标签名长度限制在1到20之间', trigger: 'change' },
          { validator: this.isExist, trigger: 'change' },
        ]
      },
      form: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
    }
  },
  components: {
    Breadcrumb,
    Hamburger,
    Password
  },
  created() {
    this.$store.dispatch('getCurrentUserInfo')
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar'
    ])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
      this.$store.dispatch('logout').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    }
  }
}
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  border-radius: 0px !important;

  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
      //transition: all .5s;

      &:hover {
        // transform: scale(1.1);
      }
    }

    .international {
      vertical-align: top;
    }

    .theme-switch {
      vertical-align: 15px;
    }

    .avatar-container {
      height: 50px;
      margin-right: 30px;

      .avatar-wrapper {
        cursor: pointer;
        margin-top: 5px;
        position: relative;

        .user-avatar {
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>

