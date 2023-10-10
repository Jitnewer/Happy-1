import { createRouter, createWebHashHistory } from 'vue-router'
import Vuex from 'vuex'
import AdminEventView from '@/components/AdminEventView.vue'
import EventDetailsView from '@/components/EventDetailsView.vue'
import WelcomePage from '@/components/Welcome.vue'
import AboutUs from '@/components/AboutUs.vue'
import Registration from '@/components/Registration.vue'

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
    path: '/admin/events',
    component: AdminEventView,
    children: [
      { path: ':id', component: EventDetailsView }
    ]
  },
  {
    path: '/about-us',
    name: 'AboutUs',
    component: AboutUs
  },
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
