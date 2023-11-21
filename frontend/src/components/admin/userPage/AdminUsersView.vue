<script>
import { User } from '@/models/user'

export default {
  name: 'AdminUsersView',
  inject: ['usersService'],
  data () {
    return {
      filter: {
        search: null,
        userType: null
      },
      selectedUser: null,
      isSelected: false,
      create: false,
      users: []
    }
  },
  methods: {
    setByUrl () {
      const routeId = parseInt(this.$route.params.id)
      const foundUser = this.users.find(user => user.id === routeId)
      if (foundUser) {
        this.selectedUser = foundUser
        this.isSelected = true
      }
    },
    createUser () {
      this.create = true
      this.selectedUser = new User(0, require('../../../assets/img/profilepic.png'))
      this.selectedUser.status = User.status.Active
      this.selectedUser.tag = 'N/A'
      this.selectedUser.userType = User.userTypes.Admin
      this.isSelected = !this.isSelected
      this.$router.push(this.$route.matched[0].path + '/' + this.selectedUser.id)
    },
    async unblockUser (user) {
      if (confirm('Are you sure you want to unblock this user')) {
        user.status = User.status.Unbanned
        await this.usersService.asyncSave(user)
      }
    },
    async blockUser (user) {
      if (confirm('Are you sure you want to block this user?')) {
        user.status = User.status.Banned
        await this.usersService.asyncSave(user)
      }
    },
    editUser (user) {
      this.$router.push(this.$route.matched[0].path + '/' + user.id)
    },
    cancel () {
      this.selectedUser = null
      this.isSelected = !this.isSelected
      this.create = false
      this.$router.push(this.$route.matched[0])
    },
    async save (user) {
      try {
        const savedUser = await this.usersService.asyncSave(user)

        if (this.create === true) {
          this.users.push(savedUser)
        } else {
          const indexToUpdate = this.users.findIndex(oldUser => oldUser.id === savedUser.id)

          if (indexToUpdate >= 0) {
            this.users.splice(indexToUpdate, 1, savedUser)
          }
        }
        this.selectedUser = null
        this.isSelected = !this.isSelected
        this.create = false
        this.$router.push(this.$route.matched[0])
      } catch (e) {
        console.log(e)
      }
    },
    async deleteUser (user) {
      if (confirm('Are you sure you want to delete this user?')) {
        try {
          await this.usersService.asyncDeleteById(user.id)
          const indexToUpdate = this.users.findIndex(oldUser => oldUser.id === user.id)

          if (indexToUpdate >= 0) {
            this.users.splice(indexToUpdate, 1)
          }
        } catch (e) {
          console.log(e)
        }
      }
    },
    viewUser () {
      // TODO redirect to profile of user
    },
    isBanned (user) {
      return user && user.status === User.status.Banned
    }
  },
  computed: {
    filterdUsers () {
      if (!this.filter.search && !this.filter.userType) {
        // If neither date nor search filter is set, return all events
        return this.users
      }

      // Filter events based on date and/or search filter
      return this.users.filter(user => {
        // Check if the event date matches the date filter (if set)
        const userTypeMatch = !this.filter.userType || user.userType === this.filter.userType
        // Check if the event name contains the search filter (if set)
        const name = user.firstname + ' ' + user.lastname
        const searchMatch = !this.filter.search || name.toLowerCase().includes(this.filter.search.toLowerCase())
        // Return true if both date and search filters match, or if neither filter is set
        return searchMatch && userTypeMatch
      })
    }
  },
  async created () {
    this.users = await this.usersService.asyncFindAll()
  },
  watch: {
    '$route' () {
      this.setByUrl()
    }
  }
}
</script>

<template>
  <div class="container-admin">
    <div class="title">
      <h1>Users</h1>
    </div>
    <div class="filters">
      <input type="text" class="search-filter" id="nameFilter" placeholder="Search for Users.." title="Type in a name"
             v-model="filter.search">
      <select id="select-master" class="select-usertype" v-model="filter.userType">
        <option value="">Choose userType</option>
        <option value="ADMIN">Admin</option>
        <option value="ENTREPRENEUR">Entrepreneur</option>
        <option value="PARTNER">Partner</option>
        <option value="SUPERUSER">Superuser</option>
      </select>
      <button class="create-btn" @click="createUser">Create</button>
      <button class="notification"><i class="fa-regular fa-bell"></i></button>
    </div>
    <div class="users-list">
      <table class="users-table">
        <thead>
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Email</th>
          <th>Usertype</th>
          <th>Tag</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
          <tr v-bind:class="{'banned': isBanned(user)}" v-for="user in filterdUsers" :key="user.id">
          <td> {{ user.id }}</td>
          <td> {{ user.firstname }} {{ user.lastname}} </td>
          <td> {{ user.mail }}</td>
          <td> {{ user.userType }}</td>
          <td> {{ user.tag }}</td>
          <td> {{ user.status }}</td>
          <td class="buttons">
            <button class="view-button">View</button>
            <button class="edit-button" @click="editUser(user)">Edit</button>
            <button v-if="!isBanned(user)" class="block-button" @click="blockUser(user)">Block</button>
            <button v-if="isBanned(user)" class="block-button" @click="unblockUser(user)">Unblock</button>
            <button class="delete-button" @click="deleteUser(user)">Delete</button>
          </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <router-view v-if="isSelected" :selectedUser="selectedUser" @cancel-edit="cancel" @save-edit="save" :create="create"/>
</template>

<style scoped>
.container-admin {
  margin-top: 1rem;
  width:80%
}

h1, h2, h3, h4, h5, h6, p {
  color: black;
  margin: 0;
}
</style>
