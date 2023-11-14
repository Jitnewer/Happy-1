<template>
  <nav>
    <img id="logo" src="../../assets/images/happy-hospitality-collective.png" height="119" width="310" alt=""/>
    <svg id="hamburger" @click="toggleNav" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--! Font Awesome Pro 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M0 96C0 78.3 14.3 64 32 64H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32C14.3 128 0 113.7 0 96zM0 256c0-17.7 14.3-32 32-32H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32c-17.7 0-32-14.3-32-32zM448 416c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32s14.3-32 32-32H416c17.7 0 32 14.3 32 32z"/></svg>
    <transition name="nav">
    <div class="nav-links" v-show="showNav">
      <div class="nav-links-left">
        <router-link to="/home">Home</router-link>
        <router-link to="/about">About Us</router-link>
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
  name: 'NavBar.vue',
  inject: ['loginAndRegisterService'],
  data () {
    return {
      showNav: false
    }
  },
  methods: {
    async login () {
      try {
        // const user = await this.loginAndRegisterService.asyncLogin('mylovandijk@gmail.com', 'test')
        const user = await this.loginAndRegisterService.asyncLogin('evadegraaf@gmail.com', 'test')

        // const user = await this.loginAndRegisterService.asyncLogin('timgroot@gmail.com', 'test')
        if (user !== null) {
          if (user.userType === 'ADMIN') {
            localStorage.setItem('email', 'evadegraaf@gmail.com')
            this.$emit('loginAdmin')
            this.$router.push({ path: '/admin/events' })
          } else {
            localStorage.setItem('email', 'mylovandijk@gmail.com')
            this.$router.push({ path: '/home' })
            this.$emit('loginUser')
          }
        }
      } catch (e) {
        console.error(e)
      }
    },
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
