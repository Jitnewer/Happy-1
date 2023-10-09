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
          this.isSelected = true
          document.body.style.overflow = 'hidden'
          break
        }
      }
    },
    setSelectedEvent (event) {
      this.$router.push(this.$route.matched[0].path + '/' + event.id)
    },
    deleteEvent (eventToDelete) {
      if (confirm('Are you sure you want to delete this event?')) {
        const indexToDelete = this.events.findIndex(event => event.id === eventToDelete.id)
        if (indexToDelete !== -1) {
          this.events.splice(indexToDelete, 1)
        }
        // eslint-disable-next-line no-unreachable-loop
        for (let i = 0; i < this.usedIds.length; i++) {
          if (this.usedIds[i] === eventToDelete.id) this.usedIds.splice(this.usedIds[i], 1)
          break
        }
        this.selectedEvent = null // Clear the selected event
        this.isSelected = false // Update the isSelected flag
        document.body.style.overflow = 'auto'
        this.$router.push(this.$route.matched[0])
      }
    },
    closeEvent () {
      if (confirm('Are you sure you don\'t want to save changes?')) {
        this.selectedEvent = null
        this.create = false
        this.isSelected = false
        document.body.style.overflow = 'auto'

        this.$router.push(this.$route.matched[0])
      }
    },
    activateCreateEvent () {
      this.selectedEvent = new Event()
      this.selectedEvent.id = this.generateId()
      this.selectedEvent.image = require('../assets/images/imagePlaceholder.jpg')
      this.create = true
      this.isSelected = true
      document.body.overflow = 'hidden'
      this.$router.push(this.$route.matched[0].path + '/' + this.selectedEvent.id)
    },
    saveEvent (event) {
      if (confirm('Are you sure you want to save changes to event?')) {
        if (!this.create) {
          // Find the index of the selected item in the array
          const selectedIndex = this.events.findIndex(oldEvent => oldEvent.id === event.id)

          if (selectedIndex !== -1) {
            // Replace the selected item with the new item
            this.events.splice(selectedIndex, 1, event)
          }

          // Reset selected item and clear isSelected
          this.selectedEvent = null
          this.isSelected = false
        } else {
          this.events.push(event)
          this.usedIds.push(event.id)
          this.isSelected = false
          this.selectedEvent = null
          this.create = false
        }
      }
      document.body.style.overflow = 'auto'
      this.$router.push(this.$route.matched[0])
    },
    generateId () {
      let newId
      do {
        // Generate a new ID (you can use any method you prefer)
        newId = Math.floor((Math.random() * 100) + 1) // Replace with your preferred method to generate IDs

        // If the newId is in usedIds, repeat the loop to generate a new one
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
              <h2 class="event-price" v-if="event.price">&#x20AC; {{ event.price }}</h2>
            </div>
            <div>
              <h3><i class="fa-regular fa-calendar-days"></i>{{ event.date }}</h3>
              <h3><i class="fa-regular fa-clock"></i>{{ event.timeBegin }} - {{ event.timeEnd }}</h3>
              <h3><i class="fa-regular fa-location-dot"></i>{{ event.location }}</h3>
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
</style>
