<template>
  <component :is="navBar"></component>
  <router-view></router-view>
</template>

<script>
import { mapState } from 'vuex'
import NavBarNotLoggedIn from '@/components/navbar/NavBarNotLoggedIn.vue'
import NavBarLoggedIn from '@/components/navbar/NavBarLoggedIn.vue'
import NavBarNotLoggedInAdminAndSuperUser from '@/components/navbar/NavBarNotLoggedInAdminAndSuperUser.vue'

export default {
  name: 'App',
  components: {
    NavBarNotLoggedIn,
    NavBarLoggedIn,
    NavBarNotLoggedInAdminAndSuperUser
  },
  computed: {
    ...mapState(['loggedIn']), // Map the 'loggedIn' state from Vuex
    navBar () {
      if (this.loggedIn === 0) {
        return 'NavBarNotLoggedIn'
      } else if (this.loggedIn === 1) {
        return 'NavBarLoggedIn'
      }
      return this.loggedIn
    }
  }
}

</script>
