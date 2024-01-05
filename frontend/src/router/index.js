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
import UserProfilePage from '@/components/user/UserProfilePage.vue'
import PageNotFound from '@/components/PageNotFound.vue'
import UserProfileInfo from '@/components/user/UserProfileInfo.vue'
import ProfilePage from '@/components/admin/userPage/ProfilePage.vue'
import UserProfileEvents from '@/components/user/UserProfileEvents.vue'
import Challenges from '@/components/challenges/Challenges.vue'
import ChallengeDetails from '@/components/challenges/ChallengeDetails.vue'
import Researches from '@/components/research/Researches.vue'
import ResearchDetails from '@/components/research/ResearchDetails.vue'
import welcomeAdmin from '@/components/admin/welcomeAdmin.vue'
import News from '@/components/News.vue'
import ChallengesAdmin from '@/components/admin/challenges/ChallengesAdmin.vue'
import ChallengeAdminCreate from '@/components/admin/challenges/ChallengeAdminCreate.vue'
import ChallengeAdminEdit from '@/components/admin/challenges/ChallengeAdminEdit.vue'

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
    path: '/news',
    name: 'news',
    component: News
  },
  {
    path: '/news/challenges/:id',
    name: 'challenge',
    component: ChallengeDetails
  },
  {
    path: '/news/researches',
    name: 'researches',
    component: Researches
  },
  {
    path: '/news/researches/:id',
    name: 'research',
    component: ResearchDetails
  },
  {
    path: '/about',
    name: 'about',
    component: AboutUs
  },
  {
    path: '/admin',
    name: 'admin',
    component: welcomeAdmin
  },
  {
    path: '/admin/events',
    name: 'adminEvents',
    component: AdminEventView
  },
  {
    path: '/admin/events/:id',
    name: 'adminEventDetail',
    component: EventDetailsView
  },
  {
    path: '/admin/challenges',
    name: 'adminChallenges',
    component: ChallengesAdmin
  },
  {
    path: '/admin/challenges/create',
    name: 'adminChallengeCreate',
    component: ChallengeAdminCreate
  },
  {
    path: '/admin/challenges/edit/:id',
    name: 'adminChallengeEdit',
    component: ChallengeAdminEdit
  },
  {
    path: '/admin/statistics',
    name: 'statistics',
    component: AdminStatistics
  },
  {
    path: '/admin/users',
    name: 'users',
    component: AdminUsersView,
    children: [
      {
        path: 'profile/:id',
        name: 'adminProfileView',
        component: ProfilePage,
        children: [
          {
            path: 'info',
            name: 'adminProfileViewInfo',
            component: UserProfileInfo
          },
          {
            path: 'events',
            name: 'adminProfileViewEvents',
            component: UserProfileEvents
          }
        ]
      },
      {
        path: ':id',
        name: 'userDetail',
        component: AdminUsersDetail
      }
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
  },
  {
    path: '/profile',
    name: 'profilePage',
    component: UserProfilePage,
    children: [
      {
        path: 'events',
        name: 'profilePageEvents',
        component: UserProfileEvents
      },
      {
        path: 'info',
        name: 'profilePageInfo',
        component: UserProfileInfo
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    component: PageNotFound
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
