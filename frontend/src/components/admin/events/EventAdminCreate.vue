<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminEvents' }">Events</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminEventCreate' }">Create</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
        <h1>Create Event</h1>
        <button @click="back()">Back</button>
      </div>
      <div>
        <form @submit.prevent="create" class="challenge-create-form" v-if="event">
          <div class="form-label">
            <p>Name</p>
            <input type="text" v-model="event.name" :class="{'invalid-input': event.name && !isNameValid,'valid-input': event.name && isNameValid}" @input="validateName(event.name)">
            <p class="errorMessage" v-if="!isNameValid && event.name">Invalid name, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>City</p>
            <input type="text" v-model="event.city" :class="{'invalid-input': event.city && !isCityValid,'valid-input': event.city && isCityValid}" @input="validateCity(event.city)">
            <p class="errorMessage" v-if="!isCityValid && event.city">Invalid city, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>Location</p>
            <input type="text" v-model="event.location" :class="{'invalid-input': event.location && !isLocationValid,'valid-input': event.location && isLocationValid}" @input="validateLocation(event.location)">
            <p class="errorMessage" v-if="!isLocationValid && event.location">Invalid location, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>Price</p>
            <input type="text" v-model="event.price" :class="{'invalid-input': event.price && !isPriceValid,'valid-input': event.price && isPriceValid}" @input="validatePrice(event.price)">
            <p class="errorMessage" v-if="!isPriceValid && event.price">Invalid price, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>Info</p>
            <input type="text" v-model="event.info" :class="{'invalid-input': event.info && !isInfoValid,'valid-input': event.info && isInfoValid}" @input="validateInfo(event.info)">
            <p class="errorMessage" v-if="!isInfoValid && event.info">Invalid info, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>Date</p>
            <input type="date" v-model="event.date" :class="{'invalid-input': event.date && !isDateValid,'valid-input': event.date && isDateValid}" @input="validateDate(event.date)">
            <p class="errorMessage" v-if="!isDateValid && event.date">Invalid date, needs te be at least 5 days ahead of today</p>
          </div>
          <div class="form-label">
            <p>Time Begin</p>
            <input type="time" v-model="event.timeBegin" :class="{'invalid-input': event.timeBegin && !isTimeBeginValid,'valid-input': event.timeBegin && isTimeBeginValid}" @input="validateTimeBegin(event.timeBegin)">
            <p class="errorMessage" v-if="!isTimeBeginValid && event.timeBegin">Invalid time begin, is not allowed to be empty or time end needs to be after time begin</p>
          </div>
          <div class="form-label">
            <p>Time End</p>
            <input type="time" v-model="event.timeEnd" :class="{'invalid-input': event.timeEnd && !isTimeEndValid,'valid-input': event.timeEnd && isTimeEndValid}" @input="validateTimeEnd(event.timeEnd)">
            <p class="errorMessage" v-if="!isTimeEndValid && event.timeEnd">Invalid time end, is not allowed to be empty or time end needs to be after time begin</p>
          </div>
          <div class="form-label">
            <p>Size</p>
            <input type="number" v-model="event.size" :class="{'invalid-input': event.size && !isSizeValid,'valid-input': event.size && isSizeValid}" @input="validateSize(event.size)">
            <p class="errorMessage" v-if="!isSizeValid && event.size">Invalid size, maximum is 500</p>
          </div>
          <div class="form-label">
            <p>Image</p>
            <input type="file"  @change="handleFileChange" :class="{'invalid-input': image && !isImageValid,'valid-input': image && isImageValid}" @input="validateImage(image)">
            <p class="errorMessage" v-if="!isImageValid && image">{{ imageError }}</p>
          </div>

          <button type="submit" :disabled="!eventEdited && !validateForm()">Save</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import CustomError from '@/CustomError'

