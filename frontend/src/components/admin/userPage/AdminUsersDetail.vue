<script>
import { User } from '@/models/user'

export default {
  name: 'AdminUserDetail',
  props: ['selectedUser', 'create'],
  emits: ['cancel-edit', 'save-edit'],
  inject: ['usersServiceAdmin', 'usersService'],
  data () {
    return {
      selectedCopy: null,
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
    async save () {
      if (this.validateFields) {
        if (confirm('Are you sure you want to change the access of this user?')) {
          this.$emit('save-edit', this.selectedCopy)
        }
      }
    },
    setUpErrorMessage (elementId, message) {
      document.querySelector(elementId).setCustomValidity(message)
      document.querySelector(elementId).reportValidity()
    }
  },
  computed: {
    validateFields () {
      if (!this.validateFirstName) {
        this.setUpErrorMessage('#firstname', 'Firstname must be at least 3 characters and can\'t contain any numbers and symbols')
        return false
      }
      if (!this.validateLastName) {
        this.setUpErrorMessage('#lastname', 'Lastname must be at least 3 characters and can\'t contain any numbers and symbols')
        return false
      }
      if (!this.validateMail) {
        this.setUpErrorMessage('#email', 'Email must contain @ and end with .com/.nl etc.')
        return false
      }
      if (!this.validatePassWord) {
        this.setUpErrorMessage('#password', 'Password must be at least 6 characters')
        return false
      }

      return true
    },
    validateFirstName () {
      const firstNameFieldIsEmpty = !this.selectedCopy.firstname || this.selectedCopy.firstname.trim() === ''
      return !firstNameFieldIsEmpty && /^[a-zA-Z]{2,}$/.test(this.selectedCopy.firstname)
    },
    validateLastName () {
      const lastNameFieldIsEmpty = !this.selectedCopy.lastname || this.selectedCopy.lastname.trim() === ''
      return !lastNameFieldIsEmpty && /^[a-zA-Z]{2,}$/.test(this.selectedCopy.firstname)
    },
    validatePassWord () {
      const passwordFieldIsEmpty = !this.selectedCopy.password || this.selectedCopy.password.trim() === ''
      return !passwordFieldIsEmpty && /^.{6,}$/.test(this.selectedCopy.password)
    },
    validateMail () {
      const emailFieldIsEmpty = !this.selectedCopy.mail || this.selectedCopy.mail.trim() === ''
      return !emailFieldIsEmpty && /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(this.selectedCopy.mail)
    },
    isAdminOrSuperUser () {
      if (this.selectedUser.userType === User.userTypes.Admin) return true
      if (this.selectedUser.userType === User.userTypes.SuperUser) return true
      return false
    }
  }
}
</script>

<template>
  <div class="detail-container" v-if="selectedCopy">
    <div class="black"></div>
    <div class="user">
      <div class="user-info">
        <img id="profile-image" :src="require(`../../../${selectedCopy.profilePic}`)" alt="user image"/>
        <form class="inputInfo">
          <h2 v-if="!create"> {{ selectedCopy.firstname }} {{ selectedCopy.lastname }} </h2>
          <h3 v-if="!create"> {{ selectedCopy.mail }} </h3>
          <input autocomplete="username" v-if="create" type="text" id="firstname" v-model="selectedCopy.firstname" placeholder="firstname">
          <input v-if="create" type="text" id="lastname" v-model="selectedCopy.lastname" placeholder="lastname">
          <input autocomplete="email" v-if="create" type="email" id="email" v-model="selectedCopy.mail" placeholder="Email">
          <input autocomplete="new-password" v-if="create" type="password" id="password" v-model="selectedCopy.password" placeholder="Password">
          <select v-if="isAdminOrSuperUser" id="select" class="edit-usertype" v-model="selectedCopy.userType">
            <option value="ADMIN">Admin</option>
            <option value="SUPERUSER">Superuser</option>
          </select>
          <select v-else id="select" class="edit-usertype" v-model="selectedCopy.userType">
            <option value="ENTREPRENEUR">Entrepreneur</option>
            <option value="PARTNER">Partner</option>
          </select>
        </form>
      </div>
      <div class="edit-user-btn">
        <button class="admin-save" @click="save">Save</button>
        <button class="cancel" @click="cancel">Cancel</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
h1, h2, h3, h4, h5, h6, p {
  padding: 0.2rem 1rem;
  box-shadow: rgba(0, 0, 0, 0.19) 0 10px 20px, rgba(0, 0, 0, 0.23) 0 6px 6px;
  border: none;
}
</style>
