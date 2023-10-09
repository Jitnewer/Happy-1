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
        this.$emit('cancel-edit')
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
        <h2> {{ selectedUser.name }} </h2>
        <h3> {{ selectedUser.mail }} </h3>
        <input v-if="create" type="text" id="name" v-model="selectedCopy.name" placeholder="Username" :style="{ borderColor: nameError ? 'red' : '' }">
        <span class="error-message">{{ nameError }}</span>
        <input v-if="create" type="email" id="email" v-model="selectedCopy.mail" placeholder="Email" :style="{ borderColor: mailError ? 'red' : '' }">
        <span class="error-message">{{ mailError }}</span>
        <select class="edit-usertype" v-model="selectedCopy.userType" :style="{ borderColor: userTypeError ? 'red' : '' }">
          <option value="ADMIN">Admin</option>
<!--          <option value="ENTREPRENEUR">Entrepreneur</option>-->
<!--          <option value="PARTNER">Partner</option>-->
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
.detail-container {
  position: fixed;
  top: 0;
  width: 100vw;
  height: 100vh;
}

.black {
  background-color: black;
  opacity: 0.5;
  height: 100%;
  width: 100%;
}

.user {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 500px;
  height: min-content;
  margin: auto;
  border-radius: 5px;
  background-color: white;
  border: 1px solid black;
  display: flex;
  flex-direction: column;
}

h2, h3, input {
  margin: 10px 0 0 20px;
}

.user-info {
  display: flex;
  flex-direction: row;
  padding: 10px;
  gap: 20px;
}

.user-info img {
  height: 150px;
  width: 150px;
  margin-left: 20px;
}

.inputInfo {
  display: flex;
  flex-direction: column;
}

.edit-usertype {
  width: 200px;
  height: 40px;
  margin-left: 20px;
  margin-top: 10px;
}

.edit-user-btn {
  display: flex;
  flex-direction: row;
  gap: 20px;
  justify-content: right;
  padding: 10px 10px 10px 0;
}

.edit-user-btn button {
  background-color: #FECC32;
  color: white;
  padding: 12px 16px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
}

button:hover {
  background-color: #E8B003;
}

input {
  height: 30px;
  width: 200px;
  border-radius: 5px;
}

.error-message {
  color: red;
  font-size: 12px;
  margin-left: 20px;
  margin-top: 4px;
}
</style>
