import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import AdminStatistics from '@/components/admin/AdminStatistics.vue'
import Events from '@/components/events/Events.vue'
import EventsDetails from '@/components/events/EventsDetails.vue'
import WelcomePage from '@/components/welcomePage/Welcome.vue'
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
import ResearchesAdmin from '@/components/admin/researches/ResearchesAdmin.vue'
import ResearchAdminCreate from '@/components/admin/researches/ResearchAdminCreate.vue'
import ResearchAdminEdit from '@/components/admin/researches/ResearchAdminEdit.vue'
import NetworksDetails from '@/components/networks/NetworkDetails.vue'
import Networks from '@/components/networks/Networks.vue'
import EventsAdmin from '@/components/admin/events/EventsAdmin.vue'
import EventAdminCreate from '@/components/admin/events/EventAdminCreate.vue'
import EventAdminEdit from '@/components/admin/events/EventAdminEdit.vue'
import NetworksAdmin from '@/components/admin/networks/NetworksAdmin.vue'
import NetworkAdminCreate from '@/components/admin/networks/NetworkAdminCreate.vue'
import NetworkAdminEdit from '@/components/admin/networks/NetworkAdminEdit.vue'

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
    path: '/news/networks',
    name: 'networks',
    component: Networks
  },
  {
    path: '/news/networks/:id',
    name: 'network',
    component: NetworksDetails
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
    component: EventsAdmin
  },
  {
    path: '/admin/events/create',
    name: 'adminEventCreate',
    component: EventAdminCreate
  },
  {
    path: '/admin/events/edit/:id',
    name: 'adminEventEdit',
    component: EventAdminEdit
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
    path: '/admin/researches',
    name: 'adminResearches',
    component: ResearchesAdmin
  },
  {
    path: '/admin/researches/create',
    name: 'adminResearchCreate',
    component: ResearchAdminCreate
  },
  {
    path: '/admin/researches/edit/:id',
    name: 'adminResearchEdit',
    component: ResearchAdminEdit
  },
  {
    path: '/admin/networks',
    name: 'adminNetworks',
    component: NetworksAdmin
  },
  {
    path: '/admin/networks/create',
    name: 'adminNetworkCreate',
    component: NetworkAdminCreate
  },
  {
    path: '/admin/networks/edit/:id',
    name: 'adminNetworkEdit',
    component: NetworkAdminEdit
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
