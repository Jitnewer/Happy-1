<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminResearches' }">Research Articles</router-link>
    </div>
    <div class="challenges-admin">
      <div class="title-button">
        <h1>Research Articles</h1>
        <button @click="create()">Create</button>
      </div>
      <table v-if="researches">
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
        <tr v-for="research in researches" :key="research.id">
          <td>{{ research.id }}</td>
          <td>{{ research.title }}</td>
          <td class="firstParagraph">{{ research.firstParagraph }}</td>
          <td v-if="research.dateTime" class="datetime">{{ formattedDateTime(research.dateTime) }}</td>
          <td class="theme_small">{{ research.theme }}</td>
          <td class="image"><img :src="research.image ? require(`../../../${research.image}`) : ''" alt="Research Image"></td>
          <td><button class="relation" @click="paragraphs(research)">Check Paragraphs</button></td>
          <td>
            <div class="table-buttons">
              <button class="edit" @click="edit(research.id)">Edit</button>
              <button class="delete" @click="remove(research)">Delete</button>
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
  name: 'ResearchesAdmin.vue',
  inject: ['researchService', 'researchServiceSuperUser', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      showParagraphs: false,
      selectedResearch: null
    }
  },
  methods: {
    create () {
      this.$router.push({ name: 'adminResearchCreate' })
    },
    paragraphs (research) {
      this.showParagraphs = !this.showParagraphs
      this.selectedResearch = research
    },
    back () {
      this.showParagraphs = false
    },
    async remove (research) {
      try {
        await this.researchServiceSuperUser.asyncDeleteById(research.id)
        await this.fileUploadService.asyncDeleteImage(research.image)
        await this.researchService.asyncFindAll()
      } catch (e) {
        console.error(e)
      }
    },
    async updateResearches () {
      try {
        await this.researchService.asyncFindAll()
      } catch (e) {
        console.error(e)
      }
    },
    edit (id) {
      this.$router.push({ name: 'adminResearchEdit', params: { id: id } })
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
    sortedParagraphs () {
      if (this.selectedResearch) {
        // Sort paragraphs based on id
        return this.selectedResearch.paragraphs.slice().sort((a, b) => a.id - b.id)
      }
      return []
    }
  }
}

</script>
