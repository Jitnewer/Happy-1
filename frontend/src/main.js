import { createApp } from 'vue'
import { createStore } from 'vuex'
import App from './App.vue'
import router from './router'
import createPersistedState from 'vuex-persistedstate'
import Cookies from 'js-cookie'
import '../src/assets/css/main.css'
/* import the fontawesome core */
import { library, config } from '@fortawesome/fontawesome-svg-core'

/* import font awesome icon component */
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

/* import specific icons */
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'

/* add icons to the library */
library.add(faUserSecret)

const store = createStore({
  state () {
    return {
      loggedIn: 0 // Default value
    }
  },
  mutations: {
    setLoggedIn (state, value) {
      state.loggedIn = value
    },
    setFullName (state, name) {
      state.fullName = name
    }
  },
  getters: {
    isLoggedIn: state => state.loggedIn > 1,
    isAdmin: state => state.loggedIn > 2,
    isSuperUser: state => state.loggedIn > 3,
    getFullName: state => state.fullName
  },
  plugins: [createPersistedState({
    storage: {
      getItem: (key) => Cookies.get(key),
      setItem: (key, value) => Cookies.set(key, value, { expires: 365 }),
      removeItem: (key) => Cookies.remove(key)
    }

  })]
})

const app = createApp(App)
app.component('font-awesome-icon', FontAwesomeIcon)

config.styleDefault = 'duotone'

app.config.productionTip = false

app.use(store)
app.use(router)
app.mount('#app')
