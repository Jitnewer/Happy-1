import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import AdminEventView from '@/components/AdminEventView.vue'
import EventDetailsView from '@/components/EventDetailsView.vue'
import AdminStatistics from '@/components/admin/AdminStatistics.vue'
import Events from '@/components/events/Events.vue'
import EventsDetails from '@/components/events/EventsDetails.vue'
import WelcomePage from '@/components/welcomePage/Welcome.vue'
import SuperUserView from '@/components/admin/SuperUserView.vue'
import SuperUserDetail from '@/components/admin/SuperUserDetail.vue'
import AdminUsersView from '@/components/admin/AdminUsersView.vue'
import AdminUsersDetail from '@/components/admin/AdminUsersDetail.vue'
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
  },
  {
    path: '/admin/users',
    component: AdminUsersView,
    children: [
      { path: ':id', component: AdminUsersDetail }
    ]
  },
  {
    path: '/superuser/news',
    component: SuperUserView,
    children: [
      { path: ':id', component: SuperUserDetail }
    ]
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
