<template>
  <component :is="navBar"></component>
  <router-view v-if="navBar !== 'NavBarLoggedInAdminAndSuperUser'"></router-view>
</template>

<script>
import { mapState } from 'vuex'
import NavBarNotLoggedIn from '@/components/navbar/NavBarNotLoggedIn.vue'
import NavBarLoggedIn from '@/components/navbar/NavBarLoggedIn.vue'
import NavBarLoggedInAdminAndSuperUser from '@/components/navbar/NavBarLoggedInAdminAndSuperUser.vue'
import { Event } from '@/models/event.js'
import CONFIG from '../app-config.js'
import { RESTAdaptorWithFetch } from '@/services/RESTAdaptorWithFetch'

export default {
  name: 'App',
  components: {
    NavBarNotLoggedIn,
    NavBarLoggedIn,
    NavBarLoggedInAdminAndSuperUser
  },
  provide () {
    return {
      eventsService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/events', Event.copyConstructor)
    }
  },
  computed: {
    ...mapState(['loggedIn']), // Map the 'loggedIn' state from Vuex
    navBar () {
      if (this.loggedIn === 0) {
        return 'NavBarNotLoggedIn'
      } else if (this.loggedIn === 1) {
        return 'NavBarLoggedIn'
      } else if (this.loggedIn === 2 || this.loggedIn === 3) {
        return 'NavBarLoggedInAdminAndSuperUser'
      }
      return this.loggedIn
    }
  }
}
</script>
