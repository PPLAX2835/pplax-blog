import { login, logout, getInfo } from '@/api/auth'
import { getToken, setToken, removeToken } from '@/utils/auth'

const user = {
  state: {
    token: '',
    uid: '',
    userInfoUid: '',
    name: '',
    avatar: '',
    menu: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_UID: (state, uid) => {
      state.uid = uid
    },
    SET_USER_INFO_UID: (state, uid) => {
      state.userInfoUid = uid
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_MENU: (state, menu) => {
      state.menu = menu
    }
  },

  actions: {
    // 获取token
    getToken({ commit }, loginForm) {
      loginForm.username.trim()
      loginForm.password.trim()
      loginForm.nonceStr.trim()
      return new Promise((resolve, reject) => {

        login(loginForm).then(response => {
          const data = response.data
          // 存token
          setToken('Bearer ' + data.access_token)
          commit('SET_TOKEN', 'Bearer ' + data.access_token)
          // 存用户uid
          commit('SET_UID', data.uid)
          // 存用户信息uid
          commit('SET_USER_INFO_UID', data.userInfoUid)
          // 存昵称
          commit('SET_NAME', data.userInfo.nickname)
          // 存头像
          commit('SET_AVATAR', data.userInfo.avatar.fileUrl)
          // 存菜单
          commit('SET_MENU', data.role.menuList)

          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
