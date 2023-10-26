import { createRouter, createWebHashHistory } from 'vue-router'
import Vuex from 'vuex'
import AdminEventView from '@/components/AdminEventView.vue'
import EventDetailsView from '@/components/EventDetailsView.vue'
import AdminStatistics from '@/components/AdminStatistics.vue'
import Events from '@/components/events/Events.vue'

const routes = [
  {
    path: '/events',
    component: Events
  },
  {
    path: '/admin/events',
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
