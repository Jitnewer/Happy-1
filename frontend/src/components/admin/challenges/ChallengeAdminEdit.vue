<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'welcome' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminChallenges' }">Challenges</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminChallengeEdit', query: { id: $route.params.id } }">Edit / {{ challenge.id}}</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
      <h1>Create Challenge</h1>
      <button @click="back()">Back</button>
      </div>
      <div>
        <form @submit.prevent="edit" class="challenge-create-form">
          <div class="form-label">
            <p>Title</p>
            <input type="text" v-model="challenge.title">
          </div>
          <div class="form-label">
            <p>First Paragraph</p>
            <input type="text" v-model="challenge.firstParagraph">
          </div>
          <div class="form-label">
            <p>DateTime</p>
            <input type="datetime-local" v-model="formattedDateTimeInput">
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
            <input type="file"  @change="handleFileChange">
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
                  <input type="text" v-model="paragraph.title">
                </div>
                <div class="form-label">
                  <p>Content</p>
                  <input type="text" v-model="paragraph.content">
                </div>
              </div>
            </div>
          <button type="submit">Save</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChallengeAdminEdit.vue',
  inject: ['challengeService', 'challengeServiceAdmin', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      showParagraphs: false,
      selectedChallenge: null,
      image: null,
      paragraphsAmount: 0
    }
  },
  methods: {
    async edit () {
      this.challenge.dateTime = new Date(this.challenge.dateTime).toISOString()
      try {
        if (this.image !== null) {
          const file = await this.fileUploadService.asyncUploadChallengePic(this.image, this.challenge.id)
          this.challenge.image = file.filePath
          await this.challengeServiceAdmin.asyncSave(this.challenge)
        } else {
          await this.challengeServiceAdmin.asyncSave(this.challenge)
        }
        this.$router.push({ name: 'adminChallenges' })
      } catch (e) {
        console.error(e)
      }
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
      // Update the challenge.image property when a file is selected
      this.image = event.target.files[0]
    },
    async updateChallenges () {
      try {
        await this.challengeService.asyncFindById(this.$route.params.id)
        this.paragraphsAmount = this.challenge.paragraphs.length
        console.log(this.paragraphsAmount)
      } catch (e) {
        console.error(e)
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
    await this.updateChallenges()
    console.log(this.challenge)
  },
  watch: {
    $route (to, from) {
      if (to.fullPath !== from.fullPath) {
        this.updateChallenges()
      }
    }
  },
  computed: {
    challenge () {
      return this.challengeService.entities
    },
    formattedDateTimeInput: {
      get () {
        // Format challenge.dateTime for datetime-local input
        return this.formatDateTimeWithoutSeconds(this.challenge.dateTime)
      },
      set (value) {
        // Parse the input value back to ISO format
        this.challenge.dateTime = new Date(value).toISOString()
      }
    },
    sortedParagraphs () {
      if (this.selectedChallenge) {
        // Sort paragraphs based on id
        return this.selectedChallenge.paragraphs.slice().sort((a, b) => a.id - b.id)
      }
      return []
    }
  }
}

</script>
