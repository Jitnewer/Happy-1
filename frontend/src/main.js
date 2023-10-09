import { createApp } from 'vue'
import { createStore } from 'vuex'
import App from './App.vue'
import router from './router'
import createPersistedState from 'vuex-persistedstate'
import Cookies from 'js-cookie'
import '../src/assets/css/main.css'

const store = createStore({
  state () {
    return {
      loggedIn: 0 // Default value
    }
  },
  mutations: {
    setLoggedIn (state, value) {
      state.loggedIn = value
    }
  },
  getters: {
    isLoggedIn: state => state.loggedIn > 1,
    isAdmin: state => state.loggedIn > 2,
    isSuperUser: state => state.loggedIn > 3
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
app.use(store)
app.use(router)
app.mount('#app')
