<script>
import { Event } from '@/models/event'
export default {
  name: 'EventDetailsView',
  inject: ['eventsService'],
  props: ['selectedEvent', 'create'],
  emits: ['deselect-event', 'delete-event', 'save-event'],
  data () {
    return {
      created: this.create,
      selectedCopy: null
    }
  },
  methods: {
    closeEventDetail () {
      if (confirm('Are you sure you don\'t want to save changes?')) {
        this.$router.push({ name: 'adminEvents' })
      }
    },
    async saveEventDetail () {
      if (this.validateFields()) {
        if (confirm('Are you sure you want to save changes to event?')) {
          try {
            await this.eventsService.asyncSave(this.selectedCopy)
            this.$router.push({ name: 'adminEvents' })
          } catch (e) {
            console.error(e)
          }
        }
      }
    },
    async deleteEventDetail () {
      if (confirm('Are you sure you want to delete this event?')) {
        try {
          await this.eventsService.asyncDeleteById(this.selectedCopy.id)
          this.$router.push({ name: 'adminEvents' })
        } catch (e) {
          console.error(e)
        }
      }
    },
    handleImageUpload (event) {
      const file = event.target.files[0]
      this.selectedCopy.image = URL.createObjectURL(file)
      console.log(this.selectedCopy.image)
    },
    clearInputs () {
      this.selectedCopy.name = null
      this.selectedCopy.date = null
      this.selectedCopy.timeBegin = null
      this.selectedCopy.timeEnd = null
      this.selectedCopy.location = null
      this.selectedCopy.city = null
      this.selectedCopy.price = null
      this.selectedCopy.info = null
    },
    activateInput () {
      document.querySelector('#file').click()
    },
    eventNameIsValid () {
      const eventNameIsEmpty = !this.selectedCopy.name || this.selectedCopy.name.trim() === ''
      return !eventNameIsEmpty && /^.{3,}$/.test(this.selectedCopy.name)
    },
    eventDateIsValid () {
      const eventDateIsEmpty = !this.selectedCopy.date
      return !eventDateIsEmpty && Date.parse(this.selectedCopy.date) > Date.now()
    },
    eventBeginTimeIsValid () {
      return this.selectedCopy.timeBegin !== undefined
    },
    eventEndTimeIsValid () {
      return this.selectedCopy.timeEnd !== undefined
    },
    eventLocationIsValid () {
      const eventLocationIsEmpty = !this.selectedCopy.location || this.selectedCopy.location === ''
      return !eventLocationIsEmpty && /^[a-zA-Z].{3,}$/.test(this.selectedCopy.location)
    },
    eventCityIsValid () {
      const eventCityIsEmpty = !this.selectedCopy.city || this.selectedCopy.city === ''
      return !eventCityIsEmpty && /^[a-zA-Z].{3,}$/.test(this.selectedCopy.location)
    },
    eventPriceIsValid () {
      const eventPriceIsEmpty = !this.selectedCopy.price || this.selectedCopy.price === ''
      return !eventPriceIsEmpty && this.selectedCopy.price >= 0 && this.selectedCopy.price <= 500
    },
    setUpErrorMessage (elementId, message) {
      document.querySelector(elementId).setCustomValidity(message)
      document.querySelector(elementId).reportValidity()
    },
    validateFields () {
      if (!this.eventNameIsValid()) {
        this.setUpErrorMessage('#edit-event-name', 'Name must be more than 3 characters')
        return false
      }
      if (!this.eventDateIsValid()) {
        this.setUpErrorMessage('#edit-event-date', 'Event date must be further in the future')
        return false
      }
      if (!this.eventBeginTimeIsValid()) {
        this.setUpErrorMessage('#edit-event-time-begin', 'This field is required')
        return false
      }
      if (!this.eventEndTimeIsValid()) {
        this.setUpErrorMessage('#edit-event-time-end', 'This field is required')
        return false
      }
      if (!this.eventLocationIsValid()) {
        this.setUpErrorMessage('#edit-event-location', 'Location must be more than 3 characters and can\'t contain numbers or symbols')
        return false
      }
      if (!this.eventCityIsValid()) {
        this.setUpErrorMessage('#edit-event-city', 'City must be more than 3 characters and can\'t contain numbers or symbols')
        return false
      }
      if (!this.eventPriceIsValid()) {
        this.setUpErrorMessage('#edit-event-price', 'Price must be 0 - 500')
        return false
      }

      return true
    },
    async loadEvent () {
      const eventId = this.$route.params.id
      if (eventId) {
        try {
          this.event = await this.eventsService.asyncFindById(eventId)
          this.selectedCopy = { ...this.event }
          console.log(this.selectedCopy)
        } catch (e) {
          console.error(e)
        }
      }
    }
  },
  watch: {
    selectedEvent (newSelectedEvent) {
      if (newSelectedEvent) {
        this.selectedCopy = Event.copyConstructor(newSelectedEvent)
      }
    },
    async '$route' (to, from) {
      const eventId = to.params.id
      // Check if the scooterId is present and different from the current scooter
      if (eventId !== undefined) {
        try {
          this.event = await this.eventsService.asyncFindById(eventId)
          this.selectedCopy = { ...this.event } // Create a copy of the scooter
        } catch (e) {
          console.error(e)
        }
      }

      // Check if the scooterId is not present (deselected)
      if (!eventId) {
        this.event = null
        this.selectedCopy = null
      }
    }
  },
  async created () {
    try {
      await this.loadEvent()
    } catch (e) {
      console.error(e)
    }
  },
  computed: {
    formattedDate: {
      get () {
        if (this.selectedCopy.date) {
          const eventDate = new Date(this.selectedCopy.date)
          const formattedDate = `${eventDate.getFullYear()}-${(eventDate.getMonth() + 1)
            .toString()
            .padStart(2, '0')}-${eventDate.getDate().toString().padStart(2, '0')}`
          return formattedDate
        }
        return ''
      },
      set (newFormattedDate) {
        if (newFormattedDate) {
          const [year, month, day] = newFormattedDate.split('-')
          this.selectedCopy.date = new Date(year, month - 1, day)
        }
      }
    }
  }
}
</script>

