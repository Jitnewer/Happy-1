<script>
import { User } from '@/models/user'

export default {
  name: 'AdminUsersView',
  data () {
    return {
      filter: {
        search: null,
        tag: null
      },
      selectedUser: null,
      isSelected: false,
      create: false,
      users: [],
      usedIds: []
    }
  },
  methods: {
    setByUrl () {
      const routeId = parseInt(this.$route.params.id)
      const foundUser = this.users.find(user => user.id === routeId)
      if (foundUser) {
        this.selectedUser = foundUser
        this.isSelected = true
        document.body.style.overflow = 'hidden'
      }
    },
    createUser () {
      this.create = true
      const id = User.generateId(this.usedIds)
      this.selectedUser = new User(id, require('../assets/img/profilepic.png'), null, null, null, null, 'N/A', 'N/A', 'Inactive', null, null)
      this.usedIds.push(id)
      this.isSelected = !this.isSelected
      this.$router.push(this.$route.matched[0].path + '/' + id)
    },
    unblockUser (user) {
      if (confirm('Are you sure you want to unblock this user')) {
        user.status = User.status.Active
      }
    },
    blockUser (userToBlock) {
      if (confirm('Are you sure you want to block this user?')) {
        const indexToBlock = this.users.findIndex(user => user.id === userToBlock.id)
        if (indexToBlock !== -1) {
          this.users[indexToBlock].status = User.status.Banned
        }
        this.selectedUser = null
        this.isSelected = !this.isSelected
      }
    },
    editUser (user) {
      document.body.style.overflow = 'hidden'
      this.$router.push(this.$route.matched[0].path + '/' + user.id)
    },
    cancel () {
      this.selectedUser = null
      this.isSelected = !this.isSelected
      this.create = false
      document.body.style.overflow = 'auto'
      this.$router.push(this.$route.matched[0])
    },
    save (user) {
      const selectedIndex = this.users.findIndex(oldUser => oldUser.id === user.id)

      if (selectedIndex !== -1) {
        // Replace the selected item with the new item
        this.users.splice(selectedIndex, 1, user)
      } else {
        this.users.push(user)
      }
      this.selectedUser = null
      this.isSelected = !this.isSelected
      this.create = false
      this.$router.push(this.$route.matched[0])
    },
    viewUser () {
      // TODO redirect to profile of user
    },
    isBanned (user) {
      return user.status === User.status.Banned
    }
  },
  computed: {
    filterdUsers () {
      if (!this.filter.search && !this.filter.tag) {
        // If neither date nor search filter is set, return all events
        return this.users
      }

      // Filter events based on date and/or search filter
      return this.users.filter(user => {
        // Check if the event date matches the date filter (if set)
        const tagMatch = !this.filter.tag || user.userType === this.filter.tag
        // Check if the event name contains the search filter (if set)
        const searchMatch = !this.filter.search || user.name.toLowerCase().includes(this.filter.search.toLowerCase())
        // Return true if both date and search filters match, or if neither filter is set
        return searchMatch && tagMatch
      })
    }
  },
  created () {
    for (let i = 0; i < 5; i++) {
      const user = User.createSampleUser()
      this.users.push(user)
      this.usedIds.push(user.id)
    }
  },
  watch: {
    '$route' () {
      this.setByUrl()
    }
  }
}
</script>

<template>
  <div class="user-container">
    <div class="title">
      <h2>Users</h2>
    </div>
    <div class="filters">
      <input type="text" class="search-filter" id="nameFilter" placeholder="Search for Users.." title="Type in a name"
             v-model="filter.search">
      <select id="select-master" class="select-usertype" v-model="filter.tag">
        <option value=""></option>
        <option value="ADMIN">Admin</option>
        <option value="ENTREPRENEUR">Entrepreneur</option>
        <option value="PARTNER">Partner</option>
        <option value="SUPERUSER">Superuser</option>
      </select>
      <button class="create-btn" @click="createUser">Create</button>
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
        <tr v-for="user in filterdUsers" :key="user.id">
          <td> {{ user.id }}</td>
          <td> {{ user.name }}</td>
          <td> {{ user.mail }}</td>
          <td> {{ user.userType }}</td>
          <td> {{ user.tag }}</td>
          <td> {{ user.status }}</td>
          <td class="buttons">
            <button class="view-button">View</button>
            <button class="edit-button" @click="editUser(user)">Edit</button>
            <button v-if="!isBanned(user)" class="block-button" @click="blockUser(user)">Block</button>
            <button v-if="isBanned(user)" class="block-button" @click="unblockUser(user)">Unblock</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
  <router-view v-if="isSelected" :selectedUser="selectedUser" @cancel-edit="cancel" @save-edit="save" :create="create"/>
</template>

<style scoped>
/* Needs to be removed later on styling already exist in the AdminEventView that should be transfered to main.css*/
.user-container {
  font-family: Poppins, serif;
  color: #000000;
  margin: 0 60px;
  height: 100%;
  overflow: hidden;
}

h1, h2, h3, h4, h5, h6, p {
  color: black;
}

.title {
  height: 30px;
  margin-bottom: 20px;
  text-align: left;
}

.filters {
  display: flex;
  flex-direction: row;
  gap: 20px;
  padding: 0 5px;
}

/* Searchbar filter*/
.search-filter {
  padding: 12px 20px 12px 40px;
  border: 1px solid #000000;
  font-size: 16px;
  width: 300px;
  border-radius: 5px;
}

/* Create button */
.create-btn {
  padding: 12px 20px;
  font-size: 16px;
  border-radius: 5px;
  border: none;
  background-color: #FECC32;
  color: #ffffff;
}

.create-btn:hover {
  background-color: #E8B003;
  cursor: pointer;
}
</style>
