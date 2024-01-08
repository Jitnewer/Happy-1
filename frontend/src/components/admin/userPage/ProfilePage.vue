<script>
export default {
  name: 'ProfilePage',
  inject: ['usersService'],
  props: ['selectedUser'],
  data () {
    return {
      user: null,
      previousRoute: null,
      viewRoute: 'info'
    }
  },
  methods: {
    infoView () {
      this.$router.push({ name: 'profileInfoView' })
    },
    adminEventsView () {
      this.$router.push({ name: 'profileEventsView' })
    },
    previousPage () {
      if (this.previousRoute.path === '/') {
        this.$router.push({ name: 'users' })
      } else {
        this.$router.push(this.previousRoute.path)
      }
    },
    async findUserByUrl (urlParam) {
      if (urlParam <= 0) {
        this.$router.push({ path: '/PageNotFound' })
        return
      }
      this.user = await this.usersService.asyncFindById(urlParam)

      if (this.user === null) {
        this.$router.push({ path: '/PageNotFound' })
        return
      }
      this.infoView()
    },
    setSelected (newSelectedElementClass, oldSelectedElementClass) {
      document.querySelector(oldSelectedElementClass).classList.remove('selected')
      document.querySelector(newSelectedElementClass).classList.add('selected')
    }
  },
  created () {
    this.findUserByUrl(parseInt(this.$route.params.id))
  },
  watch: {
    '$route' (to) {
      if (!to) return
      if (to.path.includes('events')) {
        this.setSelected('.events-view', '.info-view')
        this.viewRoute = 'event'
      }
      if (to.path.includes('info')) {
        this.setSelected('.info-view', '.events-view')
        this.viewRoute = 'info'
      }
    }
  },
  computed: {
    isInfoView () {
      if (this.viewRoute === 'info') return true
      return false
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.previousRoute = from
    })
  }
}
</script>

<template>
  <div class="user-profile-container" v-if="user">
    <div class="breadcrum-admin breadcrum-admin-margin">
      <router-link :to="{ name: 'users' }">User</router-link>
      <p>></p>
      <p> {{ user.id }}</p>
      <p>></p>
      <router-link v-if="isInfoView" class="infoViewRoute selectedRoute" :to="{ name: 'profileInfoView'}">info</router-link>
      <router-link v-if="!isInfoView" class="eventViewRoute" :to="{ name: 'profileEventsView'}">event</router-link>
    </div>
    <div class="profile-banner">
    </div>
    <div class="profile-info">
      <div class="info-left">
        <img class="profile-pic" :src="require(`../../../${user.profilePic}`)">
        <div class="profile-edit-buttons">
          <button class="back-button" @click="previousPage">Back</button>
        </div>
      </div>
      <div class="info-right">
        <div class="userName">
          <h1> {{ user.firstname }} {{ user.lastname }}</h1>
        </div>
        <div class="buttons-view">
          <router-link class="info-view selected" @click="infoView" to="profileInfoView">Info</router-link>
          <router-link class="events-view" @click="adminEventsView" to="profileEventsView">Events</router-link>
        </div>
        <div class="info-right-bottom">
          <router-view :user="user"></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.breadcrum-admin {
  position: absolute;
  color: white;
}

.breadcrum-admin a, .breadcrum-admin p {
  color: white;
}
</style>
