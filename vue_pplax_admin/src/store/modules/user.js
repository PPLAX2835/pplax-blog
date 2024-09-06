import { login, logout } from '@/api/auth'
import { setToken, removeToken, setUserUid, getUserUid, removeUserUid } from '@/utils/auth'
import { getUserInfo, getRoleWithMenu } from '@/api/user';

const user = {
  state: {
    nickname: '',
    avatar: '',
    email: '',
    menu: [],
    roleName: ''
  },

  mutations: {
    SET_NICKNAME: (state, nickname) => {
      state.nickname = nickname
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_EMAIL: (state, email) => {
      state.email = email
    },
    SET_MENU: (state, menu) => {
      state.menu = menu
    },
    SET_ROLE_NAME: (state, roleName) => {
      state.roleName = roleName
    },
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
          // 存用户uid
          setUserUid(data.uid)

          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获得当前登录用户的信息
    getCurrentUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getUserInfo(getUserUid()).then(response => {
          const data = response.data
          const userInfo = data.userInfo
          // 存昵称
          commit('SET_NICKNAME', userInfo.nickname)
          // 存头像
          commit('SET_AVATAR', userInfo.avatar.fileUrl)
          // 存邮箱
          commit('SET_EMAIL', data.email)

          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获得当前用户的角色，包含菜单
    getCurrentUserRoleWithMenu({ commit }) {

      return new Promise((resolve, reject) => {
        getRoleWithMenu(getUserUid()).then(response => {
          const data = response.data

          // 存角色
          commit('SET_ROLE_NAME', data.roleName)
          // 存菜单
          commit('SET_MENU', data.menuList)

          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })

    },

    // 登出
    logout({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          removeToken()
          removeUserUid()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

  }
}

export default user
