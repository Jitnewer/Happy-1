<template>
  <div class="container-admin" v-if="user">
    <div class="breadcrum-admin breadcrum-admin-margin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
    </div>
    <div class="welcome-admin">
      <h1>Hello {{ this.user.firstname }} {{ this.user.lastname }}. Welcome to the admin page!</h1>
      <h2>More coming soon!.....</h2>
    </div>
  </div>
</template>

<script >
export default {
  name: 'welcomeAdmin.vue',
  inject: ['sessionSBService'],
  emits: ['loginAdmin', 'loginUser'],
  data () {
    return {
      user: null
    }
  },
  methods: {

  },
  async created () {
    try {
      this.user = await this.sessionSBService.asyncFindByEmail(JSON.parse(localStorage.getItem('userDetails')).mail)
    } catch (e) {
      console.log(e)
    }
  }

}
</script>
