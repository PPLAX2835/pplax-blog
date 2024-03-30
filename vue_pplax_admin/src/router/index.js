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
        path: 'admin',
        name: 'Admin',
        redirect: '/admin/dashboard',
        component: {      // 中间路径没有配置组件，但是作为容器显示下一级路径的组件
          render(c) { return c('router-view'); }
        },
        children: [

          {
            path: 'dashboard',
            name: 'Dashboard',
            meta: { title: '首页', icon: 'table', affix: true },
            component: () => import('@/views/dashboard/index'),
          },

          {
            path: 'blog',
            redirect: 'blog/index',
            component: {
              render(c) { return c('router-view'); }
            },
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
              }
            ]
          },

          {
            path: 'system',
            redirect: 'system/user',
            component: {
              render(c) { return c('router-view'); }
            },
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
                path: 'role',
                name: 'Role',
                component: () => import('@/views/role/index'),
                meta: { title: '角色管理', icon: 'table' }
              },
              {
                path: 'menu',
                name: 'Menu',
                component: () => import('@/views/menu/index'),
                meta: { title: '菜单管理', icon: 'table' }
              }
            ]
          },


          {
            path: 'message',
            redirect: 'message/comment',
            component: {
              render(c) { return c('router-view'); }
            },
            name: 'Message',
            meta: { title: '消息管理', icon: 'table' },
            children: [
              {
                path: 'comment',
                name: 'Comment',
                component: () => import('@/views/comment/index'),
                meta: { title: '消息管理', icon: 'table' }
              },
              {
                path: 'feedback',
                name: 'Feedback',
                component: () => import('@/views/feedback/index'),
                meta: { title: '反馈管理', icon: 'table' }
              }
            ]
          },


          {
            path: 'website',
            redirect: 'website/siteSetting',
            component: {
              render(c) { return c('router-view'); }
            },
            name: 'Message',
            meta: { title: '网站管理', icon: 'table' },
            children: [
              {
                path: 'siteSetting',
                name: 'SiteSetting',
                component: () => import('@/views/siteSetting/index'),
                meta: { title: '站点设置', icon: 'table' }
              },
              {
                path: 'link',
                name: 'Link',
                component: () => import('@/views/link/index'),
                meta: { title: '友链管理', icon: 'table' }
              },
              {
                path: 'fileStorage',
                name: 'FileStorage',
                component: () => import('@/views/fileStorage/index'),
                meta: { title: '文件管理', icon: 'table' }
              }
            ]
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
