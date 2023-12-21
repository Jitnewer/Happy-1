<script>
import { User } from '@/models/user'

export default {
  name: 'AdminUsersView',
  inject: ['usersService', 'fileUploadService'],
  data () {
    return {
      filter: {
        search: null,
        userType: null
      },
      selectedUser: null,
      create: false,
      edit: false,
      users: []
    }
  },
  methods: {
    createUser () {
      this.create = true
      this.selectedUser = new User(
        0,
        null,
        'assets/profilePic/profilepic.png',
        null,
        null,
        null,
        'N/A',
        0,
        'Happy Hospitality',
        'N/A',
        User.status.Active,
        User.userTypes.Admin,
        'N/A',
        null,
        'Happy Hospitality'
      )

      this.$router.push({ name: 'userDetail', params: { id: this.selectedUser.id } })
    },
    async unblockUser (user) {
      if (confirm('Are you sure you want to unblock this user')) {
        try {
          await this.usersService.asyncSave(user)

          user.status = User.status.Unbanned
        } catch (e) {
          console.log(e.message)
        }
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
    saveUser (user) {
      try {
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
          await this.fileUploadService.asyncDeleteImage(user.profilePic)

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
      this.$router.push({ name: 'profileView', params: { id: user.id } })
    },
    isBanned (user) {
      return user && user.status === User.status.Banned
    },
    selectUserByUrl (routeParam) {
      if (!routeParam) return
      if (routeParam === 0) this.createUser()
      const user = this.users.find(user => user.id === routeParam)

      if (user === undefined) {
        this.$router.push({ path: '/PageNotFound' })
      } else {
        this.selectedUser = user
      }
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

    this.selectUserByUrl(parseInt(this.$route.params.id))

    this.users = this.users.filter(user => {
      return user.id !== parseInt(localStorage.getItem('profileId'))
    })
  },
  watch: {
    '$route' (to) {
      this.selectUserByUrl(parseInt(to.params.id))
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
  <router-view v-if="selectedUser" :selectedUser="selectedUser" @cancel-edit="cancelEdit" @save-edit="saveUser"
               :create="create"/>
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
