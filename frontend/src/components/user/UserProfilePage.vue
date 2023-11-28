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
      }
    }
  },
  async created () {
    this.user = await this.usersService.asyncFindById(this.$route.params.id)
    console.log(this.user)
  }
}
</script>

<template>
  <div class="user-profile-container" v-if="user">
    <div class="profile-banner">
    </div>
    <div class="profile-info">
      <div class="info-left">
        <img class="profile-pic" src="../../../src/assets/img/profilepic.png">
        <div v-if="!edit" class="profile-edit-buttons">
          <button class="edit-button" @click="editProfile">Edit</button>
          <button class="delete-button">Delete</button>
        </div>
        <div v-else class="profile-edit-buttons">
          <button class="save-button">Save</button>
          <button class="clear">Clear</button>
          <button class="cancel" @click="cancel">Cancel</button>
        </div>
      </div>
      <div v-if="!edit" class="info-right">
        <div class="userName">
          <h1 v-if="!edit"> {{ user.firstname }} {{ user.lastname }}</h1>
        </div>
        <div v-if="!edit">
          <button class="info-view selected">Info</button>
          <button class="uploads-view unselected">Uploads</button>
        </div>
        <div class="info-right-bottom">
          <div>
            <h3>Id</h3>
            <p> {{ user.id }} </p>
          </div>
          <div>
            <h3>Email</h3>
            <p> {{ user.mail }} </p>
          </div>
          <div>
            <h3>Age</h3>
            <p> {{ user.age }} </p>
          </div>
          <div>
            <h3>Gender</h3>
            <p> {{ user.gender }} </p>
          </div>
          <div>
            <h3>Company Type</h3>
            <p> {{ user.companyType }} </p>
          </div>
          <div>
            <h3>Company Name</h3>
            <p> {{ user.companyName }} </p>
          </div>
          <div>
            <h3>User type</h3>
            <div class="usertype-tag"> {{ user.userType }} </div>
          </div>
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
          <label>Postal code</label>
          <input type="text" placeholder="1232AD" v-model="copyUser.postalCode">
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
p, h1 {
  color: black;
  margin: 0;
}
</style>
