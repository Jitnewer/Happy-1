<template>
  <NavBar></NavBar>
  <router-view v-if="!loggedInAsAdmin && !loggedInAsSuperUser"></router-view>
  <Footer v-if="!loggedInAsAdmin && !loggedInAsSuperUser"></Footer>
</template>

<script>
import { Event } from '@/models/event.js'
import CONFIG from '../app-config.js'
import { User } from '@/models/user'
import { SessionSbService } from '@/services/SessionSbService'
import { UserEvent } from '@/models/UserEvent'
import { Challenge } from '@/models/challenge'
import { Research } from '@/models/research'
import { Carousel } from '@/models/carousel'
import { reactive, shallowReactive } from 'vue'
import NavBar from '@/components/navbar/NavBar.vue'
import Footer from '@/components/welcomePage/Footer.vue'
import { mapState } from 'vuex'
import { FetchInterceptor } from '@/services/FetchInterceptor'
import { CachedRESTAdaptorWithFetch } from '@/services/CachedRESTAdaptorWithFetch'
import { Network } from '@/models/network.js'
import { FileUploadAdapter } from '@/services/FileUploadAdapter'
import { Newsletter } from '@/models/newsletter'

export default {
  name: 'App',
  computed: {
    ...mapState(['loggedIn', 'loggedInAsAdmin', 'loggedInAsSuperUser'])
  },
  components: {
    NavBar,
    Footer
  },
  provide () {
    this.theSessionSbService = shallowReactive(new SessionSbService(CONFIG.BACKEND_URL + '/authentication', CONFIG.JWT_STORAGE_ITEM))
    this.theFetchInterceptor = new FetchInterceptor(this.theSessionSbService, this.$router)
    return {
      carouselService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/carousels', Carousel.copyConstructor)),
      carouselServiceSuperUser: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/carousels/superuser', Carousel.copyConstructor)),
      eventsService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/events', Event.copyConstructor)),
      eventsServiceSuperUser: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/events/superuser', Event.copyConstructor)),
      usersService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/users', User.copyConstructor)),
      challengeService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/challenges', Challenge.copyConstructor)),
      challengeServiceSuperUser: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/challenges/superuser', Challenge.copyConstructor)),
      userEventsService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/userevents', UserEvent.copyConstructor)),
      usersServiceAdmin: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/users/admin', User.copyConstructor)),
      sessionSBService: shallowReactive(new SessionSbService(CONFIG.BACKEND_URL + '/authentication', CONFIG.JWT_STORAGE_ITEM)),
      researchService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/researches', Research.copyConstructor)),
      researchServiceSuperUser: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/researches/superuser', Research.copyConstructor)),
      fileUploadService: new FileUploadAdapter(CONFIG.BACKEND_URL + '/image'),
      networkService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/networks', Network.copyConstructor)),
      networkServiceSuperUser: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/networks/superuser', Network.copyConstructor)),
      newsletterService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/newsletters/authentication', Newsletter.copyConstructor)),
      newsletterServiceSuperuser: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/newsletters/superuser', Newsletter.copyConstructor))

    }
  },
  unmounted () {
    console.log('App.unmounted() has been called.')
    this.theFetchInterceptor.unregister()
  }
}
</script>