export default {
  name: 'EventAdminCreate.vue',
  inject: ['eventsService', 'eventsServiceSuperUser', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      event: {
        name: '',
        city: '',
        location: '',
        price: '',
        info: '',
        date: '',
        timeBegin: '',
        timeEnd: '',
        size: '',
        image: null
      },
      image: null,
      eventEdited: false,
      isNameValid: null,
      isCityValid: null,
      isLocationValid: null,
      isPriceValid: null,
      isDateValid: null,
      isTimeBeginValid: null,
      isTimeEndValid: null,
      isSizeValid: null,
      isInfoValid: null,
      isSaved: false,
      imageError: null,
      isImageValid: null
    }
  },
  methods: {
    validateName (name) {
      const nameRegex = /^[a-zA-Z0-9\s\-!\\?.$€&,:'"ëéèêàáâûüúöóôçīńł]+$/u

      this.isNameValid = nameRegex.test(name)
      return this.isNameValid
    },
    validateCity (city) {
      const cityRegex = /^[a-zA-Z0-9\s\-!\\?.$€&,:'"ëéèêàáâûüúöóôçīńł]+$/u

      this.isCityValid = cityRegex.test(city)
      return this.isCityValid
    },
    validateLocation (location) {
      const locationRegex = /^[a-zA-Z0-9\s\-!\\?.$€&,:'"ëéèêàáâûüúöóôçīńł]+$/u

      this.isLocationValid = locationRegex.test(location)
      return this.isLocationValid
    },
    validatePrice (price) {
      const priceRegex = /^[0-9]+([,.][0-9]{1,2})?$/

      this.isPriceValid = priceRegex.test(price)
      return this.isPriceValid
    },
    validateInfo (info) {
      const infoRegex = /^[a-zA-Z0-9\s\-!\\?.$€&,:'"ëéèêàáâûüúöóôçīńł]+$/u

      this.isInfoValid = infoRegex.test(info)
      return this.isInfoValid
    },
    validateDate (date) {
      const inputDate = new Date(date)
      // Get the current date
      const currentDate = new Date()

      // Set the current date to 7 days in the future
      currentDate.setDate(currentDate.getDate() + 4)

      // Check if the input date is at least 7 days after the current date
      this.isDateValid = inputDate >= currentDate

      return this.isDateValid
    },
    validateTimeBegin (timeBegin) {
      // Use regex to check if the time is not empty
      const timeRegex = /^(?=.*[0-9])[\d:]*[APMapm]*$/
      this.isTimeBeginValid = timeRegex.test(timeBegin)

      return this.isTimeBeginValid
    },
    validateSize (size) {
      const maxSize = 500
      this.isSizeValid = /^\d+$/.test(size) && parseInt(size, 10) <= maxSize

      return this.isSizeValid
    },
    validateTimeEnd (timeEnd) {
      // Use regex to check if the time is not empty
      const timeRegex = /^(?=.*[0-9])[\d:]*[APMapm]*$/
      this.isTimeEndValid = timeRegex.test(timeEnd)

      if (this.isTimeEndValid) {
        // If the time is not empty, compare timeEnd with timeBegin
        const timeBegin = new Date(`2000-01-01 ${this.event.timeBegin}`)
        const endTime = new Date(`2000-01-01 ${timeEnd}`)

        this.isTimeEndValid = endTime >= timeBegin
      } else {
        this.isTimeEndValid = false // Set to false if time is not valid
      }

      return this.isTimeEndValid
    },
    validateImage (file) {
      if (file !== null) {
        // Check if the file is an image
        const isImage = /^image\//.test(file.type)
        console.log(isImage)

        // Check if the file size is within the allowed limit (in bytes)
        const maxSize = 5 * 1024 * 1024 // 5MB
        const isSizeValid = file.size <= maxSize

        this.isImageValid = isImage && isSizeValid
      }
      return this.isImageValid
    },
    async create () {
      if (this.validateForm()) {
        try {
          const response = await this.eventsServiceSuperUser.asyncSave(this.event)
          const event = response.event
          const file = await this.fileUploadService.asyncUploadEventPic(this.image, event.id)
          event.image = file.filePath
          const response2 = await this.eventsServiceSuperUser.asyncSave(event)
          this.isSaved = true
          this.$store.commit('setSuccess', true)
          this.$store.commit('setSuccessMessage', response2.message)
          setTimeout(() => {
            this.$store.commit('setSuccess', false)
            this.$store.commit('setSuccessMessage', null)
          }, 8000)
          this.$router.push({ name: 'adminEvents' })
        } catch (e) {
          if (e instanceof CustomError) {
            console.error(e.toJSON())
            this.$store.commit('setError', true)
            this.$store.commit('setErrorMessage', e.toJSON().error)
            setTimeout(() => {
              this.$store.commit('setError', false)
              this.$store.commit('setErrorMessage', null)
            }, 8000)
          } else {
            this.$store.commit('setError', true)
            this.$store.commit('setErrorMessage', 'Error adding the network')
            setTimeout(() => {
              this.$store.commit('setError', false)
              this.$store.commit('setErrorMessage', null)
            }, 8000)
          }
        }
      }
    },
    back () {
      this.$router.push({ name: 'adminChallenges' })
    },
    handleFileChange (event) {
      const file = event.target.files[0]

      if (file) {
        // Validate the image file
        this.isImageValid = this.validateImage(file)

        if (this.isImageValid) {
          // Update the challenge.image property when a valid file is selected
          this.image = file
        } else {
          this.image = file
          // Reset the input field and show an error message
          event.target.value = null
          this.imageError = 'Invalid image file. Please select a valid image (up to 5MB).'
        }
      }
    },
    validateForm () {
      const isNameValid = this.validateName(this.event.name)
      const isCityValid = this.validateCity(this.event.city)
      const isLocationValid = this.validateLocation(this.event.location)
      const isPriceValid = this.validatePrice(this.event.price)
      const isInfoValid = this.validateInfo(this.event.info)
      const isDateValid = this.validateDate(this.event.date)
      const isTimeBeginValid = this.validateTimeBegin(this.event.timeBegin)
      const isTimeEndValid = this.validateTimeEnd(this.event.timeEnd)
      const isSizeValid = this.validateSize(this.event.size)
      const isImageValid = this.isImageValid

      return isNameValid && isCityValid && isLocationValid && isPriceValid && isInfoValid && isDateValid && isTimeBeginValid && isTimeEndValid && isSizeValid && isImageValid
    },
    getFormattedDate (dateString) {
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('nl-NL', options)
    }
  },
  computed: {
    events () {
      return this.eventsService.entities
    }
  },
  beforeRouteUpdate (to, from, next) {
    if (!this.isSaved) {
      this.conformationAlert(() => {
        // Continue with the route update
        next()
      }, 'Are you sure you want to leave with unsaved changes?')
    } else {
      next()
    }
  }
}

</script>
