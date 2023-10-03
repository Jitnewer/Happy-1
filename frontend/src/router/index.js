import { createRouter, createWebHashHistory } from 'vue-router'
import Registration from '@/components/Registration.vue'

const routes = [
  {
    path: '/sign-up',
    name: 'register',
    component: Registration
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
