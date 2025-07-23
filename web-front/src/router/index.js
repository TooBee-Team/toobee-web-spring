import { createRouter, createWebHashHistory } from 'vue-router';
import Homepage from '../pages/Homepage.vue';
import Login from '../pages/Login.vue';
import UserInfo from '../pages/UserInfo.vue';
import Project from '../pages/CreateProject.vue';
import MyProject from '../pages/MyProject.vue';
import { compile } from 'vue';

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
  {
    path: '/',
    name: 'home',
    component: Homepage,
  },
  {
    path: '/userinfo',
    name: 'userinfo',
    component: UserInfo,
  },
  {
    path: '/project',
    name: 'project',
    component: Project,
  },
  {
    path: '/myProject',
    name: 'myProject',
    component: MyProject
  }

];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach(async (to, form, next) => {
  if (to.path === '/login') {
    next();
  } else {
    try {
      const res = await fetch('/user/getUserInfo', { credentials: 'include' });
      if (res.status === 401) {
        next('/login');
      } else {
        next();
      }
    } catch (e) {
      next('/login');
    }
  }
});

export default router;
