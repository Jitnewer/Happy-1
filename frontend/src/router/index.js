import { createRouter, createWebHashHistory } from 'vue-router'
import WelcomePage from '@/components/Welcome.vue'
const routes = [
  {
    path: '/home',
    name: 'welcome',
    component: WelcomePage
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
