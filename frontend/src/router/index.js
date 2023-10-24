import { createRouter, createWebHashHistory } from 'vue-router'
import AdminEventView from '@/components/AdminEventView.vue'
import EventDetailsView from '@/components/EventDetailsView.vue'
import AdminStatistics from '@/components/AdminStatistics.vue'

const routes = [
  {
    path: '/admin/event',
    component: AdminEventView,
    children: [
      { path: ':id', component: EventDetailsView }
    ]
  },
  {
    path: '/admin/statistics',
    component: AdminStatistics
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
