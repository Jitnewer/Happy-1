<template>
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'news', query: { sort: filter }  }">News</router-link>
    <p>></p>
    <router-link :to="{ name: 'challenges' , query: { sort: filter }  }">Challenges</router-link>
  </div>
  <div class="container">
    <div class="title-filter">
    <h1 id="challenges-title">Challenge Articles</h1>
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
      <div class="challenge" v-for="challenge in sortedChallenges" :key="challenge.id" @click="selectChallenge(challenge)">
        <div class="challenge-left">
          <img :src="challenge.image ? require(`../../assets/img/${challenge.image}`) : ''" alt="Challenge Image">
        </div>
        <div class="challenge-right">
          <p class="challenge-time">{{ formattedDateTime(challenge.dateTime) }}</p>
          <h3>{{  challenge.title }}</h3>
          <p>{{ shortenParagraph(challenge.firstParagraph) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Challenges.vue',
  inject: ['challengeService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false
    }
  },
  methods: {
    async selectChallenge (challenge) {
      this.$router.push({ name: 'challenge', params: { id: challenge.id }, query: { sort: this.filter } })
      await this.challengeService.asyncFindById(challenge.id)
    },
    toggleFilter () {
      this.showFilter = !this.showFilter
    },
    updateFilter (filterValue) {
      if (this.filter === filterValue) {
        // If the same filter is clicked again, deselect it
        this.filter = null
        this.$router.push({ name: 'challenges' })
      } else {
        // Otherwise, set the selected filter
        this.filter = filterValue
        this.$router.push({ name: 'challenges', query: { sort: this.filter } })
      }
    },
    async updateChallenges () {
      try {
        if (this.filter == null) {
          await this.challengeService.asyncFindAll()
        } else {
          await this.challengeService.asyncFindByProperty(this.filter, 'getByTheme')
          this.$router.push({ name: 'challenges', query: { sort: this.filter } })
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
    if (this.$route.query) {
      this.filter = this.$route.query.sort
    }
    await this.updateChallenges()
  },
  watch: {
    $route (to, from) {
      if (to.fullPath !== from.fullPath) {
        this.updateChallenges()
      }
    }
  },
  computed: {
    challenges () {
      return this.challengeService.entities
    },
    sortedChallenges () {
      if (Array.isArray(this.challenges)) {
        return this.challenges.slice().sort((a, b) => {
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
