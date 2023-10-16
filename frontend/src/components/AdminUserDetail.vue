<script>
import { User } from '@/models/user'

export default {
  name: 'AdminUserDetail',
  props: ['selectedUser', 'create'],
  emits: ['cancel-edit', 'save-edit'],
  data () {
    return {
      selectedCopy: null,
      nameError: '',
      mailError: '',
      userTypeError: ''
    }
  },
  created () {
    this.selectedCopy = User.copyConstructor(this.selectedUser)
  },
  methods: {
    cancel () {
      if (!this.changedData) {
        if (confirm('Are you sure you wnat to discard changes?')) {
          this.$emit('cancel-edit')
        }
      } else {
        this.$emit('cancel-edit', this.selectedUser)
      }
    },
    save () {
      if (this.validateInput()) {
        if (confirm('Are you sure you want to change the access of this user?')) {
          this.$emit('save-edit', this.selectedCopy)
        }
      }
    },
    validateInput () {
      this.nameError = ''
      this.mailError = ''

      let isValid = true

      if (this.create) {
        if (!this.selectedCopy.name) {
          this.nameError = 'Name is required'
          isValid = false
        }

        if (!this.selectedCopy.mail) {
          this.mailError = 'Email is required'
          isValid = false
        }

        if (!this.selectedCopy.userType) {
          this.userTypeError = 'Usertype is required'
          isValid = false
        }
      }

      return isValid
    }
  },
  computed: {
    changedData () {
      // Compare the selectedCopy data with the selectedEvent data
      return !this.selectedUser.equals(this.selectedCopy, this.create)
    }
  }
}
</script>

<template>
<div class="detail-container">
  <div class="black"></div>
  <div class="user">
    <div class="user-info">
      <img :src="selectedUser.profilePic" alt="user image"/>
      <div class="inputInfo">
        <h2 v-if="!create"> {{ selectedUser.name }} </h2>
        <h3 v-if="!create"> {{ selectedUser.mail }} </h3>
        <input v-if="create" type="text" id="name" v-model="selectedCopy.name" placeholder="Username" :style="{ borderColor: nameError ? 'red' : '' }">
        <span class="error-message">{{ nameError }}</span>
        <input v-if="create" type="email" id="email" v-model="selectedCopy.mail" placeholder="Email" :style="{ borderColor: mailError ? 'red' : '' }">
        <span class="error-message">{{ mailError }}</span>
        <select id="select" class="edit-usertype" v-model="selectedCopy.userType" :style="{ borderColor: userTypeError ? 'red' : '' }">
          <option value="ADMIN">Admin</option>
          <option value="ENTREPRENEUR">Entrepreneur</option>
          <option value="PARTNER">Partner</option>
          <option value="SUPERUSER">Superuser</option>
        </select>
        <span class="error-message">{{ userTypeError }}</span>
      </div>
    </div>
    <div class="edit-user-btn">
      <button :disabled="changedData" @click="save">Save</button>
      <button @click="cancel">Cancel</button>
    </div>
  </div>
</div>
</template>

<style scoped>
h1, h2, h3, h4, h5, h6, p {
  color: black;
}
</style>
