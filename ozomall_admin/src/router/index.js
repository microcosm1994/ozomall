import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */
import componentsRouter from './modules/components'
import chartsRouter from './modules/charts'
import tableRouter from './modules/table'
import nestedRouter from './modules/nested' 
import settingRouter from './modules/setting' // 后台系统设置
import mallManage from './modules/mallManage' // 商城管理模块
import goodsManage from './modules/goodsManage' // 商品管理模块
import userManage from './modules/userManage' // 用户管理模块
export default new Router({
  routes: [
    {
      path: '/redirect',
      component: Layout,
      hidden: true,
      children: [
        {
          path: '/redirect/:path(.*)',
          component: () => import('@/views/redirect/index')
        }
      ]
    },
    {
      path: '/login',
      component: () => import('@/views/login/index'),
      hidden: true
    },
    {
      path: '/',
      component: Layout,
      redirect: '/dashboard',
      children: [
        {
          path: 'dashboard',
          component: () => import('@/views/dashboard/index'),
          name: 'Dashboard',
          meta: { title: '工作台', icon: 'el-icon-s-platform', affix: true }
        }
      ]
    },
    {
      path: '/guide',
      component: Layout,
      redirect: '/guide/index',
      children: [
        {
          path: 'index',
          component: () => import('@/views/guide/index'),
          name: 'Guide',
          meta: { title: 'Guide', icon: 'guide', noCache: true }
        }
      ]
    },
    {
      path: '/profile',
      component: Layout,
      redirect: '/profile/index',
      children: [
        {
          path: 'index',
          component: () => import('@/views/profile/index'),
          name: 'Profile',
          meta: { title: 'Profile', icon: 'user', noCache: true }
        }
      ]
    },
    {
      path: '/icons',
      component: Layout,
      redirect: '/icons/index',
      children: [
        {
          path: 'index',
          component: () => import('@/views/icons/index'),
          name: 'icons',
          meta: { title: 'icons', icon: 'user', noCache: true }
        }
      ]
    },
    componentsRouter,
    chartsRouter,
    tableRouter,
    nestedRouter,
    goodsManage,
    userManage,
    mallManage,
    settingRouter,
  ]
})
