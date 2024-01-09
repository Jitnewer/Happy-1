<template>
  <nav v-if="user">
    <img id="logo" src="../../assets/img/happy-hospitality-collective.png" height="119" width="310" alt=""/>
    <svg id="hamburger" @click="toggleNav" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--! Font Awesome Pro 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M0 96C0 78.3 14.3 64 32 64H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32C14.3 128 0 113.7 0 96zM0 256c0-17.7 14.3-32 32-32H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32c-17.7 0-32-14.3-32-32zM448 416c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32s14.3-32 32-32H416c17.7 0 32 14.3 32 32z"/></svg>
    <transition name="nav">
      <div class="nav-links" v-show="showNav">
        <div class="nav-links-left">
          <router-link to="/home">Home</router-link>
          <div class="dropdown">
            <a @click="toggleDropdown">News</a>
            <transition name="dropdown">
              <div class="dropdown-content" v-show="showDropdown">
                <router-link class="dropdown-content-link" to="/news/challenges">
                  Challenges
                </router-link>
                <router-link class="dropdown-content-link" to="/news/researches">
                  Research
                </router-link>
                <router-link class="dropdown-content-link" to="/news/networks">
                  Network
                </router-link>
              </div>
            </transition>
          </div>
          <router-link :to="{ name: 'events', query: { sort: 'asc' } }">
            Events
          </router-link>
          <router-link to="/about">
            About Us
          </router-link>
        </div>
        <div class="nav-links-right">
          <div class="dropdown-profile" @click="toggleProfile">
            <div class="profile-d">
              <img v-if="user.profilePic != null" :src='`https://ik.imagekit.io/happy1hva${user.profilePic}`' class="profile" width="45" height="45" alt="Event Image">
              <canvas v-if="user.profilePic == null" ref="profileCanvas" class="profile" width="45" height="45"></canvas>
              <p id="profile-name">{{ user.firstname }} {{ user.lastname }}</p>
              <div class="caret">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 320 512"><!--! Font Awesome Pro 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M182.6 137.4c-12.5-12.5-32.8-12.5-45.3 0l-128 128c-9.2 9.2-11.9 22.9-6.9 34.9s16.6 19.8 29.6 19.8H288c12.9 0 24.6-7.8 29.6-19.8s2.2-25.7-6.9-34.9l-128-128z"/></svg>
              </div>
            </div>
            <transition name="dropdownProfile">
              <div class="dropdown-profile-content" v-show="showProfile">
                <router-link class="dropdown-profile-content-link"  to="/myProfile">
                  Profile
                </router-link>
                <router-link class="dropdown-profile-content-link" to="/profile/settings">
                  Settings
                </router-link >
                <a class="dropdown-profile-content-link" @click="handleLogout">
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
import { mapGetters, mapMutations } from 'vuex'

export default {
  name: 'NavBarLoggedIn.vue',
  emits: ['logout'],
  inject: ['sessionSBService'],
  data () {
    return {
      user: null,
      showNav: false,
      showDropdown: false,
      showProfile: false,
      randomColor: '',
      fullName: null,
      picture: null,
      mail: null
    }
  },
  methods: {
    handleLogout () {
      this.sessionSBService.signOut()
      this.$store.commit('setLoggedIn', false)
      this.$store.commit('setLoggedInAsSuperUser', false)
      this.$store.commit('setLoggedInAsAdmin', false)
      this.$store.commit('setSuccess', true)
      this.$store.commit('setSuccessMessage', 'Logged out!')
      setTimeout(() => {
        this.$store.commit('setSuccess', false)
        this.$store.commit('setSuccessMessage', null)
      }, 8000)
      this.$router.push({ path: '/login' })
    },
    toggleNav () {
      this.showNav = !this.showNav
    },
    toggleDropdown (event) {
      event.stopPropagation()
      this.showDropdown = !this.showDropdown
    },
    toggleProfile (event) {
      event.stopPropagation()
      this.showProfile = !this.showProfile
    },
    updateShowNav () {
      this.showNav = window.innerWidth >= 1050
    },
    showDropdownContent () {
      this.$refs.dropdownContent.style.visibility = 'visible'
    },
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
    handleOutsideClick (event) {
      if (!event.target.matches('.dropdown')) {
        this.showDropdown = false
      }
      if (!event.target.matches('.dropdown-profile')) {
        this.showDropdown = false
      }
    },
    getRandomColor () {
      const letters = '0123456789ABCDEF'
      let color = '#'
      for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)]
      }
      return color
    }
  },
  async created () {
    try {
      // Initiate the asynchronous operation
      const userAndToken = await this.sessionSBService.asyncFindByEmail(JSON.parse(localStorage.getItem('userDetails')).mail)
      this.user = userAndToken.body
      if (this.user.profilePic === '') {
        this.user.profilePic = null
      }
    } catch (e) {
      console.error(e.toJSON())
    }

    const fullname = `${this.user.firstname} ${this.user.lastname}`

    let initials = ''
    const words = fullname.split(' ')
    if (words.length === 1) {
      initials = fullname.charAt(0).toUpperCase()
    } else {
      initials = words[0].charAt(0).toUpperCase() + words[1].charAt(0).toUpperCase()
    }
    this.randomColor = this.getRandomColor()

    this.drawProfilePicture(initials, this.randomColor)
    this.updateShowNav()
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
