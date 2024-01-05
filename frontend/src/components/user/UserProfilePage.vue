<script>
import { User } from '@/models/user'

export default {
  name: 'UserProfilePage',
  inject: ['usersService'],
  data () {
    return {
      user: null,
      edit: false,
      copyUser: null
    }
  },
  methods: {
    editProfile () {
      this.edit = true
      this.copyUser = User.copyConstructor(this.user)
    },
    cancel () {
      if (confirm('Are you sure you wan\'t to delete changes?')) {
        this.edit = false
        this.copyUser = null
      }
    },
    handleImageUpload (event) {
      const file = event.target.files[0]
      this.selectedCopy.image = URL.createObjectURL(file)
    },
    activateInput () {
      if (this.edit) {
        document.querySelector('#file').click()
      }
    },
    infoView () {
      this.$router.push({ name: 'profilePageInfo' })
    },
    eventsView () {
      this.$router.push({ name: 'profilePageEvents' })
    },
    async save () {
      if (confirm('Are you sure you wan\'t to save changes?')) {
        try {
          await this.usersService.asyncSave(this.copyUser)
          this.user = this.copyUser
          this.edit = false
          this.copyUser = null
        } catch (e) {
          console.log(e)
        }
      }
    }
  },
  async created () {
    console.log(this.$route)
    if (localStorage.getItem('email') == null) {
      this.$router.push({ route: 'PageNotFound' })
    }
    this.user = await this.usersService.asyncFindById(parseInt(localStorage.getItem('profileId')))
    this.$router.push({ name: 'profilePageInfo' })
  }
}
</script>

<template>
  <div class="breadcrum" v-if="$route.fullPath === '/profile/info' && user">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <a>News</a>
    <p>></p>
    <router-link :to="{ name: 'profilePage' }">Profile</router-link>
    <p>></p>
    <router-link :to="{ name: 'profilePageInfo', params: { id: user.id } }">Info</router-link>
  </div>
  <div class="breadcrum" v-if="$route.fullPath === '/profile/events' && user">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <a>News</a>
    <p>></p>
    <router-link :to="{ name: 'profilePage' }">Profile</router-link>
    <p>></p>
    <router-link :to="{ name: 'profilePageEvents', params: { id: user.id } }">Info</router-link>
  </div>
  <div class="user-profile-container" v-if="user">
    <div class="profile-banner">
    </div>
    <div class="profile-info">
      <div class="info-left">
        <img class="profile-pic" src="../../../src/assets/img/profilepic.png" @click="activateInput">
        <input type="file" accept="image/jpeg, image/png, image/jpg" id="file" @change="handleImageUpload">
        <div v-if="!edit" class="profile-edit-buttons">
          <button class="edit-button" @click="editProfile">Edit</button>
          <button class="delete-button">Delete</button>
        </div>
        <div v-else class="profile-edit-buttons">
          <button class="save-button" @click="save">Save</button>
          <button class="cancel" @click="cancel">Cancel</button>
        </div>
      </div>
      <div v-if="!edit" class="info-right">
        <div class="userName">
          <h1 v-if="!edit"> {{ user.firstname }} {{ user.lastname }}</h1>
        </div>
        <div v-if="!edit" class="buttons-view">
          <router-link :to="{name: 'profilePageInfo'}" class="info-view selected">Info</router-link>
          <router-link :to="{ name: 'profilePageEvents'}" class="events-view">Events</router-link>
        </div>
        <div class="info-right-bottom">
          <router-view v-if="!edit" :user="user"></router-view>
        </div>
      </div>
      <div v-else class="edit-inputs">
        <div class="profile-input">
          <label>Firstname</label>
          <input type="text" placeholder="Firstname" v-model="copyUser.firstname">
        </div>
        <div class="profile-input">
          <label>Lastname</label>
          <input type="text" placeholder="Lastname" v-model="copyUser.lastname">
        </div>
        <div class="profile-input">
          <label>Email</label>
          <input type="text" placeholder="Email" v-model="copyUser.mail">
        </div>
        <div class="profile-input">
          <label>Gender</label>
          <select v-model="copyUser.gender">
            <option value="male">Male</option>
            <option value="female">Female</option>
            <option value="N/A">N/A</option>
          </select>
        </div>
        <div class="profile-input">
          <label>Company Type</label>
          <select v-model="copyUser.companyType">
            <option value="Bar">Bar</option>
            <option value="Catering">Catering</option>
            <option value="Delivery">Delivery</option>
            <option value="Fast food">Fast food</option>
            <option value="Hotel">Hotel</option>
            <option value="Restaurant">Restaurant</option>
          </select>
        </div>
        <div class="profile-input">
          <label>Company Name</label>
          <input type="text" placeholder="1232AD" v-model="copyUser.companyName">
        </div>
        <div class="profile-input">
          <label>Postal code</label>
          <input type="text" placeholder="1232AD" v-model="copyUser.postalCode">
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
