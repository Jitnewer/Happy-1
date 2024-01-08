<template>
  <div class="container-admin" v-if="copiedResearch">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminResearches' }">Research Articles</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminResearchEdit', query: { id: $route.params.id } }">Edit / {{ copiedResearch.id}}</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
        <h1>Edit Research</h1>
        <button @click="back()">Back</button>
      </div>
      <div>
        <form @submit.prevent="edit" class="challenge-create-form" v-if="copiedResearch">
          <div class="form-label">
            <p>Title</p>
            <input type="text" v-model="copiedResearch.title" :class="{'invalid-input': copiedResearch.title && !isTitleValid,'valid-input': copiedResearch.title && isTitleValid}" @input="validateTitle(copiedResearch.title)">
            <p class="errorMessage" v-if="!isTitleValid && copiedResearch.title">Invalid title, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>First Paragraph</p>
            <input type="text" v-model="copiedResearch.firstParagraph" :class="{'invalid-input': copiedResearch.firstParagraph && !isFirstParagraphValid,'valid-input': copiedResearch.firstParagraph && isFirstParagraphValid}" @input="validateFirstParagraph(copiedResearch.firstParagraph)">
            <p class="errorMessage" v-if="!isFirstParagraphValid && copiedResearch.firstParagraph">Invalid first paragraph, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>DateTime</p>
            <input type="datetime-local" v-model="formattedDateTimeInput" :class="{'invalid-input': formattedDateTimeInput && !isDateTimeValid,'valid-input': formattedDateTimeInput && isDateTimeValid}" @input="validateDateTime(formattedDateTimeInput)">
            <p class="errorMessage" v-if="!isDateTimeValid && formattedDateTimeInput">Invalid datetime, needs te be now or in the future</p>
          </div>
          <div class="form-label">
            <p>Theme</p>
            <select v-model="copiedResearch.theme">
              <option value="FOOD_WASTE">FOOD_WASTE</option>
              <option value="DISTRIBUTION">DISTRIBUTION</option>
              <option value="ENERGY_TRANSITION">ENERGY_TRANSITION</option>
              <option value="SINGLE_USED_PLASTIC">SINGLE_USED_PLASTIC</option>
              <option value="PROTEIN_TRANSITION">PROTEIN_TRANSITION</option>
              <option value="WATER">WATER</option>
            </select>
          </div>
          <div class="form-label">
            <p>Image</p>
            <input type="file"  @change="handleFileChange" :class="{'invalid-input': image && !isImageValid,'valid-input': image && isImageValid}" @input="validateImage(image)">
            <p class="errorMessage" v-if="!isImageValid && image">{{ imageError }}</p>
          </div>
          <div class="paragraphs-amount">
            <p>Paragraphs</p>
            <input readonly type="text" v-model="paragraphsAmount">
            <div @click="decrementParagraphs"><p>-</p></div>
            <div @click="incrementParagraphs"><p>+</p></div>
          </div>
          <div class="paragraphs-inputs" v-if="paragraphsAmount !== 0">
            <div class="paragraph" v-for="(paragraph, index) in copiedResearch.paragraphs" :key="index">
              <p>Paragraph {{ index + 1 }}</p>
              <div class="form-label">
                <p>Title</p>
                <input type="text" v-model="paragraph.title" :class="{'invalid-input': paragraph.title && !isParagraphTitleValid[index],'valid-input': paragraph.title && isParagraphTitleValid[index]}" @input="validateParagraphTitle(paragraph.title, index)">
                <p class="errorMessage" v-if="!isParagraphTitleValid[index] && paragraph.title">Invalid paragraph title, no special symbols allowed</p>
              </div>
              <div class="form-label">
                <p>Content</p>
                <input type="text" v-model="paragraph.content" :class="{'invalid-input': paragraph.content && !isParagraphContentValid[index],'valid-input': paragraph.content && isParagraphContentValid[index]}" @input="validateParagraphContent(paragraph.content, index)">
                <p class="errorMessage" v-if="!isParagraphContentValid[index] && paragraph.content">Invalid paragraph content, no special symbols allowed</p>
              </div>
            </div>
          </div>
          <button type="submit" :disabled="!researchEdited && !validateForm()">Save</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>

import CustomError from '@/CustomError'

