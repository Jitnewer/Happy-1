<template>
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <a>News</a>
    <p>></p>
    <router-link :to="{ name: 'researches' }">Researches</router-link>
  </div>
  <div class="container">
    <h1 id="challenges-title">Research</h1>
    <div class="challenges">
      <div class="challenge" v-for="research in sortedResearch" :key="research.id" @click="selectResearch(research)">
        <div class="challenge-left">
          <img :src="require(`../../assets/img/${research.image}`)" alt="Challenge Image"/>
        </div>
        <div class="challenge-right">
          <p class="challenge-time">{{ formattedDateTime(research.dateTime) }}</p>
          <h3>{{  research.title }}</h3>
          <p>{{ shortenParagraph(research.firstParagraph) }}</p>
        </div>
      </div>
    </div>
  </div>
  <router-view @update-selected-challenge="updateSelectedResearch"></router-view>
</template>

<script>
export default {
  name: 'Researches.vue',
  inject: ['researchService'],
  emits: ['loginAdmin', 'loginUser'],
  data () {
    return {
      researches: [],
      selectedResearch: null
    }
  },
  methods: {
    selectResearch (research) {
      this.$router.push({ name: 'research', params: { id: research.id } })
      this.selectedResearch = research
    },
    shortenParagraph (paragraph) {
      const maxChars = 150 // Pas dit aan naar de gewenste lengte
      return paragraph.length > maxChars ? paragraph.slice(0, maxChars) + '...' : paragraph
    },
    getFormattedDate (dateString) {
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('nl-NL', options)
    },
    formattedDateTime (dateTime) {
      const today = new Date()
      const researchDate = new Date(dateTime)

      const formattedTime = researchDate.toLocaleTimeString('nl-NL', { hour: '2-digit', minute: '2-digit' })

      if (
        researchDate.getDate() === today.getDate() &&
        researchDate.getMonth() === today.getMonth() &&
        researchDate.getFullYear() === today.getFullYear()
      ) {
        // Vandaag, (tijd)
        return `Vandaag, ${formattedTime}`
      } else if (
        researchDate.getDate() === today.getDate() - 1 &&
        researchDate.getMonth() === today.getMonth() &&
        researchDate.getFullYear() === today.getFullYear()
      ) {
        // Gisteren, (tijd)
        return `Gisteren, ${formattedTime}`
      } else {
        // Maandag, (tijd), Donderdag (tijd)
        return `${this.getFormattedDate(dateTime)}, ${formattedTime}`
      }
    },
    updateSelectedResearch () {
      this.selectedResearch = null
    }
  },
  async created () {
    try {
      this.researches = await this.researchService.asyncFindAll()
    } catch (e) {
      console.error(e)
    }
  },
  computed: {
    sortedResearch () {
      return this.researches.slice().sort((a, b) => {
        const dateA = new Date(a.dateTime)
        const dateB = new Date(b.dateTime)
        return dateA - dateB
      })
    }
  }
}

</script>
