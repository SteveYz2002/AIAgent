import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/love-master',
    name: 'love-master',
    component: () => import('../views/LoveMasterView.vue')
  },
  {
    path: '/super-agent',
    name: 'super-agent',
    component: () => import('../views/SuperAgentView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router 