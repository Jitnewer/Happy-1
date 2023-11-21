<script>
import { User } from '@/models/user'

export default {
  name: 'AdminUserDetail',
  props: ['selectedUser', 'create'],
  emits: ['cancel-edit', 'save-edit'],
  inject: ['usersService'],
  data () {
    return {
      selectedCopy: null,
      error: {
        firstNameError: '',
        lastNameError: '',
        mailError: '',
        userTypeError: '',
        passwordError: ''
      },
      isValid: true
    }
  },
  created () {
    this.selectedCopy = User.copyConstructor(this.selectedUser)
  },
  methods: {
    cancel () {
      if (confirm('Are you sure you want to discard changes?')) {
        this.$emit('cancel-edit', this.selectedUser)
      }
    },
    save () {
      if (confirm('Are you sure you want to change the access of this user?')) {
        if (this.validateFields()) {
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
    validateFirstName () {
      const firstNameFieldIsEmpty = !this.selectedCopy.firstname || this.selectedCopy.firstname.trim() === ''
      return !firstNameFieldIsEmpty && /^[a-zA-Z]{3,}$/.test(this.selectedCopy.firstname)
    },
    validateLastName () {
      const lastNameFieldIsEmpty = !this.selectedCopy.lastname || this.selectedCopy.lastname.trim() === ''
      return !lastNameFieldIsEmpty && /^[a-zA-Z]{3,}$/.test(this.selectedCopy.firstname)
    },
    validatePassWord () {
      const passwordFieldIsEmpty = !this.selectedCopy.password || this.selectedCopy.password.trim() === ''
      return !passwordFieldIsEmpty && this.selectedCopy.password.length > 4
    },
    validateMail () {
      const emailFieldIsEmpty = !this.selectedCopy.mail || this.selectedCopy.mail.trim() === ''
      return !emailFieldIsEmpty && /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(this.selectedCopy.mail)
    },
    validateFields () {
      this.error.firstNameError = ''
      this.error.lastNameError = ''
      this.error.mailError = ''
      this.error.passwordError = ''
      let invalid = false

      if (!this.validateFirstName()) {
        this.error.firstNameError = 'Invalid firstname'
        invalid = true
      }
      if (!this.validateLastName()) {
        this.error.lastNameError = 'Invalid lastname'
        invalid = true
      }
      if (!this.validatePassWord()) {
        this.error.passwordError = 'Invalid password length'
        invalid = true
      }
      if (!this.validateMail()) {
        this.error.mailError = 'Invalid e-mail'
        invalid = true
      }

      if (invalid === true) return false

      return true
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
        <form class="inputInfo">
          <h2 v-if="!create"> {{ selectedCopy.firstname }} {{ selectedCopy.lastname }} </h2>
          <h3 v-if="!create"> {{ selectedCopy.mail }} </h3>
          <input autocomplete="username" v-if="create" type="text" id="firstname" v-model="selectedCopy.firstname" placeholder="firstname" :style="{ borderColor: error.firstNameError ? 'red' : '' }">
          <span class="error-message">{{ error.firstNameError }}</span>
          <input v-if="create" type="text" id="lastname" v-model="selectedCopy.lastname" placeholder="lastname" :style="{ borderColor: error.lastNameError ? 'red' : '' }">
          <span class="error-message">{{ error.lastNameError }}</span>
          <input autocomplete="email" v-if="create" type="email" id="email" v-model="selectedCopy.mail" placeholder="Email" :style="{ borderColor: error.mailError ? 'red' : '' }">
          <span class="error-message">{{ error.mailError }}</span>
          <input autocomplete="new-password" v-if="create" type="password" id="password" v-model="selectedCopy.password" placeholder="Password" :style="{ borderColor: error.passwordError ? 'red' : '' }">
          <span class="error-message">{{ error.passwordError }}</span>
          <select id="select" class="edit-usertype" v-model="selectedCopy.userType" :style="{ borderColor: error.userTypeError ? 'red' : '' }">
            <option value="ADMIN">Admin</option>
            <option value="SUPERUSER">Superuser</option>
          </select>
          <span class="error-message">{{ error.userTypeError }}</span>
        </form>
      </div>
      <div class="edit-user-btn">
        <button class="save" @click="save">Save</button>
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
