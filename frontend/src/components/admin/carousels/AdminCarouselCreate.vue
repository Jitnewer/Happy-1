<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminCarousels' }">Carousel Slides</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminCarouselCreate' }">Create</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
        <h1>Create Carousel Slide</h1>
        <button @click="back()">Back</button>
      </div>
      <div>
        <form @submit.prevent="create" class="challenge-create-form" v-if="carousel">
          <div class="form-label">
            <p>Title</p>
            <input type="text" v-model="carousel.title" :class="{'invalid-input': carousel.title && !isTitleValid,'valid-input': carousel.title && isTitleValid}" @input="validateTitle(carousel.title)">
            <p class="errorMessage" v-if="!isTitleValid && carousel.title">Invalid title, no special symbols allowed</p>
            <p class="charCounter">{{ charCount }} / 82 characters</p>
          </div>
          <div class="form-label">
            <p>DateTime</p>
            <input type="datetime-local" v-model="carousel.dateTime" :class="{'invalid-input': carousel.dateTime && !isDateTimeValid,'valid-input': carousel.dateTime && isDateTimeValid}" @input="validateDateTime(carousel.dateTime)">
            <p class="errorMessage" v-if="!isDateTimeValid && carousel.dateTime">Invalid datetime, needs te be now or in the future</p>
          </div>
          <div class="form-label">
            <p>Image</p>
            <input type="file"  @change="handleFileChange" :class="{'invalid-input': image && !isImageValid,'valid-input': image && isImageValid}" @input="validateImage(image)">
            <p class="errorMessage" v-if="!isImageValid && image">{{ imageError }}</p>
          </div>
          <button type="submit" :disabled="!carouselEdited && !validateForm()">Save</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminCarouselCreate.vue',
  inject: ['carouselService', 'carouselServiceSuperUser', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      carousel: {
        title: '',
        dateTime: '',
        image: null
      },
      image: null,
      carouselEdited: false,
      isTitleValid: null,
      isDateTimeValid: null,
      isSaved: false,
      imageError: null,
      isImageValid: null
    }
  },
  methods: {
    validateTitle (title) {
      const titleRegex = /^[a-zA-Z0-9\s\-!\\?.$€&,:'"ëéèêàáâûüúöóôçīńł]+$/u

      this.isTitleValid = titleRegex.test(title)
      this.charCount = title.length

      return this.isTitleValid && this.charCount <= 82
    },
    validateDateTime (dateTime) {
      const inputDate = new Date(dateTime)
      // Get the current date
      const currentDate = new Date()

      // Check if the date is today or in the future
      this.isDateTimeValid = inputDate >= currentDate

      return this.isDateTimeValid
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
        this.carousel.dateTime = new Date(this.carousel.dateTime).toISOString()
        try {
          const response = await this.carouselServiceSuperUser.asyncSave(this.carousel)
          console.log('Eerste response:', response)
          const carousel = response.carousel
          console.log('Carousel:', carousel)
          console.log('Image voor fileupload:', this.image)
          console.log('Carousel ID voor fileupload:', carousel.id)
          const file = await this.fileUploadService.asyncUploadCarouselPic(this.image, carousel.id)
          console.log('File upload response:', file)
          carousel.image = file.filePath
          await this.carouselServiceSuperUser.asyncSave(carousel)
          this.isSaved = true
          this.$router.push({ name: 'adminCarousels' })
        } catch (e) {
          console.error('Fout bij het maken van de carousel:', e)
        }
      }
    },
    back () {
      this.$router.push({ name: 'adminCarousels' })
    },
    handleFileChange (carousel) {
      const file = carousel.target.files[0]

      if (file) {
        // Validate the image file
        this.isImageValid = this.validateImage(file)

        if (this.isImageValid) {
          // Update the challenge.image property when a valid file is selected
          this.image = file
        } else {
          this.image = file
          // Reset the input field and show an error message
          carousel.target.value = null
          this.imageError = 'Invalid image file. Please select a valid image (up to 5MB).'
        }
      }
    },
    validateForm () {
      const isTitleValid = this.validateTitle(this.carousel.title)
      const isDateTimeValid = this.validateDateTime(this.carousel.dateTime)
      const isImageValid = this.isImageValid
      // Form is valid if all individual validations pass
      return isTitleValid && isDateTimeValid && isImageValid
    },
    getFormattedDate (dateString) {
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('nl-NL', options)
    }
  },
  computed: {
    carousels () {
      return this.carouselService.entities
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
