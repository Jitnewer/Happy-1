<script>
export default {
  name: 'ProfilePage',
  inject: ['usersService'],
  props: ['selectedUser'],
  emits: ['back'],
  data () {
    return {
      user: null,
      test: -1
    }
  },
  methods: {
    infoView () {
      this.test--
      this.$router.push({ name: 'profileInfoView' })
    },
    adminEventsView () {
      this.test--
      this.$router.push({ name: 'profileEventsView' })
    },
    previousPage () {
      this.$router.go(this.test)
    },
    async findUserByUrl (urlParam) {
      if (urlParam < 0) {
        this.$router.push({ path: '/PageNotFound' })
        return
      }
      this.user = await this.usersService.asyncFindById(urlParam)

      if (this.user === null) {
        this.$router.push({ path: '/PageNotFound' })
        return
      }

      this.infoView()
    }
  },
  created () {
    this.findUserByUrl(parseInt(this.$route.params.id))
  }
}
</script>

<template>
  <div class="user-profile-container" v-if="user">
    <div class="profile-banner">
    </div>
    <div class="profile-info">
      <div class="info-left">
        <img class="profile-pic" src="../../../assets/profilePic/profilepic.png">
        <div class="profile-edit-buttons">
          <button class="edit-button" @click="previousPage">Back</button>
        </div>
      </div>
      <div class="info-right">
        <div class="userName">
          <h1> {{ user.firstname }} {{ user.lastname }}</h1>
        </div>
        <div class="buttons-view">
          <a class="router-link-active router-link-exact-active info-view selected" @click="infoView">Info</a>
          <a class="events-view" @click="adminEventsView">Events</a>
        </div>
        <div class="info-right-bottom">
          <router-view :user="user"></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
