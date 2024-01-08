<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminEvents' }">Events</router-link>
    </div>
    <div class="challenges-admin">
      <div class="title-button">
        <h1>Events</h1>
        <button @click="create()" class="admin-create">Create</button>
      </div>
      <table v-if="events">
        <thead>
        <tr>
          <th>Id</th>
          <th class="adminName">Name</th>
          <th class="adminCity">City</th>
          <th>Location</th>
          <th>Price</th>
          <th class="adminInfo">Info</th>
          <th class="adminDate">Date</th>
          <th class="adminTimeBegin">Time Begin</th>
          <th class="adminTimeEnd">Time End</th>
          <th class="adminSize">Size</th>
          <th class="adminImage">Image</th>
          <th>Users</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="event in events" :key="event.id">
          <td>{{ event.id }}</td>
          <td class="adminName">{{ event.name }}</td>
          <td class="adminCity">{{ event.city }}</td>
          <td>{{ event.location }}</td>
          <td v-if="event.price">{{ formattedPrice(event.price) }}</td>
          <td class="adminInfo">{{ event.info }}</td>
          <td class="adminDate">{{ parseDate(event.date) }}</td>
          <td class="adminTimeBegin" v-if="event.timeBegin">{{ event.timeBegin.slice(0, 5) }}</td>
          <td class="adminTimeEnd" v-if="event.timeEnd">{{ event.timeEnd.slice(0, 5) }}</td>
          <td class="adminSize">{{ event.size }}</td>
          <td class="adminImage"><img :src="event.image ? `https://ik.imagekit.io/happy1hva${event.image}` : ''" alt="Event Image"></td>
          <td><button class="relation" @click="selecteUsers(event)">Check Users</button></td>
          <td>
            <div class="table-buttons">
              <button class="edit" @click="edit(event.id)">Edit</button>
              <button class="delete" @click="remove(event)">Delete</button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      <div class="paragraphs paragraphs-admin" v-if="showUsers">
        <div><button @click="back">Back</button>
          <table>
            <thead>
            <tr>
              <th>Id</th>
              <th>Firstname</th>
              <th>Lastname</th>
              <th>Mail</th>
              <th>Age</th>
              <th>Postal Code</th>
              <th>Profile Picture</th>
              <th>Tag</th>
              <th>Status</th>
              <th>User Type</th>
              <th>Company Type</th>
              <th>Company Name</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="user in sortedUsers" :key="user.id">
              <td>{{ user.id }}</td>
              <td>{{ user.firstname }}</td>
              <td>{{ user.lastname }}</td>
              <td>{{ user.mail }}</td>
              <td>{{ user.age }}</td>
              <td>{{ user.postalCode }}</td>
              <td ><img :src="user.profilePic ? require(`../../../${user.profilePic}`) : ''" class="profile-admin" alt="Event Image"></td>
              <td>{{ user.tag }}</td>
              <td>{{ user.status }}</td>
              <td>{{ user.userType }}</td>
              <td>{{ user.companyType }}</td>
              <td>{{ user.companyName }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EventsAdmin.vue',
  inject: ['eventsService', 'eventsServiceSuperUser', 'userEventsService', 'usersServiceAdmin', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      showUsers: false,
      selectedEvent: null,
      users: null
    }
  },
  methods: {
    create () {
      this.$router.push({ name: 'adminEventCreate' })
    },
    async selecteUsers (event) {
      this.showUsers = !this.showUsers
      this.selectedEvent = event
      this.users = await this.userEventsService.asyncFindByProperty(this.selectedEvent.id, 'usersByEvent')
    },
    back () {
      this.showUsers = false
      this.users = null
    },
    async remove (event) {
      try {
        const response = await this.eventsServiceSuperUser.asyncDeleteById(event.id)
        await this.fileUploadService.asyncDeleteImage(event.image)
        await this.eventsService.asyncFindAll()
        this.$store.commit('setSuccess', true)
        this.$store.commit('setSuccessMessage', response.message)
        setTimeout(() => {
          this.$store.commit('setSuccess', false)
          this.$store.commit('setSuccessMessage', null)
        }, 8000)
      } catch (e) {
        console.error(e.toJSON())
        this.$store.commit('setError', true)
        this.$store.commit('setErrorMessage', e.toJSON().error)
        setTimeout(() => {
          this.$store.commit('setError', false)
          this.$store.commit('setErrorMessage', null)
        }, 8000)
      }
    },
    async updateChallenges () {
      try {
        await this.eventsService.asyncFindAll()
      } catch (e) {
        console.error(e.toJSON())
        this.$store.commit('setError', true)
        this.$store.commit('setErrorMessage', e.toJSON().error)
        setTimeout(() => {
          this.$store.commit('setError', false)
          this.$store.commit('setErrorMessage', null)
        }, 8000)
      }
    },
    edit (id) {
      this.$router.push({ name: 'adminEventEdit', params: { id: id } })
    },
    getFormattedDate (dateString) {
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('nl-NL', options)
    },
    formattedPrice (price) {
      if (price !== null) {
        return `€${price.toFixed(2).replace('.', ',')},-`
      } else {
        return 'N/A'
      }
    },
    parseDate (dateString) {
      const dateObject = new Date(dateString)
      const day = dateObject.getDate()
      const month = dateObject.getMonth() + 1
      const year = dateObject.getFullYear()

      return `${day}-${month}-${year}`
    }
  },
  async created () {
    await this.updateChallenges()
    console.log(this.challenges)
  },
  watch: {
    $route (to, from) {
      if (to.fullPath !== from.fullPath) {
        this.updateChallenges()
      }
    }
  },
  computed: {
    events () {
      return this.eventsService.entities
    },
    sortedUsers () {
      if (this.selectedEvent) {
        if (this.users) {
          if (this.users.length > 1) {
            return this.users.slice().sort((a, b) => a.id - b.id)
          } else {
            return this.users
          }
        }
      }
      return []
    }
  }
}

</script>
