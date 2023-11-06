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
          // document.body.style.overflow = 'hidden'
          break
        }
      }
    },
    setSelectedEvent (event) {
      this.$router.push(this.$route.matched[0].path + '/' + event)
    },
    deleteEvent (eventToDelete) {
      const indexToDelete = this.events.findIndex(event => event.id === eventToDelete.id)
      if (indexToDelete !== -1) {
        this.events.splice(indexToDelete, 1)
      }

      this.selectedEvent = null // Clear the selected event
      this.isSelected = !this.isSelected // Update the isSelected flag
      // document.body.style.overflow = 'auto'
      this.$router.push(this.$route.matched[0])
    },
    closeEvent () {
      this.selectedEvent = null
      this.create = false
      this.isSelected = !this.isSelected
      // document.body.style.overflow = 'auto'
      this.$router.push(this.$route.matched[0])
    },
    activateCreateEvent () {
      this.selectedEvent = new Event()
      this.selectedEvent.id = this.generateId()
      this.selectedEvent.image = require('../assets/images/imagePlaceholder.jpg')
      this.create = true
      this.isSelected = !this.isSelected
      // document.body.overflow = 'hidden'
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

      // document.body.style.overflow = 'auto'
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
    },
    formattedPrice (event) {
      if (event.price !== null) {
        return `€${event.price.toFixed(2)},-`
      } else {
        return 'N/A'
      }
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
  <div class="container admin-event" v-if="!isSelected">
      <div class="title">
        <h1>Events</h1>
      </div>
      <div class="filters">
        <input type="text" class="search-filter" id="nameFilter" placeholder="Search for events.." title="Type in a name"
             v-model="filter.search">
        <input class="date-filter" type="date" id="dateFilter" v-model="filter.date">
        <button class="create-btn" @click="activateCreateEvent">Create</button>
      </div>
      <div class="events">
        <div class="event" v-for="event in filteredEvents" :key="event.id" @click="setSelectedEvent(event.id)">
          <div class="event-left">
            <img :src="event.image" alt="Event Image">
          </div>
          <div class="event-right">
            <div class="event-right-main">
              <div class="event-right-left">
                <h1>{{ event.city }}</h1>
                <h2>{{ event.name }}</h2>
                <h3>{{ event.location }}</h3>
              </div>
              <div class="event-right-right">
                <p>{{ event.date }}</p>
                <p>{{ event.timeBegin }}-{{ event.timeEnd }}</p>
                <p> {{ formattedPrice(event) }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <router-view v-else :create="create" :selectedEvent="selectedEvent" @delete-event="deleteEvent"
                 @deselect-event="closeEvent" @save-event="saveEvent"
    />
</template>

<style scoped>
.container {
  margin-top: 0;
  margin-right: 0;
  width: 100%;
  max-height: calc(100vh - 119px);
}

.events {
  z-index: 1;
  display: flex;
  padding: 0 10px;
  margin-left: 20px;
  flex-wrap: wrap;
  flex-direction: row;
  gap: 10px;
  justify-content: left;
}

.admin-event:where(h1, h2, h3, h4, h5, h6, p)  {
  color: black;
}

.event {
  max-width: 450px;
}

@media (max-width: 1030px) {
  .events {
    padding: 0;
    margin-bottom: 0;
    max-height: 758px;
  }

  .container {
    max-height: 760px;
  }
}
</style>
