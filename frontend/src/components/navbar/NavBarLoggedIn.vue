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
      <transition name="loggedIn">
      <div class="nav" v-if="showNav">
        <div  class="left-nav">
          <router-link to="/home">
          Home
          </router-link>
          <div class="dropdown">
          <a class="dropBtn" @click="toggleDropdown">
            News
          </a>
            <transition name="dropdown">
              <div v-if="showDropdown" class="dropdown-content">
                <router-link class="router-link" to="/news/challenges">
                  Challenges
                </router-link>
                <router-link to="/news/research">
                  Research
                </router-link>
                <router-link to="/news/network">
                  Network
                </router-link>
              </div>
          </transition>
          </div>
        <router-link to="/events">
          Events
        </router-link>
        <router-link to="/about-us">
          About Us
        </router-link>
        </div>
    <div class="right-nav">
      <div class="dropdown-profile" @click="toggleProfile">
        <canvas ref="profileCanvas" class="profile" width="33" height="33"></canvas>
        <div>
          <div class="profile-name">
            <p>Rick Veerman</p>
          </div>
          <div class="caret">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 320 512"><!--! Font Awesome Pro 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M182.6 137.4c-12.5-12.5-32.8-12.5-45.3 0l-128 128c-9.2 9.2-11.9 22.9-6.9 34.9s16.6 19.8 29.6 19.8H288c12.9 0 24.6-7.8 29.6-19.8s2.2-25.7-6.9-34.9l-128-128z"/></svg>
          </div>
        </div>
        <transition name="dropdownProfile">
          <div v-show="showProfile" class="dropdown-profile-content">
            <router-link to="/profile">
              Your Profile
            </router-link>
            <router-link to="/profile/settings">
              Settings
            </router-link>
            <a class="clickable" @click="logout">
              Logout
            </a>
          </div>
        </transition>
      </div>
    </div>
    </div>
      </transition>
  </nav>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  name: 'NavBar.vue',
  data () {
    return {
      showDropdown: false,
      showProfile: false,
      showNav: window.innerWidth > 800,
      randomColor: '',
      showName: true
    }
  },
  methods: {
    ...mapMutations(['setLoggedIn']),

    drawProfilePicture (initials, bgColor) {
      this.$nextTick(() => {
        const canvas = this.$refs.profileCanvas
        if (canvas != null) {
          const ctx = canvas.getContext('2d')
          const centerX = canvas.width / 2
          const centerY = canvas.height / 2
          const fontSize = 15
          const fontFamily = 'Poppins, sans-serif'

          ctx.fillStyle = bgColor // Random background color
          ctx.fillRect(0, 0, canvas.width, canvas.height)

          ctx.fillStyle = '#ffffff' // Text color
          ctx.font = `${fontSize}px ${fontFamily}`
          ctx.textAlign = 'center'
          ctx.textBaseline = 'middle'

          ctx.fillText(initials, centerX, centerY)
        }
      })
    },

    getRandomColor () {
      const letters = '0123456789ABCDEF'
      let color = '#'
      for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)]
      }
      return color
    },

    /**
     * Logs out of the application
     */
    logout () {
      this.setLoggedIn(0)
    },
    /**
     * Toggles the visibility of the navigation menu.
     * @param {Event} event - The click event.
     */
    toggleDropdown (event) {
      event.stopPropagation()
      this.showDropdown = !this.showDropdown
    },
    toggleProfile (event) {
      event.stopPropagation()
      this.showProfile = !this.showProfile
    },
    /**
     * Toggles the visibility of the navigation menu.
     * @param {Event} event - The click event.
     */
    toggleNav (event) {
      event.stopPropagation()
      this.showNav = !this.showNav // Toggle navWidth
      if (this.showNav) {
        this.$nextTick(() => {
          this.updateProfilePicture()
        })
      }
    },
    /**
     * Handles clicks outside of dropdown and hamburger elements.
     */
    handleOutsideClick (event) {
      if (!event.target.matches('.dropdown')) {
        this.showDropdown = false
      }
    },

    updateProfilePicture () {
      const name = 'Rick Veerman' // Replace this with your name
      let initials = ''
      const words = name.split(' ')
      if (words.length === 1) {
        initials = name.charAt(0).toUpperCase()
      } else {
        initials = words[0].charAt(0).toUpperCase() + words[1].charAt(0).toUpperCase()
      }
      this.drawProfilePicture(initials, this.randomColor)
    },
    updateShowNav () {
      const newShowNav = window.innerWidth >= 800
      if (newShowNav !== this.showNav) {
        this.showNav = newShowNav
        if (this.showNav) {
          this.$nextTick(() => {
            this.updateProfilePicture()
          })
        }
      }
    }
  },
  created () {
    const name = 'Rick Veerman' // Replace this with your name

    let initials = ''
    const words = name.split(' ')
    if (words.length === 1) {
      initials = name.charAt(0).toUpperCase()
    } else {
      initials = words[0].charAt(0).toUpperCase() + words[1].charAt(0).toUpperCase()
    }

    this.randomColor = this.getRandomColor()

    this.drawProfilePicture(initials, this.randomColor)
    this.showName = window.innerWidth < 800
  },
  mounted () {
    window.addEventListener('click', this.handleOutsideClick)
    window.addEventListener('resize', this.updateShowNav)
  },
  beforeUnmount () {
    window.removeEventListener('click', this.handleOutsideClick)
    window.removeEventListener('resize', this.updateShowNav)
  },
  watch () {
    this.showName = window.innerWidth < 800
  }
}
</script>
