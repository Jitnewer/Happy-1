<script>
export default {
  name: 'SuperUserView',
  inject: ['challengeService'],
  data () {
    return {
      challenges: [],
      selectedChallenge: null,
      filter: {
        date: null,
        search: ''
      },
      isSelected: false,
      create: true
    }
  },
  methods: {
    back () {
      this.isSelected = false
      this.$router.push({ name: 'SuperUserChallenges' })
    },
    selectChallenge (challenge) {
      this.isSelected = true
      this.$router.push({ name: 'SuperUserChallengeDetail', params: { id: challenge.id } })
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
  },
  watch: {
    '$route' (to, from) {
      const challengeId = parseInt(to.params.id)
      if (challengeId === 0) {
        return
      }
      if (challengeId) {
        this.isSelected = true
        this.selectedChallenge = this.challenges.find(challenge => challenge.id === challengeId)
      } else {
        this.isSelected = false
        this.selectedUser = null
      }
    }
  }
}

</script>

<template>
  <div class="container" v-if="!isSelected">
    <h1 id="challenges-title">Challenges</h1>
    <div class="filters">
      <input type="text" class="search-filter" id="nameFilter" placeholder="Search for events.." title="Type in a name"
             v-model="filter.search">
      <input class="date-filter" type="date" id="dateFilter" v-model="filter.date">
      <button class="create-btn">Create</button>
    </div>
    <div class="challenges">
      <div class="challenge" v-for="challenge in challenges" :key="challenge.id" @click="selectChallenge(challenge)">
        <div class="challenge-left">
          <img :src="require(`../../../../assets/images/${challenge.image}`)" alt="Challenge Image"/>
        </div>
        <div class="challenge-right">
          <p class="challenge-time">{{ formattedDateTime(challenge.dateTime) }}</p>
          <h3>{{  challenge.title }}</h3>
          <p>{{ shortenParagraph(challenge.firstParagraph) }}</p>
        </div>
      </div>
    </div>
  </div>
  <router-view class="news-details-view" :selectedChallenge="selectedChallenge" @back="back" :create="create"/>
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
