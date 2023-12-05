<script>
import { Event } from '@/models/event'

export default {
  name: 'AdminEventView',
  inject: ['eventsService'],
  data () {
    return {
      selectedEvent: null,
      create: false,
      filter: {
        search: '',
        date: null
      },
      events: []
    }
  },
  methods: {
    setSelectedEvent (event) {
      this.$router.push({ name: 'adminEventDetail', params: { id: event } })
    },
    async deleteEvent (eventToDelete) {
      const indexToDelete = this.events.findIndex(event => event.id === eventToDelete.id)
      if (indexToDelete !== -1) {
        this.events.splice(indexToDelete, 1)
      }

      this.selectedEvent = null // Clear the selected event
      this.$router.push({ name: 'adminEvents' })
    },
    closeEvent () {
      this.selectedEvent = null
      this.create = false
      this.$router.push({ name: 'adminEvents' })
    },
    activateCreateEvent () {
      this.selectedEvent = new Event()
      this.selectedEvent.id = 0
      this.selectedEvent.image = 'imagePlaceholder.jpg'
      this.create = true
      // this.$router.push(this.$route.matched[0].path + '/' + this.selectedEvent.id)
      this.$router.push({ name: 'adminEventDetail', params: { id: this.selectedEvent.id } })
    },
    async saveEvent (event) {
      try {
        const createdEvent = await this.eventsService.asyncSave(event)

        if (this.create === true) {
          this.events.push(createdEvent)
        } else {
          const indexToUpdate = this.events.findIndex(oldEvent => oldEvent.id === event.id)

          if (indexToUpdate >= 0) {
            this.events.splice(indexToUpdate, 1, createdEvent)
          }
        }
        this.selectedEvent = null
        this.create = false

        this.$router.push({ name: 'adminEvents' })
      } catch (e) {
        console.error(e)
      }
    },
    formattedPrice (event) {
      if (event.price !== null) {
        return `€${event.price.toFixed(2)},-`
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
  async created () {
    if (localStorage.getItem('admin') === null || localStorage.getItem('admin') === false) this.$router.push({ path: '/PageNotFound' })
    this.events = await this.eventsService.asyncFindAll()
  },
  watch: {
    '$route' (to, from) {
      console.log(to)
      const eventId = parseInt(to.params.id)
      if (eventId === 0) {
        return
      }
      if (eventId) {
        this.selectedEvent = this.events.find(event => event.id === eventId)
      } else {
        this.selectedEvent = null
      }
    }
  }
}
</script>

<template>
  <div class="container-admin admin-event" v-if="!selectedEvent">
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
        <div class="event" v-for="event in filteredEvents" :key="event.id">
          <div class="event-left">
            <img :src="require(`../../../assets/images/${event.image}`)" alt="Event Image">
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
            <div class="event-right-bottom">
              <button @click="setSelectedEvent(event.id)">Edit</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <router-view :create="create" :selectedEvent="selectedEvent" @delete-event="deleteEvent"
                 @deselect-event="closeEvent" @save-event="saveEvent"
    />
</template>

<style scoped>
.container-admin {
  margin-left: 1rem;
  margin-top: 1rem;
  width:100%
}

.events {
  z-index: 1;
  display: flex;
  padding: 0 10px;
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

.event-right-bottom {
  display: flex;
  flex-direction: row;
  justify-content: right;
}

@media (max-width: 1030px) {
  .events {
    padding: 0;
    margin-bottom: 0;
  }
}
</style>
