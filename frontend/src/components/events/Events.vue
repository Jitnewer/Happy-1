<template>
  <div class="container">
    <div class="events-main">
      <div class="events-main-left">
        <h1>Events</h1>
        <button>Filter</button>
      </div>
      <div class="events-main-right">
        <input type="text" placeholder="Search for events....">
        <button>Search</button>
      </div>
    </div>
    <div class="events">
      <div class="event" v-for="event in filteredEventsOnDate" :key="event.id">
        <div class="event-left">
          <img :src="event.image" alt="Event Image">
        </div>
        <div class="event-right">
          <div class="event-right-left">
            <h1>{{ event.name }}</h1>
            <h3>{{ event.location }}</h3>
          </div>
          <div class="event-right-right">
            <p>{{ event.date }}</p>
            <p>{{ event.timeBegin }} - {{ event.timeEnd }}</p>
            <p>{{ event.price }}</p>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { Event } from '@/models/event'

export default {
  name: 'Events.vue',
  data () {
    return {
      usedIds: [],
      events: []
    }
  },
  methods: {
    nextId () {
      this.lastId = this.lastId + Math.floor(Math.random() * 5) + 1 // Ensure uniqueness
      return this.lastId
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
  created () {
    for (let i = 0; i < 12; i++) {
      this.events.push(Event.createSampleEvents(i))
    }
    for (let i = 0; i < this.events.length; i++) {
      this.usedIds.push(this.events[i].id)
    }
  },
  computed: {
    filteredEventsOnDate () {
      return this.events.slice().sort((a, b) => new Date(a.date) - new Date(b.date))
    }
  }
}
</script>
