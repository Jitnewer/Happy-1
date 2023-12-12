<template>
    <NavBar></NavBar>
    <router-view></router-view>
  <Footer></Footer>
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
import NavBar from '@/components/navbar/NavBar.vue'

export default {
  name: 'App',
  emits: ['loginAdmin', 'loginUser', 'handleLogout'],
  components: {
    NavBar,
    Footer
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
  }
}
</script>
