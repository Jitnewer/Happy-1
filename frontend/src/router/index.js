import { createRouter, createWebHashHistory } from 'vue-router'
import Welcome from '@/components/Welcome.vue'
const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'welcome',
    component: Welcome
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
