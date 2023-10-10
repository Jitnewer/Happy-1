import { createRouter, createWebHashHistory } from 'vue-router'
import Vuex from 'vuex'
import AdminEventView from '@/components/AdminEventView.vue'
import EventDetailsView from '@/components/EventDetailsView.vue'
import WelcomePage from '@/components/Welcome.vue'

const routes = [
  {
    path: '/admin/events',
    component: AdminEventView,
    children: [
      { path: ':id', component: EventDetailsView }
    ]
  },
  {
    path: '/home',
    name: 'welcome',
    component: WelcomePage
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
