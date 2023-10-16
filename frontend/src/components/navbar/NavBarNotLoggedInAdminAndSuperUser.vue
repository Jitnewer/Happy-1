<template>
  <nav>
    <div class="logo-container">
      <img id="logo" src="../../assets/images/HH_logo_portrait_20230314-removebg-preview.png" alt="logo"/>
    </div>
      <div class="nav" v-if="showNav">
          <router-link to="/admin/users">
            Users
          </router-link>
          <router-link to="/admin/stastistics">
            Statistics
          </router-link>
          <router-link to="/admin/events">
            Events
          </router-link>
          <div class="dropdown-profile-admin">
            <canvas ref="profileCanvas" class="profile-admin" width="40" height="40" @click="toggleProfile"></canvas>
            <transition name="dropdownProfileAdmin">
              <div v-show="showProfile" class="dropdown-profile-content-admin">
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
      randomColor: ''
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
          const fontSize = 20
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
      const name = 'Admin' // Replace this with your name
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
    const name = 'Admin' // Replace this with your name

    let initials = ''
    const words = name.split(' ')
    if (words.length === 1) {
      initials = name.charAt(0).toUpperCase()
    } else {
      initials = words[0].charAt(0).toUpperCase() + words[1].charAt(0).toUpperCase()
    }

    this.randomColor = this.getRandomColor()

    this.drawProfilePicture(initials, this.randomColor)
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

<style>
nav {
  background-color: #004d57 !important;
  flex-direction: column !important;
  width: 10%;
  height: 100vh;
}
.logo-container{
  width: 80% !important;
}

.dropdown-profile-admin {
  margin-top: 1rem;
  font-size: 16px;
  color: white;
  text-decoration: none;
}
.nav {
  flex-direction: column-reverse !important;}
.dropdown-profile-content-admin {
  position: relative;
}
.dropdown-profile-content-admin a {
  color: white;
  padding: 24px 0px;
  text-decoration: none;
  display: block;
  font-size: 16px;
  float: none;
}
.dropdown-profile-content-admin a:hover {
  background-color: #FECC32;
  transition: 0.2s;
  color: white;
  padding: 24px 0px;
  text-decoration: none;
  display: block;
  font-size: 16px;
  float: none;
}
.dropdownProfileAdmin-enter-active,
.dropdownProfileAdmin-leave-active {
  transition: all 0.5s;
  height: 219px
}

.dropdownProfileAdmin-enter-from,
.dropdownProfileAdmin-leave-to {
  opacity: 0;
  height: 0px;
}
.profile-admin {
  border-radius: 50%;
  border-color: #ffffff;
  border-style: solid;
  border-width: 2px;
  cursor: pointer;
}

.profile-admin:hover {
  border-color: #fcca32;
}
</style>
