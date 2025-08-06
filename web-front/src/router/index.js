<<<<<<< HEAD
import { createRouter, createWebHashHistory } from 'vue-router';
import Homepage from '../pages/Homepage.vue';
import Login from '../pages/Login.vue';
import UserInfo from '../pages/UserInfo.vue';
import Project from '../pages/CreateProject.vue';
import MyProject from '../pages/MyProject.vue';
import { compile } from 'vue';
=======
import { createRouter, createWebHashHistory } from "vue-router";
import Homepage from '../views/Homepage.vue'
import Login from '../views/Login.vue'
import UserInfo from "../views/UserInfo.vue";
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5

const routes = [
  {
    path: '/login',
    name: 'login',
<<<<<<< HEAD
    component: Login,
=======
    component: Login
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5
  },
  {
    path: '/',
    name: 'home',
<<<<<<< HEAD
    component: Homepage,
=======
    component: Homepage
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5
  },
  {
    path: '/userinfo',
    name: 'userinfo',
<<<<<<< HEAD
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
=======
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
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5
