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
      this.$emit('deselect-event', this.selectedEvent)
    },
    saveEventDetail () {
      this.$emit('save-event', this.selectedCopy)
    },
    deleteEventDetail () {
      this.$emit('delete-event', this.selectedEvent)
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
      // Check if any of the required fields are empty
      if (
        !this.selectedCopy.name ||
        !this.selectedCopy.location ||
        !this.selectedCopy.date ||
        !this.selectedCopy.timeBegin ||
        !this.selectedCopy.timeEnd ||
        !this.selectedCopy.price ||
        !this.selectedCopy.info
      ) {
        return true // Disable the "Save" button if any of the fields is empty
      }

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
.event-details-container {
  height: 100%;
  width: 100%;
}

.event-container {
  height: 600px;
  width: 650px;
  border-radius: 10px;
  border: 2px solid black;
  background-color: #ffffff;
  z-index: 99;
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  margin: auto;
  padding: 30px;
}

.black-filter {
  background-color: #000000;
  width: 100%;
  height: 100%;
  opacity: 0.5;
}

.event-info {
  display: flex;
  flex-direction: row;
}

.edit-image {
  height: 320px;
  width: 260px;
}

.edit-inputs {
  display: flex;
  flex-direction: column;
  width: 100%;
}

#file {
  display: none;
}

.edit-inputs input {
  padding: 14px 16px;
  width: 80%;
  font-size: 18px;
  border-radius: 5px;
  margin-left: 30px;
  margin-top: 10px;
}

.time-container {
  display: flex;
  flex-direction: row;
}

.time-container input {
  width: 32%;
}

#edit-event-summary {
  height: 240px;
  width: 97%;
  border-radius: 5px;
  margin-top: 10px;
  font-size: 18px;
}

.buttons {
  display: flex;
  flex-direction: row;
  gap: 10px;
  justify-content: right;
}

.edit-btn {
  padding: 12px 16px;
  font-size: 14px;
  background-color: #FECC32;
  border-radius: 5px;
  border: none;
  color: #ffffff;
}

.edit-btn:last-child {
  margin-right: 10px;
}

.edit-btn:hover {
  cursor: pointer;
  background-color: #E8B003;
}

</style>
