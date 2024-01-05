<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'welcome' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminChallenges' }">Challenges</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminChallengeCreate' }">Create</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
        <h1>Create Challenge</h1>
        <button @click="back()">Back</button>
      </div>
      <div>
        <form @submit.prevent="create" class="challenge-create-form" v-if="challenge">
          <div class="form-label">
            <p>Title</p>
            <input type="text" v-model="challenge.title" :class="{'invalid-input': challenge.title && !isTitleValid,'valid-input': challenge.title && isTitleValid}" @input="validateTitle(challenge.title)">
            <p class="errorMessage" v-if="!isTitleValid && challenge.title">Invalid title, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>First Paragraph</p>
            <input type="text" v-model="challenge.firstParagraph" :class="{'invalid-input': challenge.firstParagraph && !isFirstParagraphValid,'valid-input': challenge.firstParagraph && isFirstParagraphValid}" @input="validateFirstParagraph(challenge.firstParagraph)">
            <p class="errorMessage" v-if="!isFirstParagraphValid && challenge.firstParagraph">Invalid first paragraph, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>DateTime</p>
            <input type="datetime-local" v-model="challenge.dateTime" :class="{'invalid-input': challenge.dateTime && !isDateTimeValid,'valid-input': challenge.dateTime && isDateTimeValid}" @input="validateDateTime(challenge.dateTime)">
            <p class="errorMessage" v-if="!isDateTimeValid && challenge.dateTime">Invalid datetime, needs te be now or in the future</p>
          </div>
          <div class="form-label">
            <p>Theme</p>
            <select v-model="challenge.theme">
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
            <div class="paragraph" v-for="(paragraph, index) in challenge.paragraphs" :key="index">
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
          <button type="submit" :disabled="!challengeEdited && !validateForm()">Save</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChallengeAdminCreate.vue',
  inject: ['challengeService', 'challengeServiceAdmin', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      showParagraphs: false,
      selectedChallenge: null,
      challenge: {
        title: '',
        firstParagraph: '',
        dateTime: '',
        theme: '',
        image: null,
        paragraphs: []
      },
      image: null,
      paragraphsAmount: 0,
      challengeEdited: false,
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
        console.log(isImage)

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
    async create () {
      if (this.validateForm()) {
        this.challenge.dateTime = new Date(this.challenge.dateTime).toISOString()
        try {
          const challenge = await this.challengeServiceAdmin.asyncSave(this.challenge)
          const file = await this.fileUploadService.asyncUploadChallengePic(this.image, challenge.id)
          challenge.image = file.filePath
          await this.challengeServiceAdmin.asyncSave(challenge)
          this.isSaved = true
          this.$router.push({ name: 'adminChallenges' })
        } catch (e) {
          console.error(e)
        }
      }
    },
    paragraphs (challenge) {
      this.showParagraphs = !this.showParagraphs
      this.selectedChallenge = challenge
    },
    back () {
      this.$router.push({ name: 'adminChallenges' })
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
      this.challenge.paragraphs.push({ title: '', content: '' })
    },
    decrementParagraphs () {
      // Ensure the number of paragraphs doesn't go below 0
      if (this.paragraphsAmount > 0) {
        this.paragraphsAmount--
        // Remove the last paragraph when decrementing
        this.challenge.paragraphs.pop()
      }
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
      // Validate title
      const isTitleValid = this.validateTitle(this.challenge.title)

      // Validate first paragraph
      const isFirstParagraphValid = this.validateFirstParagraph(this.challenge.firstParagraph)

      // Validate date and time
      const isDateTimeValid = this.validateDateTime(this.challenge.dateTime)

      const isImageValid = this.isImageValid

      // Validate paragraphs
      let areParagraphsValid = true
      for (let i = 0; i < this.challenge.paragraphs.length; i++) {
        const paragraph = this.challenge.paragraphs[i]
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
  computed: {
    challenges () {
      return this.challengeService.entities
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
