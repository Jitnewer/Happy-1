<template>
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'events' }">Events</router-link>
  </div>
  <div class="container">
    <div class="events-main">
      <div class="events-main-left">
        <h1>Events</h1>
        <button class="filter-button" @click="toggleFilter">Filter</button>
        <transition name="filter">
          <div v-if="showFilter" class="event-filter">
            <div class="filter-desc">
              <p>Descending</p>
              <input class="desc" name="sort" type="radio" value="desc" v-model="filter" @change="updateFilter('desc')">
            </div>
            <div class="filter-asc">
              <p>Ascending</p>
              <input class="asc"  name="sort" type="radio" value="asc" v-model="filter" @change="updateFilter('asc')">
            </div>
          </div>
        </transition>
      </div>
      <div class="events-main-right">
        <input v-model="search" type="text" placeholder="Search for events....">
      </div>
    </div>
    <div class="events">
      <div class="event" v-for="event in filteredEventsOnDate" :key="event.id">
        <div class="event-left">
          <img :src="event.image ? require(`../../${event.image}`) : ''" alt="Image">
        </div>
        <div class="event-right">
          <div class="event-right-main">
            <div class="event-right-left">
              <h1>{{ event.city }}</h1>
              <h2>{{ event.name }}</h2>
              <h3>{{ event.location }}</h3>
            </div>
            <div class="event-right-right">
              <p>{{ parseDate(event.date)  }}</p>
              <p>{{ event.timeBegin.slice(0, 5) }} - {{ event.timeEnd.slice(0, 5) }}</p>
              <p> {{ formattedPrice(event) }}</p>
            </div>
          </div>
          <div class="event-right-bottom2">
            <button @click="selectEventMoreInfo(event)" >More Info</button>
            <button v-if="!isEventSignedIn(event)" @click="toggleSignIn(event)">Sign In</button>
            <button :disabled="disableSignOut(event)" v-if="isEventSignedIn(event)" @click="toggleSignOut(event)">Sign Out</button>

          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-if="showSignIn" class="sign-in">
    <div class="sign-in-title">
      <h1>Are you sure you want to sign in?</h1>
    </div>
    <div class="sign-in-buttons">
      <button @click="toggleSignIn">Cancel</button>
      <button @click="signIn">Sign In</button>
    </div>
  </div>
  <div v-if="showSignOut" class="sign-in">
    <div class="sign-in-title">
      <h1>Are you sure you want to sign Out?</h1>
    </div>
    <div class="sign-in-buttons">
      <button @click="toggleSignOut">Cancel</button>
      <button @click="signOut">Sign Out</button>
    </div>
  </div>
  <div v-if="signInIn || signInOut">
    <div class="loading-animation">
      <div class="loadingio-spinner-spinner-p60giii2jcd"><div class="ldio-4bagb6lp0r">
        <div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div>
      </div></div>
    </div>
  </div>
  <div v-if="signInComplete || signOutComplete">
    <div class="sign-in-complete">
      <div class="success-checkmark">
        <div class="check-icon">
          <span class="icon-line line-tip"></span>
          <span class="icon-line line-long"></span>
          <div class="icon-circle"></div>
          <div class="icon-fix"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Event } from '@/models/event'
import { mapGetters } from 'vuex'
import { reactive } from 'vue'
import ErrorPopUp from '@/components/errorPopUp.vue'

