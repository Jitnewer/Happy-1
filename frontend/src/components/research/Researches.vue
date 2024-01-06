<template>
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'news', query: { sort: filter} }">News</router-link>
    <p>></p>
    <router-link :to="{ name: 'carousels', query: { sort: filter} }">Research Articles</router-link>
  </div>
  <div class="container">
    <div class="title-filter">
      <h1 id="challenges-title">Research Articles</h1>
      <button class="filter-button" @click="toggleFilter">Filter</button>
      <transition name="filter">
        <div v-if="showFilter" class="research-filter">
          <div class="filter-up">
            <div class="filter-child">
              <p>Food Waste</p>
              <input class="food-waste" name="sort" type="radio" value="FOOD_WASTE" v-model="filter" @click="updateFilter('FOOD_WASTE')">
            </div>
            <div class="filter-child">
              <p>Distribution</p>
              <input class="distribution"  name="sort" type="radio" value="DISTRIBUTION" v-model="filter" @click="updateFilter('DISTRIBUTION')">
            </div>
            <div class="filter-child">
              <p>Energy Transition</p>
              <input class="energy-transition"  name="sort" type="radio" value="ENERGY_TRANSITION" v-model="filter" @click="updateFilter('ENERGY_TRANSITION')">
            </div>
          </div>
          <div class="filter-down">
            <div class="filter-child">
              <p>Single Used Plastic</p>
              <input class="single-used-plastic"  name="sort" type="radio" value="SINGLE_USED_PLASTIC" v-model="filter" @click="updateFilter('SINGLE_USED_PLASTIC')">
            </div>
            <div class="filter-child">
              <p>Protein Transition</p>
              <input class="protein-transition"  name="sort" type="radio" value="PROTEIN_TRANSITION" v-model="filter" @click="updateFilter('PROTEIN_TRANSITION')">
            </div>
            <div class="filter-child">
              <p>Water</p>
              <input class="water"  name="sort" type="radio" value="WATER" v-model="filter" @click="updateFilter('WATER')">
            </div>
          </div>
        </div>
      </transition>
    </div>
    <div class="challenges">
      <div class="challenge" v-for="research in sortedResearch" :key="research.id" @click="selectResearch(research)">
        <div class="challenge-left">
          <img :src="research.image ? require(`../../${research.image}`) : ''" alt="Research Image">
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
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false
    }
  },
  methods: {
    async selectResearch (research) {
      this.$router.push({ name: 'research', params: { id: research.id }, query: { sort: this.filter } })
      await this.researchService.asyncFindById(research.id)
    },
    shortenParagraph (paragraph) {
      const maxChars = 150 // Pas dit aan naar de gewenste lengte
      return paragraph.length > maxChars ? paragraph.slice(0, maxChars) + '...' : paragraph
    },
    toggleFilter () {
      this.showFilter = !this.showFilter
    },
    updateFilter (filterValue) {
      console.log(filterValue)
      console.log(this.filter)
      if (this.filter === filterValue) {
        // If the same filter is clicked again, deselect it
        this.filter = null
        this.$router.push({ name: 'carousels' })
      } else {
        // Otherwise, set the selected filter
        this.filter = filterValue
        this.$router.push({ name: 'carousels', query: { sort: this.filter } })
      }
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
    async updateResearches () {
      try {
        if (this.filter == null) {
          await this.researchService.asyncFindAll()
        } else {
          this.$router.push({ name: 'carousels', query: { sort: this.filter } })
          await this.researchService.asyncFindByProperty(this.filter, 'getByTheme')
        }
      } catch (e) {
        console.error(e)
      }
    },
    updateSelectedResearch () {
      this.selectedResearch = null
    }
  },
  async created () {
    if (this.$route.query) {
      this.filter = this.$route.query.sort
    }
    await this.updateResearches()
  },
  watch: {
    $route (to, from) {
      if (to.fullPath !== from.fullPath) {
        this.updateResearches()
      }
    }
  },
  computed: {
    researches () {
      return this.researchService.entities
    },
    sortedResearch () {
      if (Array.isArray(this.researches)) {
        console.log(this.researches)
        return this.researches.slice().sort((a, b) => {
          const dateA = new Date(a.dateTime)
          const dateB = new Date(b.dateTime)
          return dateA - dateB
        })
      }
      return null
    }
  }
}

</script>
