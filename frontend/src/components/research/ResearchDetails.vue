<template>
  <div class="breadcrum" v-if="research">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'news', query: { sort: filter }  }">News</router-link>
    <p>></p>
    <router-link :to="{ name: 'researches' , query: { sort: filter }   }">Research Articles</router-link>
    <p>></p>
    <router-link :to="{ name: 'research', params: { id: research.id } , query: { sort: filter }  }">Research Article / {{ research.id }}</router-link>
  </div>
  <div v-if="research" class="container">
    <div class="challenge-main">
      <div class="challenge-title">
        <button @click="back">Back</button>
        <h1>Research Article</h1>
      </div>
      <div class="detail-challenge">
        <div>
          <img class="research-image" :src="`https://ik.imagekit.io/happy1hva${research.image}`" alt="">
        </div>
        <div class="content">
          <div>
            <p class="challenge-time">{{ formattedDateTime(research.dateTime) }}</p>
            <h2 class="research-title">{{ research.title }}</h2>
            <p class="research-firstp">{{ research.firstParagraph}} </p>
          </div>
          <div v-for="paragraph in research.paragraphs" :key="paragraph.id">
            <h4 class="research-ptitle">{{ paragraph.title }}</h4>
            <p class="research-p">{{ paragraph.content }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import research from './Researches.vue'

export default {
  name: 'ResearchDetails.vue',
  inject: ['researchService'],
  data () {
    return {
      research: null,
      filter: this.$route.query.sort
    }
  },
  methods: {
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
    async back () {
      if (this.filter === null) {
        this.$router.push({ name: 'researches' })
      } else {
        this.$router.push({ name: 'researches', query: { sort: this.filter } })
      }
    }
  },
  async created () {
    try {
      this.research = await this.researchService.asyncFindById(this.$route.params.id)
    } catch (e) {
      console.error(e)
      this.$store.commit('setError', true)
      this.$store.commit('setErrorMessage', e.error)
      setTimeout(() => {
        this.$store.commit('setError', false)
        this.$store.commit('setErrorMessage', null)
      }, 8000)
    }
  },
  computed: {
    researches () {
      return this.researchService.entities
    },
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
