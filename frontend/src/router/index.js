import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import AdminEventView from '@/components/admin/eventPage/AdminEventView.vue'
import EventDetailsView from '@/components/admin/eventPage/EventDetailsView.vue'
import AdminStatistics from '@/components/admin/AdminStatistics.vue'
import Events from '@/components/events/Events.vue'
import EventsDetails from '@/components/events/EventsDetails.vue'
import WelcomePage from '@/components/welcomePage/Welcome.vue'
import SuperUserView from '@/components/admin/superUser/SuperUserView.vue'
import SuperUserDetail from '@/components/admin/superUser/SuperUserDetail.vue'
import AdminUsersView from '@/components/admin/userPage/AdminUsersView.vue'
import AdminUsersDetail from '@/components/admin/userPage/AdminUsersDetail.vue'
import Registration from '@/components/Registration.vue'
import AboutUs from '@/components/AboutUs.vue'
import logIn from '@/components/Login.vue'

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
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
    path: '/about',
    name: 'about',
    component: AboutUs
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
  },
  {
    path: '/register',
    name: 'register',
    component: Registration
  },
  {
    path: '/login',
    name: 'login',
    component: logIn
  },
  {
    path: '/about-us',
    name: 'AboutUs',
    component: AboutUs
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
