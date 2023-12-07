<script>
export default {
  name: 'ProfilePage',
  inject: ['usersService'],
  props: ['selectedUser'],
  emits: ['back'],
  data () {
    return {
      user: null,
      path: null
    }
  },
  methods: {
    infoView () {
      this.$router.push({ name: 'adminProfileViewInfo' })
    },
    adminEventsView () {
      this.$router.push({ name: 'adminProfileViewEvents' })
    },
    back () {
      this.$emit('back')
    }
  },
  async created () {
    this.user = await this.usersService.asyncFindById(this.selectedUser.id)
    if (this.user === null) {
      this.$router.push(this.$route.mathced[0])
    }
    this.path = this.$route.path
    this.$router.push(this.$route.path + '/info')
  }
}
</script>

<template>
  <div class="user-profile-container" v-if="user">
    <div class="breadcrum-admin breadcrum-admin-margin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <p>></p>
      <router-link :to="{ name: 'users' }">User</router-link>
      <p>></p>
      <router-link :to="{ name: 'adminProfileView', params: { id: user.id } }">User / {{ user.id }}</router-link>
    </div>
    <div class="profile-banner">
    </div>
    <div class="profile-info">
      <div class="info-left">
        <img class="profile-pic" src="../../../../src/assets/img/profilepic.png">
        <div class="profile-edit-buttons">
          <button class="edit-button" @click="back">Back</button>
        </div>
      </div>
      <div class="info-right">
        <div class="userName">
          <h1> {{ user.firstname }} {{ user.lastname }}</h1>
        </div>
        <div>
          <button class="info-view selected" @click="infoView">Info</button>
          <button class="events-view" @click="adminEventsView">Events</button>
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
