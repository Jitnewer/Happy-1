import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import AdminEventView from '@/components/AdminEventView.vue'
import EventDetailsView from '@/components/EventDetailsView.vue'
import AdminStatistics from '@/components/AdminStatistics.vue'
import Events from '@/components/events/Events.vue'
import EventsDetails from '@/components/events/EventsDetails.vue'
import WelcomePage from '@/components/Welcome.vue'
const routes = [
  {
    path: '/home',
    name: 'welcome',
    component: WelcomePage
  },
  {
    path: '/events',
    name: 'events',
    component: Events,
    children: [
      {
        path: ':id',
        name: 'event',
        component: EventsDetails
      }
    ]
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
