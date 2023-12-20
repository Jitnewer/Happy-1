<template>
  <div class="container">
    <h1 id="challenges-title">Challenges</h1>
    <div class="challenges">
      <div class="challenge" v-for="challenge in sortedChallenges" :key="challenge.id" @click="selectChallenge(challenge)">
        <div class="challenge-left">
          <img :src="require(`../../${challenge.image}`)" alt="Challenge Image"/>
        </div>
        <div class="challenge-right">
          <p class="challenge-time">{{ formattedDateTime(challenge.dateTime) }}</p>
          <h3>{{  challenge.title }}</h3>
          <p>{{ shortenParagraph(challenge.firstParagraph) }}</p>
        </div>
      </div>
    </div>
  </div>
  <router-view @update-selected-challenge="updateSelectedChallenge"></router-view>
</template>

<script>
export default {
  name: 'Challenges.vue',
  inject: ['challengeService'],
  data () {
    return {
      challenges: [],
      selectedChallenge: null
    }
  },
  methods: {
    selectChallenge (challenge) {
      this.$router.push({ name: 'challenge', params: { id: challenge.id } })
      this.selectedChallenge = challenge
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
    },
    updateSelectedChallenge () {
      this.selectedChallenge = null
    }
  },
  async created () {
    try {
      this.challenges = await this.challengeService.asyncFindAll()
    } catch (e) {
      console.error(e)
    }
  },
  computed: {
    sortedChallenges () {
      return this.challenges.slice().sort((a, b) => {
        const dateA = new Date(a.dateTime)
        const dateB = new Date(b.dateTime)
        return dateA - dateB
      })
    }
  }
}

</script>
