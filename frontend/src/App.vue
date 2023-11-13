<template>
  <div>
    <component :is="navBar" ></component>
    <router-view v-if="navBar !== 'NavBarLoggedInAdminAndSuperUser'"></router-view>
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
    async isAdmin () {
      const user = await this.loginAndRegisterService.asyncFindByEmail(this.email)
      return user.userType === 'ADMIN'
    }
  },
  computed: {
    navBar () {
      if (!localStorage.getItem('email')) {
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
