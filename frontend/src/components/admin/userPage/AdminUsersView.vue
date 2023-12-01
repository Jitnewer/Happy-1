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
      create: false,
      view: false,
      users: []
    }
  },
  methods: {
    createUser () {
      this.create = true
      this.selectedUser = new User(
        0,
        null,
        'profilepic.png',
        null,
        null,
        null,
        'N/A',
        null,
        null,
        null,
        User.status.Active,
        User.userTypes.Admin,
        null,
        null,
        null
      )

      this.$router.push({ name: 'userDetail', params: { id: this.selectedUser.id } })
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
      this.$router.push({ name: 'userDetail', params: { id: user.id } })
    },
    cancelEdit () {
      this.create = false
      this.$router.push({ name: 'users' })
    },
    async saveUser (user) {
      try {
        const savedUser = await this.usersService.asyncSave(user)
        // console.log(savedUser)
        if (this.create === true) {
          this.users.push(user)
        } else {
          const indexToUpdate = this.users.findIndex(oldUser => oldUser.id === user.id)

          if (indexToUpdate >= 0) {
            this.users.splice(indexToUpdate, 1, user)
          }
        }

        this.create = false
        this.$router.push({ name: 'users' })
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
    viewUser (user) {
      this.view = true
      this.$router.push({ name: 'adminProfileView', params: { id: user.id } })
    },
    isBanned (user) {
      return user && user.status === User.status.Banned
    },
    back () {
      this.view = false
      this.$router.push({ name: 'users' })
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
    if (localStorage.getItem('admin') === 'false') this.$router.push({ path: '/PageNotFound' })
    this.users = await this.usersService.asyncFindAll()
  },
  watch: {
    '$route' (to, from) {
      console.log(to)
      const userId = parseInt(to.params.id)
      if (userId === 0) {
        return
      }
      if (userId) {
        this.selectedUser = this.users.find(user => user.id === userId)
      } else {
        this.selectedUser = null
      }
    }
  }
}
</script>

<template>
  <div class="container-admin" v-if="!view">
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
            <button class="view-button" @click="viewUser(user)">View</button>
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
  <router-view :selectedUser="selectedUser" @cancel-edit="cancelEdit" @save-edit="saveUser" :create="create" @back="back"/>
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
