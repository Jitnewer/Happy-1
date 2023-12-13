<template>
    <NavBar></NavBar>
  <router-view v-if="!loggedInAsAdmin"></router-view>
  <Footer></Footer>
</template>

<script>
import { Event } from '@/models/event.js'
import CONFIG from '../app-config.js'
import { RESTAdaptorWithFetch } from '@/services/RESTAdaptorWithFetch'
import { User } from '@/models/user'
import { SessionSbService } from '@/services/SessionSbService'
import { UserEvent } from '@/models/UserEvent'
import { Challenge } from '@/models/challenge'
import { Research } from '@/models/research'
import { UserEventAdapter } from '@/services/UserEventAdapter'
import { shallowReactive } from 'vue'
import NavBar from '@/components/navbar/NavBar.vue'
import Footer from '@/components/welcomePage/Footer.vue'
import { mapState } from 'vuex'

export default {
  name: 'App',
  computed: {
    ...mapState(['loggedIn', 'loggedInAsAdmin'])
  },
  components: {
    NavBar,
    Footer
  },
  provide () {
    return {
      eventsService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/events', Event.copyConstructor),
      eventsServiceAdmin: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/events/admin', Event.copyConstructor),
      usersService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/users', User.copyConstructor),
      challengeService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/challenges', Challenge.copyConstructor),
      userEventsService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/userevents', UserEvent.copyConstructor),
      usersServiceAdmin: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/users/admin', User.copyConstructor),
      sessionSBService: shallowReactive(new SessionSbService(CONFIG.BACKEND_URL + '/authentication', CONFIG.JWT_STORAGE_ITEM)),
      researchService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/researches', Research.copyConstructor),
      userEventsService2: new UserEventAdapter(CONFIG.BACKEND_URL, Event.copyConstructor)

    }
  }
}
</script>
