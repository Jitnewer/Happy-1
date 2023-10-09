import { createRouter, createWebHashHistory } from 'vue-router'
import AdminEventView from '@/components/AdminEventView.vue'
import EventDetailsView from '@/components/EventDetailsView.vue'

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/adminEventView',
      component: AdminEventView,
      children: [
        { path: ':id', component: EventDetailsView }
      ]
    }
  ]
})

export default router
