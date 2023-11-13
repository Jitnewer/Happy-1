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
      userTypeError: '',
      isValid: true
    }
  },
  created () {
    this.selectedCopy = User.copyConstructor(this.selectedUser)
  },
  methods: {
    cancel () {
      if (!this.changedData) {
        if (confirm('Are you sure you wnat to discard changes?')) {
          this.$emit('cancel-edit', this.selectedUser)
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
    handleImageUpload (event) {
      const file = event.target.files[0]
      this.selectedCopy.profilePic = URL.createObjectURL(file)
    },
    activateInput () {
      document.querySelector('#file').click()
    },
    validateInput () {
      this.nameError = ''
      this.mailError = ''

      this.isValid = true

      if (this.create) {
        if (!this.selectedCopy.firstname) {
          this.nameError = 'Name is required'
          this.isValid = false
        }

        if (!this.selectedCopy.lastname) {
          this.nameError = 'Name is required'
          this.isValid = false
        }

        if (!this.selectedCopy.mail) {
          this.mailError = 'Email is required'
          this.isValid = false
        } else if (!/^.*@.*\.com$/.test(this.selectedCopy.mail)) {
          this.mailError = 'Invalid email'
          this.isValid = false
        }

        if (!this.selectedCopy.userType) {
          this.userTypeError = 'Usertype is required'
          this.isValid = false
        }
        return this.isValid
      }
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
        <img @click="activateInput" :src="selectedCopy.profilePic" alt="user image"/>
        <input type="file" accept="image/jpeg, image/png, image/jpg" id="file" @change="handleImageUpload">
        <div class="inputInfo">
          <h2 v-if="!create"> {{ selectedCopy.firstname }} {{ selectedCopy.lastname }} </h2>
          <h3 v-if="!create"> {{ selectedCopy.mail }} </h3>
          <input v-if="create" type="text" id="firstname" v-model="selectedCopy.firstname" placeholder="firstname" :style="{ borderColor: nameError ? 'red' : '' }">
          <span class="error-message" v-if="!isValid">{{ nameError }}</span>
          <input v-if="create" type="text" id="lastname" v-model="selectedCopy.lastname" placeholder="lastname" :style="{ borderColor: nameError ? 'red' : '' }">
          <span class="error-message" v-if="!isValid">{{ nameError }}</span>
          <input v-if="create" type="email" id="email" v-model="selectedCopy.mail" placeholder="Email" :style="{ borderColor: mailError ? 'red' : '' }">
          <span class="error-message" v-if="!isValid">{{ mailError }}</span>
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
        <button class="save" :disabled="changedData" @click="save">Save</button>
        <button class="cancel" @click="cancel">Cancel</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
h1, h2, h3, h4, h5, h6, p {
  color: black;
  margin-left: 0;
  padding: 0.2rem 1rem;
  box-shadow: rgba(0, 0, 0, 0.19) 0 10px 20px, rgba(0, 0, 0, 0.23) 0 6px 6px;
  border: none;
}
</style>
