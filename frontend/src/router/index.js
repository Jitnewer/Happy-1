import { createRouter, createWebHashHistory } from 'vue-router'
import AboutUs from '@/components/AboutUs.vue'

const routes = [
  // { path: '/', component: Home},
  { path: '/about-us', component: AboutUs }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
