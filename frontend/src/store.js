// store.js
import { createStore } from 'vuex'

export default createStore({
  state: {
    loggedIn: false,
    loggedInAsAdmin: false,
    loggedInAsSuperUser: false
  },
  mutations: {
    setLoggedIn (state, value) {
      state.loggedIn = value
    },
    setLoggedInAsAdmin (state, value) {
      state.loggedInAsAdmin = value
    },
    setLoggedInAsSuperUser (state, value) {
      state.loggedInAsSuperUser = value
    }
  }
})
