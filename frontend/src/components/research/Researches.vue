<template>
  <!-- Breadcrumb navigation and filter dropdown -->
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'news', query: { sort: filter } }">News</router-link>
    <p>></p>
    <router-link :to="{ name: 'researches', query: { sort: filter } }">Research Articles</router-link>
  </div>

  <!-- Main content container -->
  <div class="container">
    <div class="title-filter">
      <!-- Title and filter button -->
      <h1 id="challenges-title">Research Articles</h1>
      <button class="filter-button" @click="toggleFilter">Filter</button>

      <!-- Filter dropdown with transition -->
      <transition name="filter">
        <div v-if="showFilter" class="research-filter">
          <div class="filter-up">
            <!-- Filter options (customize as needed) -->
            <div class="filter-child">
              <p>Food Waste</p>
              <input class="food-waste" name="sort" type="radio" value="FOOD_WASTE" v-model="filter" @click="updateFilter('FOOD_WASTE')">
            </div>
            <!-- Add other filter options similarly -->
          </div>
          <div class="filter-down">
            <!-- Add more filter options here -->
          </div>
        </div>
      </transition>
    </div>

    <!-- List of research articles -->
    <div class="challenges">
      <div class="challenge" v-for="research in sortedResearch" :key="research.id" @click="selectResearch(research)">
        <div class="challenge-left">
          <!-- Research image -->
          <img :src="research.image ? require(`../../${research.image}`) : ''" alt="Research Image">
        </div>
        <div class="challenge-right">
          <!-- Research details -->
          <p class="challenge-time">{{ formattedDateTime(research.dateTime) }}</p>
          <h3>{{ research.title }}</h3>
          <p>{{ shortenParagraph(research.firstParagraph) }}</p>
        </div>
      </div>
    </div>
  </div>

  <!-- Router view for nested components -->
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
    /**
     * Navigate to the detailed view of a selected research article.
     * @param {Object} research - The selected research article.
     */
    async selectResearch (research) {
      this.$router.push({ name: 'research', params: { id: research.id }, query: { sort: this.filter } })
      await this.researchService.asyncFindById(research.id)
    },
    /**
     * Shorten a paragraph to a specified maximum number of characters.
     * @param {string} paragraph - The paragraph to be shortened.
     * @returns {string} - The shortened paragraph.
     */
    shortenParagraph (paragraph) {
      const maxChars = 150 // Pas dit aan naar de gewenste lengte
      return paragraph.length > maxChars ? paragraph.slice(0, maxChars) + '...' : paragraph
    },

    /**
     * Toggle the visibility of the filter dropdown.
     */
    toggleFilter () {
      this.showFilter = !this.showFilter
    },

    /**
     * Update the filter based on the selected value.
     * @param {string} filterValue - The selected filter value.
     */
    updateFilter (filterValue) {
      console.log(filterValue)
      console.log(this.filter)
      if (this.filter === filterValue) {
        // If the same filter is clicked again, deselect it
        this.filter = null
        this.$router.push({ name: 'researches' })
      } else {
        // Otherwise, set the selected filter
        this.filter = filterValue
        this.$router.push({ name: 'researches', query: { sort: this.filter } })
      }
    },

    /**
     * Get the formatted date from a date string.
     * @param {string} dateString - The input date string.
     * @returns {string} - The formatted date string.
     */
    getFormattedDate (dateString) {
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('nl-NL', options)
    },

    /**
     * Format the date and time for display.
     * @param {string} dateTime - The input date and time string.
     * @returns {string} - The formatted date and time string.
     */
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

    /**
     * Update the list of research articles.
     */
    async updateResearches () {
      try {
        if (this.filter == null) {
          await this.researchService.asyncFindAll()
        } else {
          this.$router.push({ name: 'researches', query: { sort: this.filter } })
          await this.researchService.asyncFindByProperty(this.filter, 'getByTheme')
        }
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
