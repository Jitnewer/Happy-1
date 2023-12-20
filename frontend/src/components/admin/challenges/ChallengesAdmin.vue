<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'welcome' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminChallenges' }">Challenges</router-link>
    </div>
    <div class="challenges-admin">
      <div class="title-button">
      <h1>Challenges</h1>
      <button>Create</button>
      </div>
      <table>
        <thead>
        <tr>
          <th>Id</th>
          <th>Title</th>
          <th>DateTime</th>
          <th>Theme</th>
          <th>Image</th>
          <th>Paragraphs</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="challenge in challenges" :key="challenge.id">
          <td>{{ challenge.id }}</td>
          <td>{{ challenge.title }}</td>
          <td>{{ formattedDateTime(challenge.dateTime) }}</td>
          <td>{{ challenge.theme }}</td>
          <td><img :src="challenge.image ? require(`../../../assets/img/${challenge.image}`) : ''" alt="Challenge Image"></td>
          <td><button class="relation" @click="paragraphs(challenge)">Check Paragraphs</button></td>
          <td>
            <div class="table-buttons">
     <button class="edit">Edit</button>
         <button class="delete">Delete</button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      <div class="paragraphs paragraphs-admin" v-if="showParagraphs">
        <div><button @click="back">Back</button>
          <table>
            <thead>
            <tr>
              <th>Id</th>
              <th>Title</th>
              <th>Content</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="paragraph in sortedParagraphs" :key="paragraph.id">
              <td>{{ paragraph.id }}</td>
              <td>{{ paragraph.title }}</td>
              <td>{{ paragraph.content }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChallengesAdmin.vue',
  inject: ['challengeService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      showParagraphs: false,
      selectedChallenge: null
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
    paragraphs (challenge) {
      this.showParagraphs = !this.showParagraphs
      this.selectedChallenge = challenge
    },
    back () {
      this.showParagraphs = false
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
        await this.challengeService.asyncFindAll()
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
    await this.updateChallenges()
    console.log(this.challenges)
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
