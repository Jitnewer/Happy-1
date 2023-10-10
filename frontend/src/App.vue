<template>
  <component :is="navBar"></component>
  <router-view></router-view>
  <Footer></Footer>
</template>

<script>
import { mapState } from 'vuex'
import NavBarNotLoggedIn from '@/components/navbar/NavBarNotLoggedIn.vue'
import NavBarLoggedIn from '@/components/navbar/NavBarLoggedIn.vue'
import NavBarLoggedInAdminAndSuperUser from '@/components/navbar/NavBarLoggedInAdminAndSuperUser.vue'
import Footer from '@/components/Footer.vue'
export default {
  name: 'App',
  components: {
    NavBarNotLoggedIn,
    NavBarLoggedIn,
    NavBarLoggedInAdminAndSuperUser,
    Footer
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
