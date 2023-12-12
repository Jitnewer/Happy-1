<template>
    <component :is="navBar" @handleLogout="handleLogout"></component>
    <router-view v-if="navBar !== 'NavBarLoggedInAdminAndSuperUser'"  @loginAdmin="loginAdmin" @loginUser="loginUser"></router-view>
  <Footer v-if="navBar !== 'NavBarLoggedInAdminAndSuperUser'"></Footer>
</template>

<script>
import NavBarNotLoggedIn from '@/components/navbar/NavBarNotLoggedIn.vue'
import NavBarLoggedIn from '@/components/navbar/NavBarLoggedIn.vue'
import NavBarLoggedInAdminAndSuperUser from '@/components/navbar/NavBarLoggedInAdminAndSuperUser.vue'
import { Event } from '@/models/event.js'
import CONFIG from '../app-config.js'
import { RESTAdaptorWithFetch } from '@/services/RESTAdaptorWithFetch'
import { User } from '@/models/user'
import { SessionSbService } from '@/services/SessionSbService'
import { UserEvent } from '@/models/UserEvent'
import Footer from '@/components/Footer.vue'
import { Challenge } from '@/models/challenge'
import { Research } from '@/models/research'
import { UserEventAdapter } from '@/services/UserEventAdapter'
import { shallowReactive } from 'vue'

export default {
  name: 'App',
  emits: ['loginAdmin', 'loginUser', 'handleLogout'],
  components: {
    NavBarNotLoggedIn,
    NavBarLoggedIn,
    NavBarLoggedInAdminAndSuperUser,
    Footer
  },
  data () {
    return {
      loggedIn: false,
      admin: false,
      email: null,
      sessionSBService: shallowReactive(new SessionSbService(CONFIG.BACKEND_URL + '/authentication', CONFIG.JWT_STORAGE_ITEM))
    }
  },
  provide () {
    return {
      eventsService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/events', Event.copyConstructor),
      usersService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/users', User.copyConstructor),
      challengeService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/challenges', Challenge.copyConstructor),
      userEventsService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/userevents', UserEvent.copyConstructor),
      sessionSBService: shallowReactive(new SessionSbService(CONFIG.BACKEND_URL + '/authentication', CONFIG.JWT_STORAGE_ITEM)),
      researchService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/researches', Research.copyConstructor),
      userEventsService2: new UserEventAdapter(CONFIG.BACKEND_URL, Event.copyConstructor)
    }
  },
  methods: {
    handleLogout () {
      // Update the navBar when the user logs out
      this.loggedIn = false
      this.admin = false
    },
    loginAdmin () {
      this.loggedIn = true
      this.admin = true
    },
    loginUser () {
      this.loggedIn = true
    },
    async isAdmin () {
      const user = await this.sessionSBService.asyncFindByEmail(this.email)
      if (user != null) return user.body.userType === 'ADMIN'
      return false
    }
  },
  computed: {
    navBar () {
      if (!this.loggedIn) {
        return 'NavBarNotLoggedIn'
      } else {
        if (this.admin) {
          return 'NavBarLoggedInAdminAndSuperUser'
        } else {
          return 'NavBarLoggedIn'
        }
      }
    }
  },
  async created () {
    // Fetch the user's authentication status and role on component mount
    this.email = localStorage.getItem('email')
    this.loggedIn = !!localStorage.getItem('email')
    this.admin = await this.isAdmin()
  }
}
</script>