export default {
  name: 'Events.vue',
  inject: ['eventsService', 'usersService', 'userEventsService', 'sessionSBService'],
  data () {
    return {
      lastId: 3000,
      search: null,
      filter: null,
      showFilter: false,
      signedIn: false,
      showSignIn: false,
      showSignOut: false,
      signInIn: false,
      selectedEventsSignIn: null,
      selectedEventsSignOut: null,
      signInComplete: false,
      signOutComplete: false,
      signInOut: false,
      user: null,
      signedInEvents: []
    }
  },
  async created () {
    try {
      this.filter = this.$route.query.sort
      if (this.filter == null) {
        this.filter = 'asc'
      }
      this.$router.push({ name: 'events', query: { sort: this.filter } })
      await this.eventsService.asyncFindAll()
      const userAndToken = await this.sessionSBService.asyncFindByEmail(JSON.parse(localStorage.getItem('userDetails')).mail)
      this.user = userAndToken.body
      await this.userEventsService.asyncFindByProperty(this.user.id, 'eventsByUser')
      this.signedInEvents = this.associatedEvents.map((event) => event.id)
    } catch (e) {
      console.error(e.toJSON())
    }
  },
  methods: {
    toggleFilter () {
      this.showFilter = !this.showFilter
    },
    disableSignOut (event) {
      // Get the current date
      const today = new Date()

      // Get the date string from the event and split it into day, month, and year
      const [day, month, year] = event.getDate().split('-').map(Number)

      // Create a Date object for the event date
      const eventDate = new Date(year, month - 1, day)

      // Calculate the difference in milliseconds
      const timeDifference = eventDate - today

      // Calculate the difference in days
      const daysDifference = timeDifference / (1000 * 60 * 60 * 24)

      // Check if the event is within 5 days from today
      return daysDifference <= 5
    },
    toggleSignIn (event) {
      this.showSignIn = !this.showSignIn
      if (this.selectedEventsSignIn !== null) {
        this.selectedEventsSignIn = null
      } else {
        this.selectedEventsSignIn = event
      }
      console.log(this.selectedEventsSignIn)
    },
    isSignedIn (event) {
      try {
        for (let i = 0; i < this.user.userEvents.length; i++) {
          if (this.user.userEvents[i].event.id === event.id) return true
        }
        return false
      } catch (e) {
        console.log(e.toJSON())
        this.$store.commit('setError', true)
        this.$store.commit('setErrorMessage', e.toJSON().error)
        setTimeout(() => {
          this.$store.commit('setError', false)
          this.$store.commit('setErrorMessage', null)
        }, 8000)
      }
    },
    updateFilter (filterValue) {
      this.filter = filterValue
      this.$router.push({ name: 'events', query: { sort: filterValue } })
    },
    toggleSignOut (event) {
      this.showSignOut = !this.showSignOut
      if (this.selectedEventsSignOut !== null) {
        this.selectedEventsSignOut = null
      } else {
        this.selectedEventsSignOut = event
      }
    },
    async signIn () {
      this.showSignIn = false
      this.signInIn = true
      // First timeout: Add participant after 10 seconds
      setTimeout(() => {
        this.userEventsService.asyncAddEntityToEntity(this.user.id, this.selectedEventsSignIn.id, 'addUserToEvent')
      }, 10000)
      // Second timeout: Hide signInIn and set signInComplete after 10 seconds
      setTimeout(() => {
        this.signInIn = false
        this.signInComplete = true
        // Third timeout: Reset selectedEventsSignIn after 5 seconds
        setTimeout(() => {
          this.signInComplete = false
          this.signedInEvents.push(this.selectedEventsSignIn.id)
          this.selectedEventsSignIn = null
          this.$store.commit('setSuccess', true)
          this.$store.commit('setSuccessMessage', 'Signed in for an event!')
          setTimeout(() => {
            this.$store.commit('setSuccess', false)
            this.$store.commit('setSuccessMessage', null)
          }, 8000)
        }, 3000)
      }, 7000)
    },
    async signOut () {
      this.showSignOut = false
      this.signInOut = true

      // First timeout: Add participant after 10 seconds
      setTimeout(() => {
        this.userEventsService.asyncRemoveEntityFromEntity(this.user.id, this.selectedEventsSignOut.id, 'removeUserFromEvent')
      }, 10000)

      // Second timeout: Hide signInIn and set signInComplete after 10 seconds
      setTimeout(() => {
        this.signInOut = false
        this.signOutComplete = true

        // Third timeout: Reset selectedEventsSignIn after 5 seconds
        setTimeout(() => {
          this.signOutComplete = false
          const index = this.signedInEvents.indexOf(this.selectedEventsSignOut.id)
          if (index !== -1) {
            this.signedInEvents.splice(index, 1)
          }
          this.selectedEventsSignOut = null
          this.$forceUpdate()
          this.$store.commit('setSuccess', true)
          this.$store.commit('setSuccessMessage', 'Signed out for an event!')
          setTimeout(() => {
            this.$store.commit('setSuccess', false)
            this.$store.commit('setSuccessMessage', null)
          }, 8000)
        }, 3000)
      }, 7000)
    },
    async selectEventMoreInfo (event) {
      this.$router.push({
        name: 'event',
        params: { id: event.id },
        query: { sort: this.filter }
      })
      await this.eventsService.asyncFindById(event.id)
    },
    searchEvent () {
      return this.events.filter(event => {
        if (event.name.toLowerCase().includes(this.search.toLowerCase())) {
          return true
        } else if (event.location.toLowerCase().includes(this.search.toLowerCase())) {
          return true
        } else if (this.search === null) {
          return true
        }
        return false
      })
    },
    handleOutsideClick (event) {
      if (!event.target.matches('.event-filter') && !event.target.matches('.filter-button') && !event.target.matches('.asc') && !event.target.matches('.desc')) {
        this.showFilter = false
      }
    },
    parseDate (dateString) {
      const dateObject = new Date(dateString)
      const day = dateObject.getDate()
      const month = dateObject.getMonth() + 1
      const year = dateObject.getFullYear()

      return `${day}-${month}-${year}`
    },
    formattedPrice (event) {
      if (event.price !== null) {
        return `€${event.price.toFixed(2).replace('.', ',')},-`
      } else {
        return 'N/A'
      }
    }
  },
  computed: {
    events () {
      return this.eventsService.entities
    },
    associatedEvents () {
      return this.userEventsService.entities
    },
    filteredEventsOnDate  () {
      if (Array.isArray(this.events)) {
        if (this.events.length > 1) {
          const sortedEvents = this.events.slice().sort((a, b) => {
            const dateA = new Date(a.date)
            const dateB = new Date(b.date)
            return this.filter === 'asc' ? dateA - dateB : dateB - dateA
          })
          return this.search ? this.searchEvent(sortedEvents) : sortedEvents
        }
        return this.events
      }
      return null
    },
    isEventSignedIn () {
      return (event) => {
        return this.signedInEvents.includes(event.id)
      }
    }
  },
  mounted () {
    window.addEventListener('click', this.handleOutsideClick)
  },
  beforeUnmount () {
    window.removeEventListener('click', this.handleOutsideClick)
  }
}
</script>
