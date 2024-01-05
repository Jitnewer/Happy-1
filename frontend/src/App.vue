<template>
  <NavBar></NavBar>
  <router-view v-if="!loggedInAsAdmin"></router-view>
  <Footer v-if="!loggedInAsAdmin"></Footer>
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
import { reactive, shallowReactive } from 'vue'
import NavBar from '@/components/navbar/NavBar.vue'
import Footer from '@/components/welcomePage/Footer.vue'
import { mapState } from 'vuex'
import { FetchInterceptor } from '@/services/FetchInterceptor'
import { CachedRESTAdaptorWithFetch } from '@/services/CachedRESTAdaptorWithFetch'
import { FileUploadAdapter } from '@/services/FileUploadAdapter'

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
    this.theSessionSbService = shallowReactive(new SessionSbService(CONFIG.BACKEND_URL + '/authentication', CONFIG.JWT_STORAGE_ITEM))
    this.theFetchInterceptor = new FetchInterceptor(this.theSessionSbService, this.$router)
    return {
      eventsService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/events', Event.copyConstructor)),
      eventsServiceAdmin: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/events/admin', Event.copyConstructor)),
      usersService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/users', User.copyConstructor)),
      challengeService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/challenges', Challenge.copyConstructor)),
      challengeServiceAdmin: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/challenges/admin', Challenge.copyConstructor)),
      userEventsService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/userevents', UserEvent.copyConstructor)),
      usersServiceAdmin: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/users/admin', User.copyConstructor)),
      sessionSBService: shallowReactive(new SessionSbService(CONFIG.BACKEND_URL + '/authentication', CONFIG.JWT_STORAGE_ITEM)),
      researchService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/researches', Research.copyConstructor)),
      researchServiceAdmin: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/researches/admin', Research.copyConstructor)),
      fileUploadService: new FileUploadAdapter(CONFIG.BACKEND_URL + '/image')
    }
  },
  unmounted () {
    console.log('App.unmounted() has been called.')
    this.theFetchInterceptor.unregister()
  }
}
</script>
