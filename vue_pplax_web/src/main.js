import Vue from 'vue'
import App from './App.vue'
import './assets/font/iconfont.css'
import './assets/font/iconfont.js'
import store from './store'


import "../src/icons";
import { vueBaberrage } from 'vue-baberrage'
Vue.use(vueBaberrage)

import jsCookie from 'js-cookie'
Vue.prototype.$cookie = jsCookie;  // 在页面里可直接用 this.$cookie 调用

import VueViewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
Vue.use(VueViewer);


import element from '@/element/index'
Vue.use(element)


import Toast from '@/components/toast/index.vue';
const ToastPlugin = {
  install(Vue) {
    Vue.prototype.$toast = new Vue(Toast).$mount();
    document.body.appendChild(Vue.prototype.$toast.$el);
  },
};
Vue.use(ToastPlugin);


import hljs from 'highlight.js';

import 'highlight.js/styles/atom-one-dark-reasonable.css' //样式
//创建v-highlight全局指令
Vue.directive('highlight', function (el) {
  let blocks = el.querySelectorAll('pre code');
  blocks.forEach((block) => {
    hljs.highlightBlock(block)
  })
})

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

import Clipboard from 'clipboard'
Vue.prototype.Clipboard = Clipboard


import Loading from '@/components/loading/loading';
// 注册全局组件
Vue.component('loading', Loading);

// 创建一个 Vue 实例作为事件总线
Vue.prototype.$bus = new Vue();

import MetaInfo from 'vue-meta-info';
Vue.use(MetaInfo)

import Empty from '@/components/empty/index.vue'
Vue.component("sy-empty", Empty);
import pagination from '@/components/pagination/index.vue'
Vue.component("sy-pagination", pagination);

import VueLazyLoad from 'vue-lazyload'
// 2.注册插件
Vue.use(VueLazyLoad, {
  preLoad: 1,
  // 懒加载默认加载图片
  loading: 'http://file.pplax.xyz/default/lazy/20210622113505943.gif',
  // 加载失败后加载的图片
  error: 'http://file.pplax.xyz/default/lazy/R-C.png',
  attempt: 1
  // the default is ['scroll', 'wheel', 'mousewheel', 'resize', 'animationend', 'transitionend']
  // listenEvents: [ 'scroll' ]
})


import router from './router'

window.vm = new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app')

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})
router.afterEach(() => {
  window.scrollTo({
    top: 0,
    behavior: "instant"
  });
});


