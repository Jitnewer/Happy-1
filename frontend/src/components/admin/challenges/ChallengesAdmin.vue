<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'welcome' }">Admin</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminChallenges' }">Challenges</router-link>
    </div>
    <div class="challenges-admin">
      <div class="title-button">
      <h1>Challenges</h1>
      <button @click="create()">Create</button>
      </div>
      <table v-if="challenges">
        <thead>
        <tr>
          <th>Id</th>
          <th>Title</th>
          <th class="firstParagraph">First Paragraph</th>
          <th class="datetime">DateTime</th>
          <th class="theme_small">Theme</th>
          <th class="image">Image</th>
          <th>Paragraphs</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="challenge in challenges" :key="challenge.id">
          <td>{{ challenge.id }}</td>
          <td>{{ challenge.title }}</td>
          <td class="firstParagraph">{{ challenge.firstParagraph }}</td>
          <td class="datetime">{{ formattedDateTime(challenge.dateTime) }}</td>
          <td class="theme_small">{{ challenge.theme }}</td>
          <td class="image"><img :src="challenge.image ? require(`../../../${challenge.image}`) : ''" alt="Challenge Image"></td>
          <td><button class="relation" @click="paragraphs(challenge)">Check Paragraphs</button></td>
          <td>
            <div class="table-buttons">
     <button class="edit" @click="edit(challenge.id)">Edit</button>
         <button class="delete" @click="remove(challenge)">Delete</button>
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
  inject: ['challengeService', 'challengeServiceAdmin', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      showParagraphs: false,
      selectedChallenge: null
    }
  },
  methods: {
    create () {
      this.$router.push({ name: 'adminChallengeCreate' })
    },
    paragraphs (challenge) {
      this.showParagraphs = !this.showParagraphs
      this.selectedChallenge = challenge
    },
    back () {
      this.showParagraphs = false
    },
    async remove (challenge) {
      try {
        await this.challengeServiceAdmin.asyncDeleteById(challenge.id)
        await this.fileUploadService.asyncDeleteImage(challenge.image)
        await this.challengeService.asyncFindAll()
      } catch (e) {
        console.error(e)
      }
    },
    async updateChallenges () {
      try {
        await this.challengeService.asyncFindAll()
      } catch (e) {
        console.error(e)
      }
    },
    edit (id) {
      this.$router.push({ name: 'adminChallengeEdit', params: { id: id } })
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
