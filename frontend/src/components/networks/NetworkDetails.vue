<script>
import networks from './Networks.vue'

export default {
  name: 'NetworkDetails.vue',
  inject: ['networkService'],
  methods: {
    getFormattedDate (dateString) {
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('nl-NL', options)
    },
    formattedDateTime (dateTime) {
      const today = new Date()
      const networkDate = new Date(dateTime)

      const formattedTime = networkDate.toLocaleTimeString('nl-NL', { hour: '2-digit', minute: '2-digit' })

      if (
        networkDate.getDate() === today.getDate() &&
        networkDate.getMonth() === today.getMonth() &&
        networkDate.getFullYear() === today.getFullYear()
      ) {
        // Vandaag, (tijd)
        return `Vandaag, ${formattedTime}`
      } else if (
        networkDate.getDate() === today.getDate() - 1 &&
        networkDate.getMonth() === today.getMonth() &&
        networkDate.getFullYear() === today.getFullYear()
      ) {
        // Gisteren, (tijd)
        return `Gisteren, ${formattedTime}`
      } else {
        // Maandag, (tijd), Donderdag (tijd)
        return `${this.getFormattedDate(dateTime)}, ${formattedTime}`
      }
    },
    async back () {
      await this.networkService.asyncFindAll()

      this.$router.push({ name: 'networks' })
      this.$emit('update-selected-network')
    }
  },
  async created () {
    try {
      await this.networkService.asyncFindById(this.$route.params.id)
    } catch (e) {
      console.error(e)
    }
  },
  computed: {
    network () {
      return this.networkService.entities
    },
    sortedNetworks () {
      return this.networks.slice().sort((a, b) => {
        const dateA = new Date(a.dateTime)
        const dateB = new Date(b.dateTime)
        return dateA - dateB
      })
    }
  }
}
</script>

<template>
  <div class="breadcrum" v-if="network">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <a>News</a>
    <p>></p>
    <router-link :to="{ name: 'networks' }">Networks</router-link>
    <p>></p>
    <router-link :to="{ name: 'network', params: { id: network.id } }">Network / {{ network.id }}</router-link>
  </div>
  <div v-if="network" class="container">
    <div class="challenge-main">
      <div class="challenge-title">
        <button @click="back">Back</button>
        <h1>Network Article</h1>
      </div>
      <div class="detail-challenge">
        <div>
          <img :src="network.image ? require(`../../${network.image}`) : ''" alt="Challenge Image">
        </div>
        <div class="content">
          <div>
            <p class="challenge-time">{{ formattedDateTime(network.dateTime) }}</p>
            <h2>{{ network.title }}</h2>
            <p>{{ network.firstParagraph}} </p>
          </div>
          <div v-for="paragraph in network.paragraphs" :key="paragraph.id">
            <h4>{{ paragraph.title }}</h4>
            <p>{{ paragraph.content }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
