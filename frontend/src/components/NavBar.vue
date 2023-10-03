<template>
  <nav>
    <div class="logo-container">
      <img id="logo" src="../assets/img/happy-hospitality-collective.png"  alt="logo"/>
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
          <div class="dropdown">
            <a class="dropBtn" @click="toggleDropdown">
              News {{ this.newsArrow }}
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
          <router-link :to="{ name: 'register' }">
            Sign Up
          </router-link>
          <router-link to="/sign-in">
            Login
          </router-link>
        </div>
      </div>
    </transition>
  </nav>
</template>

<script>
export default {
  name: 'NavBar.vue',
  data () {
    return {
      showDropdown: false,
      showNav: window.innerWidth > 800,
      newsArrow: '˅'
    }
  },
  methods: {
    /**
     * Toggles the visibility of the navigation menu.
     * @param {Event} event - The click event.
     */
    toggleDropdown (event) {
      event.stopPropagation()
      this.showDropdown = !this.showDropdown
      if (this.showDropdown === true) {
        this.newsArrow = '^'
      } else {
        this.newsArrow = '˅'
      }
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
      if (this.showDropdown === true) {
        this.newsArrow = '^'
      } else {
        this.newsArrow = '˅'
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

<style scoped>

.v-enter-active,
.v-leave-active {
  transition: all 0.5s;
  height: 240px
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
  height: 0px;
}

.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.5s;
  height: 120px
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  height: 0px;
}

#logo {
  width: 100%
}

.logo-container {
  display: flex;
  align-items: center;
  width: 12%;
}
.dropdown-content {
  position: absolute;
  background-color: #333;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  margin-top: 73px;
}

.hamburger {
  display: none;
  cursor: pointer;
}

.bar {
  width: 25px;
  height: 3px;
  background-color: white;
  margin: 4px auto;
  transition: 0.4s;
}

.nav {
  display: flex;
  width: 100%;
  justify-content: space-between;
}

.dropdown-content a {
  float: none;
  color: white;
  background-color:#0992a4;
  padding: 14px 16px;
  text-decoration: none;
  display: block;
  text-align: left;
  font-size: 16px;
}

.dropdown-content a:hover {
  background-color: #FECC32;
  transition: 0.2s;
}
.dropdown {
  cursor: pointer;
  overflow: hidden;
  float: left;
}
.dropBtn {
  user-select: none;
}

@media only screen and (max-width: 800px) {
  nav a {
    font-size: 13px;
    padding: 10px 12px;
  }
  .nav {
    display: flex;
    flex-direction: column;
  }
  .dropdown {
    display: flex;
    justify-content: center;
    flex-direction: column;
    width: 100%
  }
  .dropdown-content {
    position: revert;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 0px;
    box-shadow: none;
    background-color: #0992A4;
  }
  .dropBtn {
    padding: 10px 13px;
    font-size: 13px;
    width: 25%;
    margin: auto;
  }
  .dropdown-content a {
    float: none;
    color: white;
    padding: 10px 13px;
    text-decoration: none;
    display: block;
    text-align: center;
    font-size: 13px;
    width: 25%;
  }
  .hamburger {
    display: block;
    padding: 10px 12px;
  }
  nav a {
    width: 25%;
  }
  .logo-container {
    width: 33%;
    margin: auto;
  }
}
</style>
