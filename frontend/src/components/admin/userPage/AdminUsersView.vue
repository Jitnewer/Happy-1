<script>
import { User } from '@/models/user'
export default {
  name: 'AdminUsersView',
  inject: ['usersServiceAdmin', 'fileUploadService'],
  data () {
    return {
      filter: {
        search: null,
        userType: ''
      },
      selectedUser: null,
      create: false,
      users: []
    }
  },
  methods: {
    /**
     * Initialize a new User
     */
    createUser () {
      this.create = true
      this.selectedUser = new User(
        0,
        null,
        '/assets/profilePic/profilepic.png',
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
      document.body.style.overflow = 'hidden'

      this.$router.push({ name: 'userDetail', params: { id: this.selectedUser.id } })
    },
    async unblockUser (user) {
      if (confirm('Are you sure you want to unblock this user')) {
        try {
          user.status = User.status.Unbanned
          await this.usersServiceAdmin.asyncSave(user)
        } catch (e) {
          console.log(e.toJSON())
          this.$store.commit('setError', true)
          this.$store.commit('setErrorMessage', e.toJSON().error)
          setTimeout(() => {
            this.$store.commit('setError', false)
            this.$store.commit('setErrorMessage', null)
          }, 8000)
        }
      }
    },
    async blockUser (user) {
      if (confirm('Are you sure you want to block this user?')) {
        user.status = User.status.Banned
        await this.usersServiceAdmin.asyncSave(user)
      }
    },
    editUser (user) {
      this.$router.push({ name: 'userDetail', params: { id: user.id } })
    },
    cancelEdit () {
      this.create = false
      document.body.style.overflow = 'auto'
      this.$router.push({ name: 'users' })
    },
    async saveUser (user) {
      try {
        // Update the users list
        if (this.create) {
          this.users.push(user)
        } else {
          const indexToUpdate = this.users.findIndex(oldUser => oldUser.id === user.id)

          if (indexToUpdate >= 0) {
            this.users.splice(indexToUpdate, 1, user)
          }
        }

        // Disable create/edit mode
        document.body.style.overflow = 'auto'
        this.create = false
        this.$router.push({ name: 'users' })
      } catch (e) {
        console.log(e)
      }
    },
    /**
     * Delete user, and it's corresponding profilePic if profilePic does not equal image placeholder
     * @param user
     * @returns {Promise<void>}
     */
    async deleteUser (user) {
      if (confirm('Are you sure you want to delete this user?')) {
        try {
          await this.usersServiceAdmin.asyncDeleteById(user.id)

          // Prevent default profile picture placeholder from being deleted
          if (user.profilePic !== 'assets/profilePic/profilepic.png') {
            await this.fileUploadService.asyncDeleteImage(user.profilePic)
          }

          // Update the users list
          const indexToUpdate = this.users.findIndex(oldUser => oldUser.id === user.id)

          if (indexToUpdate >= 0) {
            this.users.splice(indexToUpdate, 1)
          }
        } catch (e) {
          console.log(e)
        }
      }
    },
    /**
     * Redirect to selected user page
     * @param user
     */
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
    /**
     * Filters out the current logged in user from the list
     * @returns {[]|*[]}
     */
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
    this.users = await this.usersServiceAdmin.asyncFindAll()

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
    <div class="breadcrum-admin breadcrum-admin-margin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <p>></p>
      <router-link :to="{ name: 'users' }">Users</router-link>
    </div>
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
      <button class="admin-create" @click="createUser">Create</button>
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
            <button class="admin-view" @click="viewUser(user)">View</button>
            <button class="admin-edit" @click="editUser(user)">Edit</button>
            <button v-if="!isBanned(user)" class="block-button" @click="blockUser(user)">Block</button>
            <button v-if="isBanned(user)" class="block-button" @click="unblockUser(user)">Unblock</button>
            <button class="admin-delete" @click="deleteUser(user)">Delete</button>
          </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <router-view :selectedUser="selectedUser" @cancel-edit="cancelEdit" @save-edit="saveUser" :create="create"/>
</template>

<style scoped>
.container-admin {
  margin-top: 1rem;
}
</style>
