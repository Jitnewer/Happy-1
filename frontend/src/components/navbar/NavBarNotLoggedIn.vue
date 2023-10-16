<template>
  <nav>
    <div class="logo-container">
      <img id="logo" src="../../assets/img/happy-hospitality-collective.png" alt="logo"/>
    </div>
      <div class="hamburger" @click="toggleNav">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
      </div>
      <transition>
      <div class="nav" v-if="showNav">
        <div  class="left-nav">
          <router-link to="/home">
          Home
          </router-link>
        <router-link to="/about-us">
          About Us
        </router-link>
        </div>
    <div class="right-nav">
      <router-link to="/sign-up" @click="loginAdmin">
        Sign Up
      </router-link>
      <router-link to="/sign-in" @click="login">
        Login
      </router-link>
    </div>
    </div>
      </transition>
  </nav>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'

export default {
  name: 'NavBar.vue',
  data () {
    return {
      showDropdown: false,
      showNav: window.innerWidth > 800
    }
  },
  computed: {
    ...mapGetters(['isLoggedIn'])
  },
  methods: {
    ...mapMutations(['setLoggedIn']),
    login () {
      this.setLoggedIn(2) // Log in as a user
    },
    loginAdmin () {
      this.setLoggedIn(2) // Log in as a admin
    },
    /**
     * Toggles the visibility of the navigation menu.
     * @param {Event} event - The click event.
     */
    toggleDropdown (event) {
      event.stopPropagation()
      this.showDropdown = !this.showDropdown
    },
    /**
     * Toggles the visibility of the navigation menu.
     * @param {Event} event - The click event.
     */
    toggleNav (event) {
      event.stopPropagation()
      this.showNav = !this.showNav // Toggle navWidth
    },
    /**
     * Handles clicks outside of dropdown and hamburger elements.
     */
    handleOutsideClick (event) {
      if (!event.target.matches('.dropdown')) {
        this.showDropdown = false
      }
    },
    updateShowNav () {
      const newShowNav = window.innerWidth >= 800
      if (newShowNav !== this.showNav) {
        this.showNav = newShowNav
      }
    }
  },
  mounted () {
    window.addEventListener('click', this.handleOutsideClick)
    window.addEventListener('resize', this.updateShowNav)
  },
  beforeUnmount () {
    window.removeEventListener('click', this.handleOutsideClick)
    window.removeEventListener('resize', this.updateShowNav)
  }
}
</script>