<template>
  <div class="container" v-if="selectedCopy">
    <div class="breadcrum-admin breadcrum-admin-margin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <p>></p>
      <router-link :to="{ name: 'adminEvents' }">Events</router-link>
      <p>></p>
      <router-link :to="{ name: 'adminEventDetail', params: { id: selectedCopy.id } }">Event / {{ selectedCopy.id }}</router-link>
    </div>
    <div class="events-title">
      <button @click="closeEventDetail" class="back-button">Back</button>
      <h3>Event</h3>
    </div>
    <form class="event-details">
      <div class="event-image-container">
        <input type="file" accept="image/jpeg, image/png, image/jpg" id="file" @change="handleImageUpload">
        <img :src="require(`../../../assets/img/${selectedCopy.image}`)" alt="event image" class="event-image" @click="activateInput">
      </div>
      <div class="info-inputs">
        <input type="text" placeholder="Event name" v-model="selectedCopy.name" id="edit-event-name">
        <input type="date" v-model="formattedDate" id="edit-event-date">
        <div class="time-container">
          <input type="time" v-model="selectedCopy.timeBegin" id="edit-event-time-begin">
          <input type="time" v-model="selectedCopy.timeEnd" id="edit-event-time-end">
        </div>
        <input type="text" placeholder="Event location" v-model="selectedCopy.location" id="edit-event-location">
        <input type="text" placeholder="Event city" v-model="selectedCopy.city" id="edit-event-city">
        <input type="number" v-model="selectedCopy.price" id="edit-event-price" placeholder="Event price">
        <textarea placeholder="Enter event summary" v-model="selectedCopy.info" id="edit-event-summary"></textarea>
      </div>
    </form>
    <div class="buttons-container">
      <button @click="clearInputs">Clear</button>
      <button @click="saveEventDetail">Save</button>
      <button @click="deleteEventDetail" v-if="!created">Delete</button>
    </div>
  </div>
</template>

<style scoped>
h1, h2, h3, h4 {
  //color: #000000;
}

.events-title h3{
  margin: 0;
}

.container {
  margin-top: 0;
  margin-right: 0;
  width: 100%;
}

button {
  background: var(--main-color-hover);
  color: var(--main-text-color);
  border-style: none;
  padding: 1rem 1.5rem;
  font-size: 18px;
  box-shadow: rgba(0, 0, 0, 0.19) 0px 10px 20px, rgba(0, 0, 0, 0.23) 0px 6px 6px;
  cursor: pointer;
  transition: 0.5s;
}
</style>
