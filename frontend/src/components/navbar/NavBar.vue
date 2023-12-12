<template>
  <div>
    <NavBarLoggedInAdminAndSuperUser v-if="!loggedIn && loggedInAsAdmin" @logout="handleLogout"></NavBarLoggedInAdminAndSuperUser>
    <NavBarLoggedIn v-if="loggedIn && !loggedInAsAdmin"></NavBarLoggedIn>
    <NavBarNotLoggedIn v-if="!loggedInAsAdmin && !loggedIn"></NavBarNotLoggedIn>
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
    ...mapState(['loggedIn', 'loggedInAsAdmin'])
  },
  methods: {
    handleLogout () {
      // Perform any necessary actions upon logout
      this.loggedInAsAdmin = false
      this.loggedIn = false
    },
    async updateNavbar () {
      console.log('Updating navbar...')

      if (localStorage.getItem('userDetails') === null || localStorage.getItem('userDetails') === undefined) {
        this.$store.commit('setLoggedIn', false)
        this.$store.commit('setLoggedInAsAdmin', false)
      } else {
        try {
          this.user = await this.sessionSBService.asyncFindByEmail(JSON.parse(localStorage.getItem('userDetails')).mail)
          console.log(this.user.body)
          if (this.user.body.userType === User.userTypes.Admin) {
            this.$store.commit('setLoggedIn', false)
            this.$store.commit('setLoggedInAsAdmin', true)
          } else {
            this.$store.commit('setLoggedInAsAdmin', false)
            this.$store.commit('setLoggedIn', true)
          }
        } catch (e) {
          console.error(e)
        }
      }
      console.log(this.loggedIn)
      console.log(this.loggedInAsAdmin)
      console.log('Updated navbar:', this.loggedIn, this.loggedInAsAdmin)
    }
  },
  async created () {
    await this.updateNavbar()
  }
}
</script>
