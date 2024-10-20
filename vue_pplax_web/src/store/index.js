import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    skin: 'shallow',//shallow浅色模式  //deep深色模式
    loginFlag: false,
    drawer: false,
    searchDrawer: false,
    searchDialogVisible: false,
    siteAccess: 0,
    visitorAccess: 0,
    startRunTime: 0,
    systemNotcie: {},
    user: sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : null,
    isCommentFlag: false,
    webSiteInfo: {
      "loginTypeList": [],
      "showList": []
    },
  },
  mutations: {
    closeModel(state) {
      state.loginFlag = false;
    },
    isCommentFlag(state, newValue) {
      state.isCommentFlag = newValue
    },
    setWebSiteInfo(state, newValue) {
      state.webSiteInfo = newValue
    },
    setSkin(state, newValue) {
      state.skin = newValue
    },
    setDrawer(state, newValue) {
      state.drawer = newValue
    },
    setSearchDrawer(state, newValue) {
      state.searchDrawer = newValue
    },
    setLoginFlag(state, newValue) {
      state.loginFlag = newValue
    },
    setUser(state, newValue) {
      state.user = newValue
      sessionStorage.setItem("user", JSON.stringify(newValue))
    },
    setSystemNotice(state, newValue) {
      state.systemNotcie = newValue
    },
  },
})