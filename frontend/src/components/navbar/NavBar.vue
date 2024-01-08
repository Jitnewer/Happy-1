<template>
  <div>
    <NavBarLoggedInAdminAndSuperUser v-if="!loggedIn && loggedInAsAdmin && !loggedInAsSuperUser || !loggedIn && !loggedInAsAdmin && loggedInAsSuperUser" @logout="handleLogout"></NavBarLoggedInAdminAndSuperUser>
    <NavBarLoggedIn v-if="loggedIn && !loggedInAsAdmin && !loggedInAsSuperUser"></NavBarLoggedIn>
    <NavBarNotLoggedIn v-if="!loggedInAsAdmin && !loggedIn && !loggedInAsSuperUser"></NavBarNotLoggedIn>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import NavBarLoggedIn from '@/components/navbar/NavBarLoggedIn.vue'
import NavBarLoggedInAdminAndSuperUser from '@/components/navbar/NavBarLoggedInAdminAndSuperUser.vue'
import NavBarNotLoggedIn from '@/components/navbar/NavBarNotLoggedIn.vue'
import { User } from '@/models/user'

export default {
  name: 'NavBar',
  inject: ['sessionSBService'],
  components: {
    NavBarLoggedIn,
    NavBarLoggedInAdminAndSuperUser,
    NavBarNotLoggedIn
  },
  computed: {
    ...mapState(['loggedIn', 'loggedInAsAdmin', 'loggedInAsSuperUser'])
  },
  methods: {
    handleLogout () {
      // Perform any necessary actions upon logout
      this.loggedInAsAdmin = false
      this.loggedIn = false
    },
    async updateNavbar () {
      if (localStorage.getItem('userDetails') === null || localStorage.getItem('userDetails') === undefined) {
        this.$store.commit('setLoggedIn', false)
        this.$store.commit('setLoggedInAsAdmin', false)
      } else {
        try {
          this.user = await this.sessionSBService.asyncFindByEmail(JSON.parse(localStorage.getItem('userDetails')).mail)
          if (this.user.body.userType === User.userTypes.Admin) {
            this.$store.commit('setLoggedIn', false)
            this.$store.commit('setLoggedInAsAdmin', true)
            this.$store.commit('setLoggedInAsSuperUser', false)
          } else if (this.user.body.userType === User.userTypes.SuperUser) {
            this.$store.commit('setLoggedInAsAdmin', false)
            this.$store.commit('setLoggedIn', false)
            this.$store.commit('setLoggedInAsSuperUser', true)
          } else {
            this.$store.commit('setLoggedInAsAdmin', false)
            this.$store.commit('setLoggedIn', true)
            this.$store.commit('setLoggedInAsSuperUser', false)
          }
        } catch (e) {
          console.error(e.toJSON())
          this.$store.commit('setError', true)
          this.$store.commit('setErrorMessage', e.toJSON().error)
          setTimeout(() => {
            this.$store.commit('setError', false)
            this.$store.commit('setErrorMessage', null)
          }, 8000)
        }
      }
    }
  },
  async created () {
    await this.updateNavbar()
  }
}
</script>
