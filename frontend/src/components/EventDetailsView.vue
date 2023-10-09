<script>
import { Event } from '@/models/event'
export default {
  name: 'EventDetailsView',
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
    saveEventDetail () {
      if (confirm('Are you sure you want to save changes to event?')) {
        this.$emit('save-event', this.selectedCopy)
      }
    },
    deleteEventDetail () {
      if (confirm('Are you sure you want to delete this event?')) {
        this.$emit('delete-event', this.selectedEvent)
      }
    },
    handleImageUpload (event) {
      const file = event.target.files[0]
      this.selectedCopy.image = URL.createObjectURL(file)
      console.log(this.selectedCopy.image)
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
    }
  }
}
</script>

<template>
  <div class="event-details-container">
    <div class="black-filter"></div>
    <div class="event-container">
      <div class="event-info">
        <img :src="selectedCopy.image" alt="event-image" class="edit-image" @click="activateInput">
        <input type="file" accept="image/jpeg, image/png, image/jpg" id="file" @change="handleImageUpload">
        <div class="edit-inputs">
          <input type="text" placeholder="Event name" v-model="selectedCopy.name" id="edit-event-name">
          <input type="date" v-model="selectedCopy.date" id="edit-event-date">
          <div class="time-container">
            <input type="time" v-model="selectedCopy.timeBegin" id="edit-event-time-begin">
            <input type="time" v-model="selectedCopy.timeEnd" id="edit-event-time-end">
          </div>
          <input type="text" placeholder="Event location" v-model="selectedCopy.location" id="edit-event-location">
          <input type="number" v-model="selectedCopy.price" id="edit-event-price" placeholder="Event price">
        </div>
      </div>
      <div class="event-summary">
        <textarea placeholder="Enter event summary" v-model="selectedCopy.info" id="edit-event-summary"></textarea>
      </div>
      <div class="buttons">
        <button class="edit-btn" @click="deleteEventDetail" v-if="!created">Delete</button>
        <button class="edit-btn" @click="saveEventDetail" :disabled="isSaveButtonDisabled">Save</button>
        <button class="edit-btn" @click="closeEventDetail">Cancel</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
