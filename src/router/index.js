import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/test',
    component: () => import('@/views/dashboard/charts/age')
  },

]



export const asyncRoutes = [
  // 超级管理员管理普通管理员
  {
    path: '/user',
    component: Layout,
    redirect: '/user/managerMenu',
    name: 'User',
    meta: { title: '人员管理', icon: 'el-icon-user-solid', role: 'ROLE_MANAGER' },
    children: [
      {
        path: 'managerMenu',
        name: 'ManagerMenu',
        component: () => import('@/views/user/managerMenu/index'),
        meta: { title: '管理员菜单', icon: 'el-icon-s-management', role: 'ROLE_MANAGER'}
      },
      {
        path: 'touristMenu',
        name: 'TouristMenu',
        component: () => import('@/views/user/tourist/index'),
        meta: { title: '游客菜单', icon: 'users', role: 'ROLE_TOURIST'}
      },
      {
        path: 'permission',
        name: 'Permission',
        component: () => import('@/views/user/permission/index'),
        meta: { title: '权限管理', icon: 'table', role: 'ROLE_MANAGER', activeMenu: '/user/managerMenu' },  // activeMenu设置后进入权限页面管理员管理图标也高亮
        hidden: true
      },
    ]
  },

  // 订单管理
  {
    path: '/order',
    component: Layout,
    children: [
      {
        path: 'orderMenu',
        name: 'OrderMenu',
        component: () => import('@/views/order/index'),
        meta: { title: '订单菜单', icon: 'order', role: 'ROLE_ORDER'}
      }
    ]
  },

  // 旅馆管理
  {
    path: '/hotel',
    component: Layout,
    children: [
      {
        path: 'hotelMenu',
        name: 'HotelMenu',
        component: () => import('@/views/hotel/index'),
        meta: { title: '旅馆菜单', icon: 'hotel', role: 'ROLE_HOTEL' }
      }
    ]
  },

  // 景点管理
  {
    path: '/scenicSpot',
    component: Layout,
    children: [
      {
        path: 'scenicSpotMenu',
        name: 'scenicSpotMenu',
        component: () => import('@/views/scenicSpot/index'),
        meta: { title: '景点菜单', icon: 'scenic', role: 'ROLE_SCENIC' }
      }
    ]
  },


  // 美食管理
  {
    path: '/delicacy',
    component: Layout,
    children: [
      {
        path: 'delicacyMenu',
        name: 'DelicacyMenu',
        component: () => import('@/views/delicacy/index'),
        meta: { title: '美食菜单', icon: 'food', role: 'ROLE_DELICACY' }
      }
    ]
  },

  // 公告管理
  {
    path: '/notice',
    component: Layout,
    children: [
      {
        path: 'noticeMenu',
        name: 'NoticeMenu',
        component: () => import('@/views/notice/index'),
        meta: { title: '公告菜单', icon: 'nested', role: 'ROLE_NOTICE' }
      }
    ]
  },

  // 旅游线路管理
  {
    path: '/touristRoute',
    component: Layout,
    redirect: '/touristRoute/routeMenu',
    name: 'TouristRoute',
    meta: { title: '旅游线路管理', icon: 'route', role: 'ROLE_ROUTE' },
    children: [
      {
        path: 'routeMenu',
        name: 'RouteMenu',
        component: () => import('@/views/touristRoute/routeMenu/index'),
        meta: { title: '线路菜单', icon: 'route', role: 'ROLE_ROUTE' }
      },
      {
        path: 'routeSites',
        name: 'RouteSites',
        component: () => import('@/views/touristRoute/routeSites/index'),
        meta: { title: '线路站点', icon: 'route', role: 'ROLE_ROUTE', activeMenu: '/touristRoute/routeMenu' },
        hidden: true
      },
    ]
  },

  // 游记管理
  {
    path: '/travelNote',
    component: Layout,
    children: [
      {
        path: 'noteMenu',
        name: 'NoteMenu',
        component: () => import('@/views/travelNote/index'),
        meta: { title: '游记菜单', icon: 'note', role: 'ROLE_NOTE'  }
      }
    ]
  },

  // 反馈管理
  {
    path: '/feedback',
    component: Layout,
    children: [
      {
        path: 'feedbackMenu',
        name: 'FeedbackMenu',
        component: () => import('@/views/feedback/index'),
        meta: { title: '反馈菜单', icon: 'feedback', role: 'ROLE_FEEDBACK' }
      }
    ]
  },

  // 评论管理
  {
    path: '/comment',
    component: Layout,
    children: [
      {
        path: 'commentMenu',
        name: 'CommentMenu',
        component: () => import('@/views/comment/index'),
        meta: { title: '评论菜单', icon: 'comment', role: 'ROLE_COMMENT' }
      }
    ]
  },

  // 详情介绍页面管理
  {
    path: '/coordinate',
    component: Layout,
    children: [
      {
        path: 'hotelDescription',
        name: 'hotelDescription',
        component: () => import('@/views/coordinate/index'),
        meta: { title: '旅馆详情页', icon: 'comment', role: 'ROLE_HOTEL' },
        hidden: true
      },
      {
        path: 'delicacyDescription',
        name: 'delicacyDescription',
        component: () => import('@/views/coordinate/index'),
        meta: { title: '美食详情页', icon: 'comment', role: 'ROLE_DELICACY' },
        hidden: true
      },
      {
        path: 'scenicDescription',
        name: 'scenicDescription',
        component: () => import('@/views/coordinate/index'),
        meta: { title: '景点详情页', icon: 'comment', role: 'ROLE_SCENIC' },
        hidden: true
      },
    ]
  },

  // 文章编辑页面
  {
    path: '/article',
    component: Layout,
    children: [
      {
        path: 'edit',
        name: 'recommendArticle',
        component: () => import('@/views/article/index'),
        meta: { title: '文章编辑', icon: 'comment', role: 'ROLE_ARTICLE' },
        hidden: true
      }
    ]
  },

  // 图库
  {
    path: '/pictures',
    component: Layout,
    children: [
      {
        path: 'repository',
        name: 'pictures',
        component: () => import('@/views/pictures/index'),
        meta: { title: '宣传图库', icon: 'el-icon-picture', role: 'ROLE_PROPAGANDIST_PHOTOS' },
        hidden: false
      }
    ]
  },

  // 地理坐标信息
  {
    path: '/geoInfo',
    component: Layout,
    children: [
      {
        path: 'coordinate',
        name: 'coordinate',
        component: () => import('@/views/coordinate/index'),
        meta: { title: '地理位置信息', icon: 'el-icon-location', role: 'ROLE_COORDINATE' },
        hidden: true
      }
    ]
  },

    // 接口操作日志信息
    {
      path: '/operateLog',
      component: Layout,
      children: [
        {
          path: 'list',
          name: 'operateLog',
          component: () => import('@/views/operateLog/index'),
          meta: { title: '操作日志', icon: 'el-icon-document', role: 'ROLE_LOG' },
          hidden: false
        }
      ]
    },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }

]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
