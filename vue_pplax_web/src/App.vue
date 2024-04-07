<template>
  <div id="app">
    <Denglong />
    <!-- 头部 -->
    <Header :userInfo=userInfo></Header>
    <!-- 侧边导航栏 -->
    <SideNavBar></SideNavBar>
    <Loading></Loading>
    <!-- <Notice></Notice> -->
    <router-view :key="$route.fullPath" style="min-height: 80vh;" />

    <!-- 登录模态框 -->
    <Login></Login>
    <SearchModle></SearchModle>
    <!-- 侧边栏 -->
    <Sidebar></Sidebar>

    <!-- 底部 -->
    <Footer></Footer>
  </div>
</template>

<script>
import Denglong from '@/components/denglong/index.vue'
import Header from '@/components/layout/Header.vue'
import Footer from '@/components/layout/Footer.vue'
import SideNavBar from "@/components/layout/SideNavBar.vue";
import Sidebar from '@/components/layout/Sidebar.vue'
import Loading from '@/components/loading/loading.vue'
import Login from '@/components/model/Login.vue'
import SearchModle from '@/components/model/Search.vue'
import Notice from '@/view/notice/topNotice.vue'
import { report, getWebSiteInfo, selectUserInfoByToken } from '@/api'
import { getToken, setToken } from '@/utils/cookieUtil'

export default {
  name: 'App',
  components: {
    Header,
    Footer,
    Sidebar,
    Login,
    SideNavBar,
    SearchModle,
    Loading,
    Notice,
    Denglong
  },
  data() {
    return {
      userInfo: this.$store.state.userInfo,
    }
  },
  created() {
    this.getUserInfo()
    this.initWebSiteInfo()
    this.report()
    var that = this

    //监听整个页面的 copy 事件
    document.addEventListener('copy', function (e) {
      let clipboardData = e.clipboardData || window.clipboardData;
      if (!clipboardData) return;
      let text = window.getSelection().toString();
      if (text) {
        e.preventDefault();
        clipboardData.setData('text/plain', text)
        that.$toast.success("复制成功,转载请务必保留原文链接!")
      }
    })

    document.addEventListener('keydown', (event) => {
      if (event.ctrlKey && event.key === 'k') {
        this.$store.state.searchDialogVisible = true
        event.preventDefault(); // 阻止默认行为  
      }
    });

  },

  methods: {
    initWebSiteInfo() {
      getWebSiteInfo().then(res => {
        this.$store.commit("setWebSiteInfo", res.data)
        this.$store.state.siteAccess = res.extra.siteAccess
        this.$store.state.visitorAccess = res.extra.visitorAccess
      })
    },
    report() {
      report().then(res => {
      });
    },
    getUserInfo() {
      let flag = window.location.href.indexOf("token") != -1
      if (flag) {
        let token = window.location.href.split("token=")[1]
        setToken(token)
      }

      // 从cookie中获取token
      let token = getToken()
      if (token != null) {
        selectUserInfoByToken(token).then(res => {
          this.userInfo = res.data
          this.$store.commit("setUserInfo", res.data)
        })
      }
    },
  },

  mounted() {
    let theme = sessionStorage.getItem("theme")
    if (theme == null) {
      theme = "light"
    }
    document.documentElement.dataset.theme = theme
  },

}
</script>

<style lang="scss" scoped>
#app {

  background: var(--body-color);
  // background-color: #efefef;
  // background-image: linear-gradient(90deg, rgba(60, 10, 30, .04) 3%, transparent 0), linear-gradient(1turn, rgba(60, 10, 30, .04) 3%, transparent 0);
  // background-size: 20px 20px;
  // background-position: 50%;
  // background-repeat: repeat;
  // &::before {
  //   content: '';
  //   z-index: -1;
  //   background: url(https://img.shiyit.com/whitebackimg.jpg) center 0 no-repeat;
  //   transition: opacity 1s;
  //   background-size: cover !important;
  //   position: fixed;
  //   top: 0;
  //   left: 0;
  //   right: 0;
  //   bottom: 0;
  // }


}
</style>
