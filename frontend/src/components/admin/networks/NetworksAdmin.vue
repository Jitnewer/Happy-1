<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminNetworks' }">Network Articles</router-link>
    </div>
    <div class="challenges-admin">
      <div class="title-button">
        <h1>Network Articles</h1>
        <button @click="create()">Create</button>
      </div>
      <table v-if="networks">
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
        <tr v-for="network in networks" :key="network.id">
          <td>{{ network.id }}</td>
          <td>{{ network.title }}</td>
          <td class="firstParagraph">{{ network.firstParagraph }}</td>
          <td v-if="network.dateTime" class="datetime">{{ formattedDateTime(network.dateTime) }}</td>
          <td class="theme_small">{{ network.theme }}</td>
          <td class="image"><img :src="network.image ? `https://ik.imagekit.io/happy1hva${network.image}` : ''" alt="Network Image"></td>
          <td><button class="relation" @click="paragraphs(network)">Check Paragraphs</button></td>
          <td>
            <div class="table-buttons">
              <button class="edit" @click="edit(network.id)">Edit</button>
              <button class="delete" @click="remove(network)">Delete</button>
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
  name: 'NetworksAdmin.vue',
  inject: ['networkService', 'networkServiceSuperUser', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      showParagraphs: false,
      selectedNetwork: null
    }
  },
  methods: {
    create () {
      this.$router.push({ name: 'adminNetworkCreate' })
    },
    paragraphs (network) {
      this.showParagraphs = !this.showParagraphs
      this.selectedNetwork = network
    },
    back () {
      this.showParagraphs = false
    },
    async remove (network) {
      try {
        await this.networkServiceSuperUser.asyncDeleteById(network.id)
        await this.fileUploadService.asyncDeleteImage(network.image)
        await this.networkService.asyncFindAll()
      } catch (e) {
        console.error(e)
      }
    },
    async updateNetworks () {
      try {
        await this.networkService.asyncFindAll()
      } catch (e) {
        console.error(e)
      }
    },
    edit (id) {
      this.$router.push({ name: 'adminNetworkEdit', params: { id: id } })
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
    sortedParagraphs () {
      if (this.selectedNetwork) {
        // Sort paragraphs based on id
        return this.selectedNetwork.paragraphs.slice().sort((a, b) => a.id - b.id)
      }
      return []
    }
  }
}

</script>
