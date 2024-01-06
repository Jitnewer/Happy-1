<template>
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <a>News</a>
    <p>></p>
    <router-link :to="{ name: 'networks' }">Networks</router-link>
  </div>
  <div class="container">
    <div class="title-filter">
      <h1 id="challenges-title">Networks</h1>
      <button class="filter-button" @click="toggleFilter">Filter</button>
      <transition name="filter">
        <div v-if="showFilter" class="challenge-filter">
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
      <div class="challenge" v-for="network in sortedNetworks" :key="network.id" @click="selectNetwork(network)">
        <div class="challenge-left">
          <img :src="network.image ? require(`../../assets/img/${network.image}`) : ''" alt="Challenge Image">
        </div>
        <div class="challenge-right">
          <p class="challenge-time">{{ formattedDateTime(network.dateTime) }}</p>
          <h3>{{  network.title }}</h3>
          <p>{{ shortenParagraph(network.firstParagraph) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Networks.vue',
  inject: ['networkService'],
  data () {
    return {
      filter: null,
      showFilter: false
    }
  },
  methods: {
    async selectNetwork (network) {
      this.$router.push({ name: 'network', params: { id: network.id } })
      await this.networkService.asyncFindById(network.id)
    },
    toggleFilter () {
      this.showFilter = !this.showFilter
    },
    updateFilter (filterValue) {
      if (this.filter === filterValue) {
        // If the same filter is clicked again, deselect it
        this.filter = null
        this.$router.push({ name: 'networks' })
      } else {
        // Otherwise, set the selected filter
        this.filter = filterValue
        this.$router.push({ name: 'networks', query: { sort: this.filter } })
      }
    },
    async updateNetworks () {
      try {
        if (this.filter == null) {
          await this.networkService.asyncFindAll()
        } else {
          this.$router.push({ name: 'challenges', query: { sort: this.filter } })
          await this.networkService.asyncFindByProperty(this.filter, 'getByTheme')
        }
      } catch (e) {
        console.error(e)
      }
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
      const networkDate = new Date(dateTime)

      const formattedTime = networkDate.toLocaleTimeString('nl-NL', { hour: '2-digit', minute: '2-digit' })

      if (
        networkDate.getDate() === today.getDate() &&
        networkDate.getMonth() === today.getMonth() &&
        networkDate.getFullYear() === today.getFullYear()
      ) {
        // Vandaag, (tijd)
        return `Vandaag, ${formattedTime}`
      } else if (
        networkDate.getDate() === today.getDate() - 1 &&
        networkDate.getMonth() === today.getMonth() &&
        networkDate.getFullYear() === today.getFullYear()
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
    if (this.$route.query) {
      this.filter = this.$route.query.sort
    }
    await this.updateNetworks()
  },
  watch: {
    $route (to, from) {
      if (to.fullPath !== from.fullPath) {
        this.updateNetworks()
      }
    }
  },
  computed: {
    networks () {
      return this.networkService.entities
    },
    sortedNetworks () {
      return this.networks.slice().sort((a, b) => {
        const dateA = new Date(a.dateTime)
        const dateB = new Date(b.dateTime)
        return dateA - dateB
      })
    }
  }
}
</script>
