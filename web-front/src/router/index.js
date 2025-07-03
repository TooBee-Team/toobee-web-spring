import { createRouter, createWebHashHistory } from "vue-router";
import Homepage from '../views/Homepage.vue'
import Login from '../views/Login.vue'
import UserInfo from "../views/UserInfo.vue";

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/',
    name: 'home',
    component: Homepage
  },
  {
    path: '/userinfo',
    name: 'userinfo',
    component: UserInfo
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach(async (to, form, next) => {
  if (to.path === '/login') {
    next()
  } else {
    try {
      const res = await fetch('/user/getUserInfo', { credentials: 'include' })
      if (res.status === 401) {
        next('/login')
      } else {
        next()
      }
    } catch (e) {
      next('/login')
    }
  }
})

export default router