import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '../views/LoginView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/board',
    name: 'board',
    component: () => import(/* webpackChunkName: "about" */ '../views/BoardView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import(/* webpackChunkName: "about" */ '../views/RegisterView.vue')
  },
  {
    path: '/write',
    name: 'write',
    component: () => import(/* webpackChunkName: "about" */ '../views/PostWriteView.vue')
  },
  {
    path: '/detail',
    name: 'detail',
    component: () => import(/* webpackChunkName: "about" */ '../views/PostDetailView.vue'),
  },
  {
    path: '/edit',
    name: 'edit',
    component: () => import(/* webpackChunkName: "about" */ '../views/PostEditView.vue')
  },
  {
    path: '/myinfo',
    name: '/myinfo',
    component: () => import(/* webpackChunkName: "about" */ '../views/MyInfoView.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
