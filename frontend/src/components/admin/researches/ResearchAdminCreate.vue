<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminResearches' }">Research Articles</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminResearchCreate' }">Create</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
        <h1>Create Research</h1>
        <button @click="back()" class="back-button">Back</button>
      </div>
      <div>
        <form @submit.prevent="create" class="challenge-create-form" v-if="research">
          <div class="form-label">
            <p>Title</p>
            <input type="text" v-model="research.title" :class="{'invalid-input': research.title && !isTitleValid,'valid-input': research.title && isTitleValid}" @input="validateTitle(research.title)">
            <p class="errorMessage" v-if="!isTitleValid && research.title">Invalid title, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>First Paragraph</p>
            <input type="text" v-model="research.firstParagraph" :class="{'invalid-input': research.firstParagraph && !isFirstParagraphValid,'valid-input': research.firstParagraph && isFirstParagraphValid}" @input="validateFirstParagraph(research.firstParagraph)">
            <p class="errorMessage" v-if="!isFirstParagraphValid && research.firstParagraph">Invalid first paragraph, no special symbols allowed</p>
          </div>
          <div class="form-label">
            <p>DateTime</p>
            <input type="datetime-local" v-model="research.dateTime" :class="{'invalid-input': research.dateTime && !isDateTimeValid,'valid-input': research.dateTime && isDateTimeValid}" @input="validateDateTime(research.dateTime)">
            <p class="errorMessage" v-if="!isDateTimeValid && research.dateTime">Invalid datetime, needs te be now or in the future</p>
          </div>
          <div class="form-label">
            <p>Theme</p>
            <select v-model="research.theme">
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
            <div class="paragraph" v-for="(paragraph, index) in research.paragraphs" :key="index">
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
export default {
  name: 'ResearchAdminCreate.vue',
  inject: ['researchService', 'researchServiceAdmin', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      showParagraphs: false,
      selectedChallenge: null,
      research: {
        title: '',
        firstParagraph: '',
        dateTime: '',
        theme: '',
        image: null,
        paragraphs: []
      },
      image: null,
      paragraphsAmount: 0,
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
        this.research.dateTime = new Date(this.research.dateTime).toISOString()
        try {
          const response = await this.researchServiceAdmin.asyncSave(this.research)
          console.log(response)

          const research = response.research
          const file = await this.fileUploadService.asyncUploadResearchPic(this.image, research.id)
          research.image = file.filePath
          await this.researchServiceAdmin.asyncSave(research)
          this.isSaved = true
          this.$router.push({ name: 'adminResearches' })
        } catch (e) {
          console.error(e)
        }
      }
    },
    paragraphs (research) {
      this.showParagraphs = !this.showParagraphs
      this.selectedResearch = research
    },
    back () {
      this.$router.push({ name: 'adminResearches' })
    },
    incrementParagraphs () {
      // Increment the number of paragraphs
      this.paragraphsAmount++
      this.research.paragraphs.push({ title: '', content: '' })
    },
    decrementParagraphs () {
      // Ensure the number of paragraphs doesn't go below 0
      if (this.paragraphsAmount > 0) {
        this.paragraphsAmount--
        // Remove the last paragraph when decrementing
        this.research.paragraphs.pop()
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
      const isTitleValid = this.validateTitle(this.research.title)

      // Validate first paragraph
      const isFirstParagraphValid = this.validateFirstParagraph(this.research.firstParagraph)

      // Validate date and time
      const isDateTimeValid = this.validateDateTime(this.research.dateTime)

      const isImageValid = this.isImageValid

      // Validate paragraphs
      let areParagraphsValid = true
      for (let i = 0; i < this.research.paragraphs.length; i++) {
        const paragraph = this.research.paragraphs[i]
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
    computed: {
      researches () {
        return this.researchService.entities
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
}

</script>