export default {
  name: 'ResearchAdminEdit.vue',
  inject: ['researchService', 'researchServiceSuperUser', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      showParagraphs: false,
      selectedResearch: null,
      image: null,
      paragraphsAmount: 0,
      copiedResearch: null,
      researchEdited: false,
      isTitleValid: null,
      isFirstParagraphValid: null,
      isDateTimeValid: null,
      isParagraphTitleValid: [],
      isParagraphContentValid: [],
      isSaved: false,
      imageError: null,
      isImageValid: null
    }
  },
  methods: {
    validateTitle (title) {
      const titleRegex = /^[a-zA-Z0-9\s\-!\\?.$€&,:'"ëéèêàáâûüúöóôçīńł]+$/u

      this.isTitleValid = titleRegex.test(title)
      return this.isTitleValid
    },
    validateFirstParagraph (firstParagraph) {
      const firstParagraphRegex = /^[a-zA-Z0-9\s\-!\\?.$€&,:'"ëéèêàáâûüúöóôçīńł]+$/u

      this.isFirstParagraphValid = firstParagraphRegex.test(firstParagraph)
      return this.isFirstParagraphValid
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

        // Check if the file size is within the allowed limit (in bytes)
        const maxSize = 5 * 1024 * 1024 // 5MB
        const isSizeValid = file.size <= maxSize

        this.isImageValid = isImage && isSizeValid
      }
      return this.isImageValid
    },
    validateParagraphTitle (paragraphTitle, index) {
      const paragraphTitleRegex = /^[a-zA-Z0-9\s\-!\\?.$€&,:'"ëéèêàáâûüúöóôçīńł]+$/u

      this.isParagraphTitleValid[index] = paragraphTitleRegex.test(paragraphTitle)
      return this.isParagraphTitleValid[index]
    },
    validateParagraphContent (paragraphContent, index) {
      const paragraphContentRegex = /^[a-zA-Z0-9\s\-!\\?.$€&,:'"ëéèêàáâûüúöóôçīńł]+$/u

      this.isParagraphContentValid[index] = paragraphContentRegex.test(paragraphContent)
      return this.isParagraphContentValid[index]
    },
    async edit () {
      // Create a deep comparison function
      if (this.researchEdited) {
        this.copiedResearch.dateTime = new Date(this.copiedResearch.dateTime).toISOString()
        try {
          if (this.validateForm()) {
            if (this.image !== null) {
              const file = await this.fileUploadService.asyncUploadChallengePic(this.image, this.copiedResearch.id)
              this.challenge.image = file.filePath
              const response = await this.researchServiceSuperUser.asyncSave(this.copiedResearch)
              this.isSaved = true
              this.$store.commit('setSuccess', true)
              this.$store.commit('setSuccessMessage', response.message)
              setTimeout(() => {
                this.$store.commit('setSuccess', false)
                this.$store.commit('setSuccessMessage', null)
              }, 8000)
            }
          } else {
            const response = await this.researchServiceSuperUser.asyncSave(this.copiedResearch)
            this.isSaved = true
            this.$store.commit('setSuccess', true)
            this.$store.commit('setSuccessMessage', response.message)
            setTimeout(() => {
              this.$store.commit('setSuccess', false)
              this.$store.commit('setSuccessMessage', null)
            }, 8000)
          }
          this.$router.push({ name: 'adminResearches' })
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
      this.$router.push({ name: 'adminResearches' })
    },
    validateForm () {
      // Validate title
      const isTitleValid = this.validateTitle(this.copiedResearch.title)

      // Validate first paragraph
      const isFirstParagraphValid = this.validateFirstParagraph(this.copiedResearch.firstParagraph)

      // Validate date and time
      const isDateTimeValid = this.validateDateTime(this.formattedDateTimeInput)

      const isImageValid = this.isImageValid

      // Validate paragraphs
      let areParagraphsValid = true
      for (let i = 0; i < this.copiedResearch.paragraphs.length; i++) {
        const paragraph = this.copiedResearch.paragraphs[i]
        const isParagraphTitleValid = this.validateParagraphTitle(paragraph.title, i)
        const isParagraphContentValid = this.validateParagraphContent(paragraph.content, i)

        // If any paragraph is not valid, set areParagraphsValid to false
        if (!isParagraphTitleValid || !isParagraphContentValid) {
          areParagraphsValid = false
          break
        }
      }

      // Form is valid if all individual validations pass
      return isTitleValid && isFirstParagraphValid && isDateTimeValid && isImageValid && areParagraphsValid
    },
    formatDateTimeWithoutSeconds (dateTime) {
      const date = new Date(dateTime)
      const year = date.getFullYear()
      const month = `0${date.getMonth() + 1}`.slice(-2)
      const day = `0${date.getDate()}`.slice(-2)
      const hours = `0${date.getHours()}`.slice(-2)
      const minutes = `0${date.getMinutes()}`.slice(-2)

      return `${year}-${month}-${day}T${hours}:${minutes}`
    },
    incrementParagraphs () {
      // Increment the number of paragraphs
      this.paragraphsAmount++
      this.copiedResearch.paragraphs.push({ title: '', content: '' })
      // Initialize validity flags for the new paragraph
      this.isParagraphTitleValid.push(null)
      this.isParagraphContentValid.push(null)
    },
    checkIfResearchEdited () {
      if (this.research && this.copiedResearch) {
        const isTitleEdited = this.research.title !== this.copiedResearch.title
        const isFirstParagraphEdited = this.research.firstParagraph !== this.copiedResearch.firstParagraph
        const isDateTimeEdited = this.research.dateTime !== this.copiedResearch.dateTime
        const isThemeEdited = this.research.theme !== this.copiedResearch.theme
        const isImageEdited = this.research.image !== this.copiedResearch.image

        let areParagraphsEdited = false

        if (this.research.paragraphs.length !== this.copiedResearch.paragraphs.length) {
          areParagraphsEdited = true
        } else {
          for (let i = 0; i < this.research.paragraphs.length; i++) {
            const paragraph = this.research.paragraphs[i]
            const copiedParagraph = this.copiedResearch.paragraphs[i]

            if (paragraph.title !== copiedParagraph.title || paragraph.content !== copiedParagraph.content) {
              areParagraphsEdited = true
              break // Break out of the loop as soon as a difference is found
            }
          }
        }
        this.researchEdited =
          isTitleEdited ||
          isFirstParagraphEdited ||
          isDateTimeEdited ||
          isThemeEdited ||
          isImageEdited ||
          areParagraphsEdited
      }
    },
    decrementParagraphs () {
      // Ensure the number of paragraphs doesn't go below 0
      if (this.paragraphsAmount > 0) {
        this.paragraphsAmount--
        // Remove the last paragraph when decrementing
        this.copiedResearch.paragraphs.pop()
        // Remove validity flags for the removed paragraph
        this.isParagraphTitleValid.pop()
        this.isParagraphContentValid.pop()
      }
    },
    handleFileChange (event) {
      const file = event.target.files[0]

      if (file) {
        // Validate the image file
        this.isImageValid = this.validateImage(file)

        if (this.isImageValid) {
          this.networkEdited = true
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
    async updateResearches () {
      try {
        await this.researchService.asyncFindById(this.$route.params.id)
        this.copiedResearch = JSON.parse(JSON.stringify(this.research))
        this.paragraphsAmount = this.research.paragraphs.length
        // Initialize validity flags for each paragraph
        this.isParagraphTitleValid = new Array(this.paragraphsAmount).fill(null)
        this.isParagraphContentValid = new Array(this.paragraphsAmount).fill(null)
      } catch (e) {
        console.error(e.toJSON())
        this.$store.commit('setError', true)
        this.$store.commit('setErrorMessage', e.toJSON().error)
        setTimeout(() => {
          this.$store.commit('setError', false)
          this.$store.commit('setErrorMessage', null)
        }, 8000)
      }
    },
    conformationAlert (callback, message) {
      const result = confirm(message)
      if (result === true) {
        callback()
      }
    },
    getFormattedDate (dateString) {
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('nl-NL', options)
    },
    formattedDateTime (dateTime) {
      const today = new Date()
      const challengeDate = new Date(dateTime)
      const formattedTime = challengeDate.toLocaleTimeString('nl-NL', { hour: '2-digit', minute: '2-digit' })

      if (
        challengeDate.getDate() === today.getDate() &&
        challengeDate.getMonth() === today.getMonth() &&
        challengeDate.getFullYear() === today.getFullYear()
      ) {
        // Vandaag, (tijd)
        return `Vandaag, ${formattedTime}`
      } else if (
        challengeDate.getDate() === today.getDate() - 1 &&
        challengeDate.getMonth() === today.getMonth() &&
        challengeDate.getFullYear() === today.getFullYear()
      ) {
        // Gisteren, (tijd)
        return `Gisteren, ${formattedTime}`
      } else {
        // Maandag, (tijd), Donderdag (tijd)
        return `${this.getFormattedDate(dateTime)}, ${formattedTime}`
      }
    }
  },
  async created () {
    await this.updateResearches()
  },
  watch: {
    $route (to, from) {
      if (to.fullPath !== from.fullPath) {
        this.updateResearches()
      }
    },
    copiedResearch: {
      handler () {
        this.checkIfResearchEdited()
      },
      deep: true
    }
  },
  computed: {
    research () {
      return this.researchService.entities
    },
    formattedDateTimeInput: {
      get () {
        // Format challenge.dateTime for datetime-local input
        return this.formatDateTimeWithoutSeconds(this.copiedResearch.dateTime)
      },
      set (value) {
        // Parse the input value back to ISO format
        this.copiedResearch.dateTime = new Date(value).toISOString()
      }
    }
  },
  beforeRouteUpdate (to, from, next) {
    if (this.researchEdited && !this.isSaved) {
      this.conformationAlert(() => {
        // Continue with the route update
        next()
      }, 'Are you sure you want to leave with unsaved changes?')
    } else {
      next()
    }
  },

  beforeRouteLeave (to, from, next) {
    if (this.researchEdited && !this.isSaved) {
      this.conformationAlert(() => {
        // Continue with leaving the route
        next()
      }, 'Are you sure you want to leave with unsaved changes?')
    } else {
      next()
    }
  }
}

</script>
