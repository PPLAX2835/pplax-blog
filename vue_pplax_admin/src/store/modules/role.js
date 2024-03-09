import { getRoleList } from "../../api/role"

const role = {
  state: {
    roleList: []
  },

  mutations: {
    SET_ROLE_LIST: (state, roleList) => {
      state.roleList = roleList
    },
  },

  actions: {
    // 获取角色列表
    getRoleList({ commit }) {
      return new Promise((resolve, reject) => {

        getRoleList().then(response => {
          const data = response.data
          // 存roleList
          commit('SET_ROLE_LIST', data)

          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
  }

}

export default role
