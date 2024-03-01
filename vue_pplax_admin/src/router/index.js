  import Vue from 'vue'
  import Router from 'vue-router'

  // in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
  // detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

  Vue.use(Router)

  /* Layout */
  import Layout from '../views/layout/Layout'

  /**
   * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
   * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
   *                                if not set alwaysShow, only more than one route under the children
   *                                it will becomes nested mode, otherwise not show the root menu
   * redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
   * name:'router-name'             the name is used by <keep-alive> (must set!!!)
   * meta : {
      title: 'title'               the name show in submenu and breadcrumb (recommend set)
      icon: 'svg-name'             the icon show in the sidebar,
    }
  **/
  export const constantRouterMap = [
    { path: '/login', component: () => import('@/views/login/index'), hidden: true },
    { path: '/404', component: () => import('@/views/404'), hidden: true },

    {
      path: '/',
      component: Layout,
      redirect: '/admin/dashboard',
      name: 'Home',
      hidden: true,
      children: [

        {
          path: 'admin/dashboard',
          name: 'Dashboard',
          component: () => import('@/views/dashboard/index'),
        },

        {
          path: 'admin/blog',
          redirect: 'index',
          meta: { title: '博客管理', icon: 'table' },
          name: 'Blog',
          children: [
            {
              path: 'index',
              name: 'Blog',
              component: () => import('@/views/blog/index'),
              meta: { title: '博客管理', icon: 'table' },
            },
            {
              path: 'blogSort',
              name: 'BlogSort',
              component: () => import('@/views/blogSort/index'),
              meta: { title: '博客分类管理', icon: 'table' }
            },
            {
              path: 'comment',
              name: 'Comment',
              component: () => import('@/views/comment/index'),
              meta: { title: '评论管理', icon: 'table' }
            },
            {
              path: 'tag',
              name: 'Tag',
              component: () => import('@/views/tag/index'),
              meta: { title: '标签管理', icon: 'table' }
            },
            {
              path: 'collect',
              name: 'Collect',
              component: () => import('@/views/collect/index'),
              meta: { title: '收藏管理', icon: 'table' }
            }
          ]
        },

        {
          path: 'admin/system',
          redirect: 'user',
          name: 'System',
          meta: { title: '系统管理', icon: 'table' },
          children: [
            {
              path: 'user',
              name: 'User',
              component: () => import('@/views/user/index'),
              meta: { title: '用户管理', icon: 'table' }
            },
            {
              path: 'feedback',
              name: 'Feedback',
              component: () => import('@/views/feedback/index'),
              meta: { title: '反馈管理', icon: 'table' }
            },
            {
              path: 'link',
              name: 'Link',
              component: () => import('@/views/link/index'),
              meta: { title: '友情链接管理', icon: 'table' }
            }
          ]
        }




      ]
    },


    { path: '*', redirect: '/404', hidden: true }
  ]

  export default new Router({
    // mode: 'history', //后端支持可开
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRouterMap
  })
