<template>
  <div class="container-admin" v-if="copiedCarousel">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminCarousels' }">Events</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminCarouselEdit', query: { id: $route.params.id } }">Edit {{ copiedCarousel.id }}</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
        <h1>Edit Carousel</h1>
        <button @click="back()" class="back-button">Back</button>
      </div>
      <div>
        <form @submit.prevent="edit" class="challenge-create-form" v-if="copiedCarousel">
          <div class="form-label">
            <p>Title</p>
            <input type="text" v-model="copiedCarousel.title" :class="{'invalid-input': copiedCarousel.title && !validateTitle,'valid-input': copiedCarousel.title && validateTitle}" @input="validateTitle(copiedCarousel.title)">
            <p class="errorMessage" v-if="!validateTitle && carousel.name">Invalid name, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>Date</p>
            <input type="datetime-local" v-model="formattedDateTimeInput" :class="{'invalid-input': formattedDateTimeInput && !isDateValid,'valid-input': formattedDateTimeInput && isDateValid}" @input="validateDate(formattedDateTimeInput)">
            <p class="errorMessage" v-if="!isDateValid && formattedDateTimeInput">Invalid date, needs to be in the future</p>
          </div>
          <div class="form-label">
            <p>Image</p>
            <input type="file"  @change="handleFileChange" :class="{'invalid-input': image && !isImageValid,'valid-input': image && isImageValid}" @input="validateImage(image)">
            <p class="errorMessage" v-if="!isImageValid && image">{{ imageError }}</p>
          </div>

          <button type="submit" :disabled="!carouselEdited && !validateForm()" class="admin-create">Save</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminCarouselEdit.vue',
  inject: ['carouselService', 'carouselServiceSuperUser', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      copiedCarousel: null,
      image: null,
      carouselEdited: false,
      isTitleValid: null,
      isDateValid: null,
      isSaved: false,
      imageError: null,
      isImageValid: null
    }
  },
  methods: {
    validateTitle (title) {
      const nameRegex = /^[a-zA-Z0-9\s\-!\\?.$€&,:'"ëéèêàáâûüúöóôçīńł]+$/u

      this.isTitleValid = nameRegex.test(title)
      return this.isTitleValid
    },
    validateDate (date) {
      const inputDate = new Date(date)

      // Format the input date to "yyyy-MM-dd"
      const formattedDate = inputDate.toISOString().split('T')[0]

      // Get the current date
      const currentDate = new Date()

      // Check if the formatted date is today or in the future
      this.isDateValid = formattedDate >= currentDate.toISOString().split('T')[0]

      return this.isDateValid
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
    async edit () {
      if (this.validateForm()) {
        try {
          if (this.image !== null) {
            const file = await this.fileUploadService.asyncUploadCarouselPic(this.image, this.copiedCarousel.id)
            this.copiedCarousel.image = file.filePath
            await this.carouselServiceSuperUser.asyncSave(this.copiedCarousel)
            this.isSaved = true
          } else {
            await this.carouselServiceSuperUser.asyncSave(this.copiedCarousel)
            this.isSaved = true
          }
          this.$router.push({ name: 'adminCarousels' })
        } catch (e) {
          console.error(e)
        }
      }
    },
    checkIfCarouselEdited () {
      if (this.carousel && this.copiedCarousel) {
        const isTitleEdited = this.carousel.title !== this.copiedCarousel.title
        const isDateEdited = this.carousel.date !== this.copiedCarousel.date
        const isImageEdited = this.carousel.image !== this.copiedCarousel.image

        this.carouselEdited =
          isTitleEdited ||
          isDateEdited ||
          isImageEdited
      }
    },
    back () {
      this.$router.push({ name: 'adminCarousels' })
    },
    handleFileChange (event) {
      const file = event.target.files[0]

      if (file) {
        // Validate the image file
        this.isImageValid = this.validateImage(file)

        if (this.isImageValid) {
          // Update the challenge.image property when a valid file is selected
          this.carouselEdited = true
          this.image = file
        } else {
          this.image = file
          // Reset the input field and show an error message
          event.target.value = null
          this.imageError = 'Invalid image file. Please select a valid image (up to 5MB).'
        }
      }
    },
    async updateCarousel () {
      try {
        await this.carouselServiceSuperUser.asyncFindById(this.$route.params.id)
        this.copiedCarousel = JSON.parse(JSON.stringify(this.carousel))
      } catch (e) {
        console.error(e)
      }
    },
    validateForm () {
      const isTitleValid = this.validateTitle(this.carousel.name)
      const isDateValid = this.validateDate(this.copiedCarousel.date)
      let isImageValid
      if (this.isImageValid === null) {
        isImageValid = true
      } else {
        isImageValid = this.isImageValid
      }

      return isTitleValid && isDateValid && isImageValid
    },
    formatDateTimeWithoutSeconds (dateTime) {
      const date = new Date(dateTime)
      const year = date.getFullYear()
      const month = `0${date.getMonth() + 1}`.slice(-2)
      const day = `0${date.getDate()}`.slice(-2)
      const hours = `0${date.getHours()}`.slice(-2)
      const minutes = `0${date.getMinutes()}`.slice(-2)

      return `${year}-${month}-${day}T${hours}:${minutes}`
    }
  },
  computed: {
    carousel () {
      return this.carouselServiceSuperUser.entities
    },
    formattedDateTimeInput: {
      get () {
        // Format copiedEvent.date for datetime-local input
        const date = new Date(this.copiedCarousel.date)
        const year = date.getFullYear()
        const month = `0${date.getMonth() + 1}`.slice(-2)
        const day = `0${date.getDate()}`.slice(-2)
        const hours = `0${date.getHours()}`.slice(-2)
        const minutes = `0${date.getMinutes()}`.slice(-2)

        return `${year}-${month}-${day}T${hours}:${minutes}`
      },
      set (value) {
        // Parse the input value back to ISO format
        this.copiedCarousel.date = new Date(value).toISOString()
      }
    }
  },
  async created () {
    await this.updateCarousel()
  },

  watch: {
    $route (to, from) {
      if (to.fullPath !== from.fullPath) {
        this.updateCarousel()
      }
    },
    copiedCarousel: {
      handler () {
        this.checkIfCarouselEdited()
      },
      deep: true
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
