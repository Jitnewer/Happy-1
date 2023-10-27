<template>
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
          <img :src="event.image" alt="Event Image">
        </div>
        <div class="event-right">
          <div class="event-right-main">
            <div class="event-right-left">
              <h1>{{ event.name }}</h1>
              <h3>{{ event.location }}</h3>
            </div>
            <div class="event-right-right">
              <p>{{ event.date }}</p>
              <p>{{ event.timeBegin }}-{{ event.timeEnd }}</p>
              <p> {{ formattedPrice(event) }}</p>
            </div>
          </div>
          <div class="event-right-bottom">
            <button>More Info</button>
            <button>Sign In</button>
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
      lastId: 3000,
      events: [],
      search: null,
      filter: 'asc',
      showFilter: false
    }
  },
  methods: {
    toggleFilter () {
      this.showFilter = !this.showFilter
    },
    updateFilter (filterValue) {
      this.filter = filterValue
    },
    nextId () {
      this.lastId = this.lastId + Math.floor(Math.random() * 5) + 1 // Ensure uniqueness
      return this.lastId
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
      const parts = dateString.split('-')
      return new Date(parts[2], parts[1] - 1, parts[0])
    },
    formattedPrice (event) {
      if (event.price !== null) {
        return `€${event.price.toFixed(2)},-`
      } else {
        return 'N/A'
      }
    }
  },
  created () {
    for (let i = 0; i < 12; i++) {
      this.events.push(Event.createSampleEvents2(this.nextId()))
    }
  },
  computed: {
    filteredEventsOnDate () {
      const sortedEvents = this.events.slice().sort((a, b) => {
        const dateA = new Date(this.parseDate(a.date))
        const dateB = new Date(this.parseDate(b.date))
        return this.filter === 'asc' ? dateA - dateB : dateB - dateA
      })

      return this.search ? this.searchEvent(sortedEvents) : sortedEvents
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
