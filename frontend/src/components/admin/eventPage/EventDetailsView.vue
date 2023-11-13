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
        this.$emit('deselect-event', this.selectedEvent)
      }
    },
    async saveEventDetail () {
      if (this.isAllInputsFilled) {
        if (confirm('Are you sure you want to save changes to event?')) {
          this.$emit('save-event', this.selectedCopy)
        }
      }
    },
    async deleteEventDetail () {
      if (confirm('Are you sure you want to delete this event?')) {
        await this.eventsService.asyncDeleteById(this.selectedEvent.id)
        this.$emit('delete-event', this.selectedEvent)
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
    }
  },
  watch: {
    selectedEvent (newSelectedEvent) {
      if (newSelectedEvent) {
        this.selectedCopy = Event.copyConstructor(newSelectedEvent)
      }
    }
  },
  created () {
    this.selectedCopy = Event.copyConstructor(this.selectedEvent)
  },
  computed: {
    isSaveButtonDisabled () {
      // Compare the selectedCopy data with the selectedEvent data
      return (
        this.selectedCopy.image === this.selectedEvent.image &&
        this.selectedCopy.name === this.selectedEvent.name &&
        this.selectedCopy.date === this.selectedEvent.date &&
        this.selectedCopy.timeBegin === this.selectedEvent.timeBegin &&
        this.selectedCopy.timeEnd === this.selectedEvent.timeEnd &&
        this.selectedCopy.location === this.selectedEvent.location &&
        this.selectedCopy.price === this.selectedEvent.price &&
        this.selectedCopy.info === this.selectedEvent.info
      )
    },
    isAllInputsFilled () {
      // Check if all input fields are filled (except the textarea)
      return (
        this.selectedCopy.name &&
        this.selectedCopy.date &&
        this.selectedCopy.timeBegin &&
        this.selectedCopy.timeEnd &&
        this.selectedCopy.location &&
        this.selectedCopy.city &&
        this.selectedCopy.price !== null
      )
    }
  }
}
</script>

<template>
  <div class="container">
    <div class="events-title">
      <button @click="closeEventDetail" class="back-button">Back</button>
      <h3>Event</h3>
    </div>
    <div class="event-details">
      <div class="event-image-container">
        <input type="file" accept="image/jpeg, image/png, image/jpg" id="file" @change="handleImageUpload">
        <img :src="require(`../../../assets/images/${selectedCopy.image}`)" alt="event image" class="event-image" @click="activateInput">
      </div>
      <div class="info-inputs">
        <input type="text" placeholder="Event name" v-model="selectedCopy.name" id="edit-event-name">
        <input type="date" v-model="selectedCopy.date" id="edit-event-date">
        <div class="time-container">
          <input type="time" v-model="selectedCopy.timeBegin" id="edit-event-time-begin">
          <input type="time" v-model="selectedCopy.timeEnd" id="edit-event-time-end">
        </div>
        <input type="text" placeholder="Event location" v-model="selectedCopy.location" id="edit-event-location">
        <input type="text" placeholder="Event city" v-model="selectedCopy.city" id="edit-event-city">
        <input type="number" v-model="selectedCopy.price" id="edit-event-price" placeholder="Event price">
        <textarea placeholder="Enter event summary" v-model="selectedCopy.info" id="edit-event-summary"></textarea>
      </div>
    </div>
    <div class="buttons-container">
      <button @click="clearInputs">Clear</button>
      <button @click="saveEventDetail" :disabled="isSaveButtonDisabled">Save</button>
      <button @click="deleteEventDetail" v-if="!created">Delete</button>
    </div>
  </div>
</template>

<style scoped>
h1, h2, h3, h4 {
  color: #000000;
}

.events-title h3{
  margin: 0;
}

.container {
  margin-top: 0;
  margin-right: 0;
  width: 100%;
}
</style>
