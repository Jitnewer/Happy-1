<template>
  <div>
    <component :is="navBar" @handleLogout="handleLogout"></component>
    <router-view v-if="navBar !== 'NavBarLoggedInAdminAndSuperUser'"  @loginAdmin="loginAdmin" @loginUser="loginUser"></router-view>
  </div>
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
      loggedIn: false,
      admin: false,
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
  methods: {
    handleLogout () {
      // Update the navBar when the user logs out
      this.loggedIn = false
      this.isAdmin = false
    },
    loginAdmin () {
      this.loggedIn = true
      this.isAdmin = true
    },
    loginUser () {
      this.loggedIn = true
    }
  },
  computed: {
    navBar () {
      console.log(this.loggedIn)
      console.log(localStorage.getItem('email'))
      console.log(localStorage.getItem('admin'))
      if (!this.loggedIn && !localStorage.getItem('email')) {
        return 'NavBarNotLoggedIn'
      } else {
        if (this.isAdmin || (localStorage.getItem('admin') === 'true')) {
          return 'NavBarLoggedInAdminAndSuperUser'
        } else {
          return 'NavBarLoggedIn'
        }
      }
    }
  }
}
</script>
