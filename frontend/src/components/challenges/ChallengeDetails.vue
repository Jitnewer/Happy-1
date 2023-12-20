<template>
  <div v-if="challenge" class="container">
    <div class="challenge-main">
      <div class="challenge-title">
        <button @click="back">Back</button>
        <h1>Challenge</h1>
      </div>
      <div class="detail-challenge">
        <div>
          <img :src="require(`../../${challenge.image}`)" alt="">
        </div>
        <div class="content">
          <div>
            <p class="challenge-time">{{ formattedDateTime(challenge.dateTime) }}</p>
            <h2>{{ challenge.title }}</h2>
            <p>{{ challenge.firstParagraph}} </p>
          </div>
          <div v-for="paragraph in challenge.paragraphs" :key="paragraph.id">
            <h4>{{ paragraph.title }}</h4>
            <p>{{ paragraph.content }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import challenges from './Challenges.vue'

export default {
  name: 'ChallengeDetails.vue',
  inject: ['challengeService'],
  data () {
    return {
      challenge: null
    }
  },
  methods: {
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
    back () {
      this.$router.push({ name: 'challenges' })
      this.$emit('update-selected-challenge')
    }
  },
  async created () {
    try {
      this.challenge = await this.challengeService.asyncFindById(this.$route.params.id)
    } catch (e) {
      console.error(e)
    }
  },
  computed: {
    challenges () {
      return challenges
    },
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
