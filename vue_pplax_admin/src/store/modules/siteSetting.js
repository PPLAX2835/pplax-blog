import {getSiteSettingMap} from "../../api/siteSetting";

const siteSetting = {
  state: {
    map: {}
  },

  mutations: {
    SET_SITE_SETTING: (state, siteSetting) => {
      state.map = siteSetting
    },
  },

  actions: {
    // 获取网站配置
    getSiteSetting({ commit }) {
      return new Promise((resolve, reject) => {

        getSiteSettingMap().then(response => {
          const data = response.data
          // 存siteSetting
          commit('SET_SITE_SETTING', data)

          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
  }

}

export default siteSetting
