<script>

export default {
  name: 'SuperUserView',
  inject: ['challengeService'],
  data () {
    return {
      challenges: [],
      filter: {
        date: null,
        search: ''
      }
    }
  },
  methods: {
    selectChallenge (challenge) {
      this.$router.push({ name: 'SuperUserChallengeDetail', params: { id: challenge.id } })
    },
    createChallenge () {
      this.$router.push({ name: 'SuperUserChallengeDetail', params: { id: 0 } })
    },
    shortenParagraph (paragraph) {
      const maxChars = 150
      return paragraph.length > maxChars ? paragraph.slice(0, maxChars) + '...' : paragraph
    },
    formattedDateTime (dateTime) {
      const localDateTime = new Date(dateTime)

      const options = {
        day: '2-digit',
        month: 'short',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      }

      return localDateTime.toLocaleDateString('nl-NL', options)
    }
  },
  async created () {
    this.challenges = await this.challengeService.asyncFindAll()
  }
}

</script>

<template>
  <div class="container">
    <h1 id="challenges-title">Challenges</h1>
    <div class="filters">
      <input type="text" class="search-filter" id="nameFilter" placeholder="Search for events.." title="Type in a name"
             v-model="filter.search">
      <input class="date-filter" type="date" id="dateFilter" v-model="filter.date">
      <button class="create-btn" @click="createChallenge">Create</button>
    </div>
    <div class="challenges">
      <div class="challenge" v-for="challenge in challenges" :key="challenge" @click="selectChallenge(challenge)">
        <div class="challenge-left">
          <img :src="require(`../../../../${challenge.image}`)" alt="Challenge Image"/>
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

<style scoped>
.container {
  width: 100%;
  padding: 20px;
}

.challenges {
  margin-top: 30px;
}

#challenges-title {
  margin-top: 0;
}

.challenges, #challenges-title {
  margin-left: 20px;
}
</style>
