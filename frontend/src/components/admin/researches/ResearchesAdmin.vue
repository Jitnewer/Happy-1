<template>
  <!-- Container for the Research Articles Admin -->
  <div class="container-admin">
    <!-- Breadcrumb navigation -->
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminResearches' }">Research Articles</router-link>
    </div>
    <!-- Main section for Research Articles Admin -->
    <div class="challenges-admin">
      <!-- Title and create button -->
      <div class="title-button">
        <h1>Research Articles</h1>
        <button @click="create()" class="admin-create">Create</button>
      </div>
      <!-- Research Articles table -->
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
        <!-- Iterate over research articles -->
        <tr v-for="research in researches" :key="research.id">
          <td>{{ research.id }}</td>
          <td>{{ research.title }}</td>
          <td class="firstParagraph">{{ research.firstParagraph }}</td>
          <td v-if="research.dateTime" class="datetime">{{ formattedDateTime(research.dateTime) }}</td>
          <td class="theme_small">{{ research.theme }}</td>
          <td class="image"><img :src="research.image ? require(`../../../${research.image}`) : ''" alt="Research Image"></td>
          <td><button class="relation" @click="paragraphs(research)">Check Paragraphs</button></td>
          <td>
            <!-- Buttons for edit and delete actions -->
            <div class="table-buttons">
              <button class="edit" @click="edit(research.id)">Edit</button>
              <button class="delete" @click="remove(research)">Delete</button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      <!-- Paragraphs section -->
      <div class="paragraphs paragraphs-admin" v-if="showParagraphs">
        <div>
          <!-- Back button for Paragraphs section -->
          <button @click="back" class="back-button">Back</button>
          <!-- Table to display paragraphs -->
          <table>
            <thead>
            <tr>
              <th>Id</th>
              <th>Title</th>
              <th>Content</th>
            </tr>
            </thead>
            <tbody>
            <!-- Iterate over sorted paragraphs -->
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
    // Remove a research article
    async remove (research) {
      try {
        const response = await this.researchServiceSuperUser.asyncDeleteById(research.id)
        await this.fileUploadService.asyncDeleteImage(research.image)
        await this.researchService.asyncFindAll()
        // Display success message
        this.$store.commit('setSuccess', true)
        this.$store.commit('setSuccessMessage', response.message)
        setTimeout(() => {
          // Hide success message after 8 seconds
          this.$store.commit('setSuccess', false)
          this.$store.commit('setSuccessMessage', null)
        }, 8000)
      } catch (e) {
        console.error(e.toJSON())
        this.$store.commit('setError', true)
        this.$store.commit('setErrorMessage', e.toJSON().error)
        setTimeout(() => {
          // Hide error message after 8 seconds
          this.$store.commit('setError', false)
          this.$store.commit('setErrorMessage', null)
        }, 8000)
      }
    },
    // Update the list of research articles
    async updateResearches () {
      try {
        await this.researchService.asyncFindAll()
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
    // Navigate to the edit page
    edit (id) {
      this.$router.push({ name: 'adminResearchEdit', params: { id: id } })
    },
    // Format a date/time string
    getFormattedDate (dateString) {
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('nl-NL', options)
    },
    // Format a date/time string
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
