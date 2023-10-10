<script>
import { Event } from '@/models/event'

export default {
  name: 'AdminEventView',
  data () {
    return {
      usedIds: [],
      selectedEvent: null,
      isSelected: false,
      create: false,
      filter: {
        search: '',
        date: null
      },
      events: []
    }
  },
  methods: {
    setByUrl () {
      for (let i = 0; i < this.events.length; i++) {
        if (parseInt(this.$route.params.id) === this.events[i].id) {
          this.selectedEvent = this.events[i]
          this.isSelected = !this.isSelected
          document.body.style.overflow = 'hidden'
          break
        }
      }
    },
    setSelectedEvent (event) {
      this.$router.push(this.$route.matched[0].path + '/' + event.id)
    },
    deleteEvent (eventToDelete) {
      const indexToDelete = this.events.findIndex(event => event.id === eventToDelete.id)
      if (indexToDelete !== -1) {
        this.events.splice(indexToDelete, 1)
      }

      this.selectedEvent = null // Clear the selected event
      this.isSelected = !this.isSelected // Update the isSelected flag
      document.body.style.overflow = 'auto'
      this.$router.push(this.$route.matched[0])
    },
    closeEvent () {
      this.selectedEvent = null
      this.create = false
      this.isSelected = !this.isSelected
      document.body.style.overflow = 'auto'
      this.$router.push(this.$route.matched[0])
    },
    activateCreateEvent () {
      this.selectedEvent = new Event()
      this.selectedEvent.id = this.generateId()
      this.selectedEvent.image = require('../assets/images/imagePlaceholder.jpg')
      this.create = true
      this.isSelected = !this.isSelected
      document.body.overflow = 'hidden'
      this.$router.push(this.$route.matched[0].path + '/' + this.selectedEvent.id)
    },
    saveEvent (event) {
      if (!this.create) {
        // Find the index of the selected item in the array
        const selectedIndex = this.events.findIndex(oldEvent => oldEvent.id === event.id)
        if (selectedIndex !== -1) {
          // Replace the selected item with the new item
          this.events.splice(selectedIndex, 1, event)
        }

        // Reset selected item and clear isSelected
        this.selectedEvent = null
        this.isSelected = !this.isSelected
      } else {
        this.events.push(event)
        this.usedIds.push(event.id)
        this.isSelected = !this.isSelected
        this.selectedEvent = null
        this.create = false
      }

      document.body.style.overflow = 'auto'
      this.$router.push(this.$route.matched[0])
    },
    generateId () {
      let newId
      do {
        // Generate a new ID (you can use any method you prefer)
        newId = Math.floor((Math.random() * 100) + 1) // Replace with your preferred method to generate IDs
      } while (this.usedIds.includes(newId))
      // Once a unique ID is generated, return it
      return newId
    }
  },
  computed: {
    filteredEvents () {
      if (!this.filter.date && !this.filter.search) {
        // If neither date nor search filter is set, return all events
        return this.events
      }

      // Filter events based on date and/or search filter
      return this.events.filter(event => {
        // Check if the event date matches the date filter (if set)
        const dateMatch = !this.filter.date || event.date === this.filter.date

        // Check if the event name contains the search filter (if set)
        const searchMatch = !this.filter.search || event.name.toLowerCase().includes(this.filter.search.toLowerCase())

        // Return true if both date and search filters match, or if neither filter is set
        return dateMatch && searchMatch
      })
    }
  },
  created () {
    for (let i = 0; i < 4; i++) {
      this.events.push(Event.createSampleEvents(i))
    }
    for (let i = 0; i < this.events.length; i++) {
      this.usedIds.push(this.events[i].id)
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
  <div class="container">
    <div class="title">
      <h2>Events</h2>
    </div>
    <div class="filters">
      <input type="text" class="search-filter" id="nameFilter" placeholder="Search for events.." title="Type in a name"
             v-model="filter.search">
      <input class="date-filter" type="date" id="dateFilter" v-model="filter.date">
      <button class="create-btn" @click="activateCreateEvent">Create</button>
    </div>
    <div class="events-list">
      <ul>
        <li v-for="event in filteredEvents" :key="event.id" @click="setSelectedEvent(event)">
          <img class="event-image" :src="event.image" alt="Event Image">
          <div class="event-info-master">
            <div class="name-price">
              <h2 class="event-name">{{ event.name }}</h2>
              <h3 class="event-price" v-if="event.price">&#x20AC; {{ event.price }}</h3>
            </div>
            <div>
              <h4><i class="fa-regular fa-calendar-days"></i>{{ event.date }}</h4>
              <h4><i class="fa-regular fa-clock"></i>{{ event.timeBegin }} - {{ event.timeEnd }}</h4>
              <h4><i class="fa-regular fa-location-dot"></i>{{ event.location }}</h4>
            </div>
            <p> {{ event.info }}</p>
          </div>
        </li>
      </ul>
    </div>
  </div>
  <router-view class="event-detail" v-if="isSelected" :create="create" :selectedEvent="selectedEvent" @delete-event="deleteEvent"
    @deselect-event="closeEvent" @save-event="saveEvent"
  />
</template>

<style scoped>
h1, h2, h3, h4, h5, h6, p {
  color: black
}
</style>
