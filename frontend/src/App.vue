<template>
  <component :is="navBar"></component>
  <router-view v-if="navBar !== 'NavBarLoggedInAdminAndSuperUser'"></router-view>
</template>

<script>
import NavBarNotLoggedIn from '@/components/navbar/NavBarNotLoggedIn.vue'
import NavBarLoggedIn from '@/components/navbar/NavBarLoggedIn.vue'
import NavBarLoggedInAdminAndSuperUser from '@/components/navbar/NavBarLoggedInAdminAndSuperUser.vue'
import { Event } from '@/models/event.js'
import CONFIG from '../app-config.js'
import { RESTAdaptorWithFetch } from '@/services/RESTAdaptorWithFetch'
import { User } from '@/models/user'
import { LoginAndRegisterAdapter } from '@/services/LoginAndRegisterAdapter'

export default {
  name: 'App',
  components: {
    NavBarNotLoggedIn,
    NavBarLoggedIn,
    NavBarLoggedInAdminAndSuperUser
  },
  data () {
    return {
      email: localStorage.getItem('email'), // Initialize email with the value from localStorage
      isAdmin: false, // New data property to store admin status
      loginAndRegisterService: new LoginAndRegisterAdapter(CONFIG.BACKEND_URL, User.copyConstructor)
    }
  },
  provide () {
    return {
      eventsService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/events', Event.copyConstructor),
      usersService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/events', User.copyConstructor),
      loginAndRegisterService: new LoginAndRegisterAdapter(CONFIG.BACKEND_URL, User.copyConstructor)
    }
  },
  watch: {
    $route: {
      handler () {
        // Check if email has changed in localStorage
        if (localStorage.getItem('email') !== this.email) {
          // Update the email property and recompute the navBar
          this.email = localStorage.getItem('email')
        }
      },
      deep: true
    }
  },
  methods: {
    async checkAdminStatus () {
      try {
        this.isAdmin = await this.loginAndRegisterService.asyncIsAdmin(this.email)
      } catch (error) {
        console.error(error)
        // Handle error if asyncIsAdmin fails
      }
    }
  },
  created () {
    this.checkAdminStatus()
  },
  computed: {
    navBar () {
      if (this.email === null) {
        return 'NavBarNotLoggedIn'
      } else {
        if (this.isAdmin) {
          return 'NavBarLoggedInAdminAndSuperUser'
        } else {
          return 'NavBarLoggedIn'
        }
      }
    }
  }
}
</script>
