// store.js
import { createStore } from 'vuex'

export default createStore({
  state: {
    loggedIn: false,
    loggedInAsAdmin: false,
    loggedInAsSuperUser: false,
    error: false,
    errorMessage: null,
    success: false,
    successMessage: null
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
    },
    setError (state, value) {
      state.error = value
    },
    setErrorMessage (state, value) {
      state.errorMessage = value
    },
    setSuccess (state, value) {
      state.success = value
    },
    setSuccessMessage (state, value) {
      state.successMessage = value
    }
  }
})
