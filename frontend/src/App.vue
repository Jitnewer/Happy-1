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
      } else if (this.loggedIn === 2 || this.loggedIn === 3) {
        return 'NavBarNotLoggedInAdminAndSuperUser'
      }
      return this.loggedIn
    }
  },
  watch: {
    navBar (newValue) {
      if (newValue === 'NavBarNotLoggedInAdminAndSuperUser') {
        document.getElementById('app').style.display = 'flex'
      } else {
        document.getElementById('app').style.display = 'block' // Or any other default value
      }
    }
  },
  mounted () {
    if (this.navBar === 'NavBarNotLoggedInAdminAndSuperUser') {
      document.getElementById('app').style.display = 'flex'
    }
  }
}

</script>

<style>
#app {
  font-family: 'Poppins', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 0;
  box-sizing: border-box;
  padding: 0;
}
</style>
