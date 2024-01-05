<template>
  <nav>
    <img id="logo" src="../../assets/img/happy-hospitality-collective.png" height="119" width="310" alt=""/>
    <svg id="hamburger" @click="toggleNav" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--! Font Awesome Pro 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M0 96C0 78.3 14.3 64 32 64H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32C14.3 128 0 113.7 0 96zM0 256c0-17.7 14.3-32 32-32H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32c-17.7 0-32-14.3-32-32zM448 416c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32s14.3-32 32-32H416c17.7 0 32 14.3 32 32z"/></svg>
    <transition name="nav">
      <div class="nav-links" v-show="showNav">
        <div class="nav-links-left">
          <router-link to="/home">Home</router-link>
          <router-link to="/about">About Us</router-link>
          <router-link to="/pillars">Pillars</router-link>
        </div>
        <div class="nav-links-right">
          <router-link to="/register">Register</router-link>
          <router-link to="/login">Login</router-link>
        </div>
      </div>
    </transition>
  </nav>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'

export default {
  name: 'NavBarNotLoggedIn.vue',
  inject: ['sessionSBService'],
  data () {
    return {
      showNav: false
    }
  },
  methods: {
    toggleNav () {
      this.showNav = !this.showNav
    },
    updateShowNav () {
      this.showNav = window.innerWidth >= 860
    }
  },
  created () {
    this.updateShowNav()
  },
  mounted () {
    window.addEventListener('resize', this.updateShowNav)
    window.addEventListener('click', this.handleOutsideClick)
  },
  beforeUnmount () {
    window.removeEventListener('resize', this.updateShowNav)
  }
}
</script>
