export const constantRoute = [
  {
    // 登录
    path: '/',
    name: 'home',
    component: () => import('@/views/home/index.vue'),
  },
  {
    // 分类
    path: '/category',
    name: 'category',
    component: () => import('@/views/category/index.vue'),
  },
  {
    // 我的
    path: '/user',
    name: 'user',
    component: () => import('@/views/user/index.vue'),
  },
  {
    // 关于
    path: '/about',
    name: 'about',
    component: () => import('@/views/about/index.vue'),
  },
  {
    // 关注
    path: '/follow',
    name: 'follow',
    component: () => import('@/views/follow/index.vue'),
  },
  {
    // 404路由
    path: '/404',
    name: '404',
    component: () => import('@/views/404/index.vue'),
  },
  {
    // 如果上边都没匹配上
    path: '/:pathMatch(.*)*',
    redirect: '/404',
    name: 'Any',
  },
]