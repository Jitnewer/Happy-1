import { createRouter, createWebHashHistory } from 'vue-router'
import AdminEventView from '@/components/AdminEventView.vue'
import EventDetailsView from '@/components/EventDetailsView.vue'
import AboutUs from '@/components/AboutUs.vue'

const routes = [
  {
    path: '/about-us',
    component: AboutUs
  },
  {
    path: '/admin/event',
    component: AdminEventView,
    children: [
      { path: ':id', component: EventDetailsView }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
