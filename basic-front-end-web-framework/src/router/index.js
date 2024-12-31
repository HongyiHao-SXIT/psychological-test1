import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/
export const constantRouterMap = [
  {path: '/login', component: () => import('@/views/login/index'), hidden: true},
  {path: '/register', component: () => import('@/views/register/index'), hidden: true},
  {path: '/404', component: () => import('@/views/404'), hidden: true},
  {
    path: '',
    component: Layout,
    redirect: '/home',
    children: [{
      path: 'home',
      name: 'home',
      component: () => import('@/views/home/index'),
      meta: {title: '首页', icon: 'home'}
    }]
  }
]

export const asyncRouterMap = [
  {
    path:'/ums',
    component: Layout,
    redirect: '/ums/admin',
    name: 'ums',
    meta: {title: '权限', icon: 'ums'},
    children: [
      {
        path: 'admin',
        name: 'admin',
        component: () => import('@/views/ums/admin/index'),
        meta: {title: '用户列表', icon: 'ums-admin'}
      },
      {
        path: 'role',
        name: 'role',
        component: () => import('@/views/ums/role/index'),
        meta: {title: '角色列表', icon: 'ums-role'}
      },
      {
        path: 'allocMenu',
        name: 'allocMenu',
        component: () => import('@/views/ums/role/allocMenu'),
        meta: {title: '分配菜单'},
        hidden: true
      },
      {
        path: 'allocResource',
        name: 'allocResource',
        component: () => import('@/views/ums/role/allocResource'),
        meta: {title: '分配资源'},
        hidden: true
      },
      {
        path: 'menu',
        name: 'menu',
        component: () => import('@/views/ums/menu/index'),
        meta: {title: '菜单列表', icon: 'ums-menu'}
      },
      {
        path: 'addMenu',
        name: 'addMenu',
        component: () => import('@/views/ums/menu/add'),
        meta: {title: '添加菜单'},
        hidden: true
      },
      {
        path: 'updateMenu',
        name: 'updateMenu',
        component: () => import('@/views/ums/menu/update'),
        meta: {title: '修改菜单'},
        hidden: true
      },
      {
        path: 'resource',
        name: 'resource',
        component: () => import('@/views/ums/resource/index'),
        meta: {title: '资源列表', icon: 'ums-resource'}
      },
      {
        path: 'resourceCategory',
        name: 'resourceCategory',
        component: () => import('@/views/ums/resource/categoryList'),
        meta: {title: '资源分类'},
        hidden: true
      }
    ]
  },
  {
    path:'/wx',
    component: Layout,
    redirect: '/wx/banner',
    name: 'wx',
    meta: {title: '微信管理', icon: 'ums'},
    children: [
      {
        path: 'banner',
        name: 'banner',
        component: () => import('@/views/wx/banner/index'),
        meta: {title: '轮播图管理', icon: 'ums-admin'}
      },
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/wx/user/index'),
        meta: {title: '微信用户管理', icon: 'ums-admin'}
      },
      {
        path: 'counselor',
        name: 'counselor',
        component: () => import('@/views/wx/counselor/index'),
        meta: {title: '心理咨询师管理', icon: 'ums-admin'}
      },
      {
        path: 'article',
        name: 'article',
        component: () => import('@/views/wx/article/index'),
        meta: {title: '健康知识管理', icon: 'ums-admin'}
      },
      {
        path: 'mind',
        name: 'mind',
        component: () => import('@/views/wx/mind/index'),
        meta: {title: '问卷管理', icon: 'ums-admin'}
      },
      {
        path: 'mind-item',
        name: 'mind-item',
        hidden: true,
        component: () => import('@/views/wx/mind-item/index'),
        meta: {title: '题目管理', icon: 'ums-admin'}
      }
    ]
  },
  {
    path:'/system',
    component: Layout,
    redirect: '/system/person-info',
    name: 'system',
    meta: {title: '权限', icon: 'ums'},
    children: [
      {
        path: 'person-info',
        name: 'person-info',
        component: () => import('@/views/system/person-info/index'),
        meta: {title: '个人信息管理', icon: 'ums-admin'}
      }
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})

