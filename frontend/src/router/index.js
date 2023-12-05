import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import AdminEventView from '@/components/admin/eventPage/AdminEventView.vue'
import EventDetailsView from '@/components/admin/eventPage/EventDetailsView.vue'
import AdminStatistics from '@/components/admin/AdminStatistics.vue'
import Events from '@/components/events/Events.vue'
import EventsDetails from '@/components/events/EventsDetails.vue'
import WelcomePage from '@/components/welcomePage/Welcome.vue'
import SuperUserDetail from '@/components/admin/superUser/research/SuperUserResearch.vue'
import AdminUsersView from '@/components/admin/userPage/AdminUsersView.vue'
import AdminUsersDetail from '@/components/admin/userPage/AdminUsersDetail.vue'
import Registration from '@/components/Registration.vue'
import AboutUs from '@/components/AboutUs.vue'
import logIn from '@/components/Login.vue'
import Challenges from '@/components/challenges/Challenges.vue'
import ChallengeDetails from '@/components/challenges/ChallengeDetails.vue'
import SuperUserResearchView from '@/components/admin/superUser/research/SuperUserResearchView.vue'
import SuperUserChallenge from '@/components/admin/superUser/challenge/SuperUserChallenge.vue'
import SuperUserChallengeView from '@/components/admin/superUser/challenge/SuperUserChallengeView.vue'

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
    component: Events
  },
  {
    path: '/events/:id',
    name: 'event',
    component: EventsDetails
  },
  {
    path: '/news/challenges',
    name: 'challenges',
    component: Challenges
  },
  {
    path: '/news/challenges/:id',
    name: 'challenge',
    component: ChallengeDetails
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
      {
        path: ':id',
        component: EventDetailsView
      }
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
      {
        path: ':id',
        component: AdminUsersDetail
      }
    ]
  },
  {
    path: '/superuser/research',
    name: 'SuperUserResearch',
    component: SuperUserResearchView,
    children: [
      {
        path: ':id',
        name: 'SuperUserResearchDetail',
        component: SuperUserDetail
      }
    ]
  },
  {
    path: '/superuser/challenges',
    name: 'SuperUserChallenges',
    component: SuperUserChallengeView,
    children: [
      {
        path: ':id',
        name: 'SuperUserChallengeDetail',
        component: SuperUserChallenge
      }
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
