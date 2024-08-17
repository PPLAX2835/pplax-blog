import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import role from "./modules/role"
import user from './modules/user'
import tagsView from './modules/tagsView'
import siteSetting from "./modules/siteSetting";
import settings from './modules/settings'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    role,
    user,
    siteSetting,
    settings,
    tagsView
  },
  getters
})

export default store
