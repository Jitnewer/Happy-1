<template>
  <div class="container-admin" v-if="user">
    <div class="breadcrum-admin breadcrum-admin-margin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
    </div>
    <div class="welcome-admin">
      <h1>Hello {{ this.user.firstname }} {{ this.user.lastname }}. Welcome to the admin page!</h1>
      <div class="admin-homepage">
        <img src="../../assets/img/dutch_restaurant_pic.png" alt="Dutch Restaurant Pic"/>
      </div>
    </div>
  </div>
</template>

<script >
import ErrorPopUp from '@/components/errorPopUp.vue'

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
      const response = await this.sessionSBService.asyncFindByEmail(JSON.parse(localStorage.getItem('userDetails')).mail)
      this.user = response.body
    } catch (e) {
      console.log(e.toJSON())
      this.$store.commit('setError', true)
      this.$store.commit('setErrorMessage', e.toJSON().error)
      setTimeout(() => {
        this.$store.commit('setError', false)
        this.$store.commit('setErrorMessage', null)
      }, 8000)
    }
  }

}
</script>
