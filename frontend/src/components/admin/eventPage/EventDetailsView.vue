<script>
import { Event } from '@/models/event'
export default {
  name: 'EventDetailsView',
  inject: ['eventsServiceSuperUser', 'eventsService', 'fileUploadService'],
  data () {
    return {
      create: false,
      selectedCopy: null,
      pictureUpload: null,
      newEventPic: null,
      event: null
    }
  },
  methods: {
    back () {
      this.$router.push({ name: 'adminEvents' })
    },
    closeEventDetail () {
      if (!Event.equals(this.event, this.selectedCopy)) {
        if (confirm('Are you sure you want to discard your changes?')) {
          this.back()
        }
      } else {
        this.back()
      }
    },
    async saveEventDetail () {
      if (!Event.equals(this.event, this.selectedCopy) || this.newEventPic) {
        if (this.validateFields) {
          if (confirm('Do you want to save the changes to this event?')) {
            if (this.pictureUpload) {
              const profilePicPath = await this.fileUploadService.asyncUploadEventPic(this.pictureUpload, this.selectedCopy.id)
              this.selectedCopy.image = profilePicPath.filePath
            }

            await this.eventsServiceAdmin.asyncSave(this.selectedCopy)
            this.back()
          }
        }
      }
    },
    async deleteEventDetail () {
      if (confirm('Are you sure you want to delete this event?')) {
        await this.eventsServiceAdmin.asyncDeleteById(this.selectedCopy.id)
        await this.fileUploadService.asyncDeleteImage(this.selectedCopy.image)

        this.back()
      }
    },
    handleImageUpload (event) {
      const file = event.target.files[0]
      this.newEventPic = URL.createObjectURL(file)
      this.pictureUpload = file
    },
    activateInput () {
      document.querySelector('#file').click()
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
    setUpErrorMessage (elementId, message) {
      document.querySelector(elementId).setCustomValidity(message)
      document.querySelector(elementId).reportValidity()
    },
    async loadEvent () {
      const eventId = parseInt(this.$route.params.id)
      if (eventId === 0) {
        this.create = true
        this.event = new Event()
        this.event.id = 0
        this.event.image = 'assets/eventPic/imagePlaceholder.jpg'
        this.selectedCopy = Event.copyConstructor(this.event)
        return
      }

      this.event = await this.eventsService.asyncFindById(eventId)

      if (eventId <= 0 || this.event === null) {
        this.$router.push({ path: '/EventNotFound' })
      } else {
        this.selectedCopy = { ...this.event }
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
    validateFields () {
      if (!this.eventNameIsValid) {
        this.setUpErrorMessage('#edit-event-name', 'Name must be more than 3 characters')
        return false
      }
      if (!this.eventDateIsValid) {
        this.setUpErrorMessage('#edit-event-date', 'Event date must be further in the future')
        return false
      }
      if (!this.eventBeginTimeIsValid) {
        this.setUpErrorMessage('#edit-event-time-begin', 'This field is required')
        return false
      }
      if (!this.eventEndTimeIsValid) {
        this.setUpErrorMessage('#edit-event-time-end', 'This field is required')
        return false
      }
      if (!this.eventLocationIsValid) {
        this.setUpErrorMessage('#edit-event-location', 'Location must be more than 3 characters and can\'t contain numbers or symbols')
        return false
      }
      if (!this.eventCityIsValid) {
        this.setUpErrorMessage('#edit-event-city', 'City must be more than 3 characters and can\'t contain numbers or symbols')
        return false
      }
      if (!this.eventPriceIsValid) {
        this.setUpErrorMessage('#edit-event-price', 'Price must be 0 - 500')
        return false
      }

      return true
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
  <div class="container admin-event-details" v-if="event">
    <div class="events-title">
      <button @click="closeEventDetail" class="back-button">Back</button>
      <h3>Event</h3>
    </div>
    <form class="event-details">
      <div class="event-image-container">
        <input type="file" accept="image/jpeg, image/png, image/jpg" id="file" @change="handleImageUpload">
        <img v-if="!newEventPic" :src="require(`../../../${selectedCopy.image}`)" alt="event image" class="event-image" @click="activateInput">
        <img v-else :src="newEventPic" alt="event image" class="event-image" @click="activateInput">
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
      <button @click="clearInputs" class="admin-clear">Clear</button>
      <button @click="saveEventDetail" class="admin-save">Save</button>
      <button @click="deleteEventDetail" class="admin-delete" v-if="!create">Delete</button>
    </div>
  </div>
</template>

<style scoped>
.events-title h3{
  margin: 0;
}
</style>
