// store.js
import { createStore } from 'vuex'

export default createStore({
  state: {
    loggedIn: false,
    loggedInAsAdmin: false
  },
  mutations: {
    setLoggedIn (state, value) {
      state.loggedIn = value
    },
    setLoggedInAsAdmin (state, value) {
      state.loggedInAsAdmin = value
    }
  }
})
