<template>
  <div class="breadcrum">
    <!-- Breadcrumb navigation links -->
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'news' }">News</router-link>
  </div>
  <div class="container news-container-user">
    <h1>News Articles</h1>
    <div class="news-user">
      <h3>Choose between these 3 articles sorts</h3>
      <div class="news-blocks">
        <!-- Clickable blocks for different article types -->
        <div class="news-block" @click="challenge()">
          <h5>Challenge Articles</h5>
        </div>
        <div class="news-block" @click="research()">
          <h5>Research Articles</h5>
        </div>
        <div class="news-block" @click="network()">
          <h5>Network Articles</h5>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'News',
  data () {
    return {
      filter: this.$route.query.sort
    }
  },
  methods: {
    /**
     * Navigate to the Challenges route with optional sorting query parameter.
     */
    challenge () {
      if (this.filter == null) {
        this.$router.push({ name: 'challenges' })
      } else {
        this.$router.push({ name: 'challenges', query: { sort: this.filter } })
      }
    },

    /**
     * Navigate to the Researches route with optional sorting query parameter.
     */
    research () {
      if (this.filter == null) {
        this.$router.push({ name: 'researches' })
      } else {
        this.$router.push({ name: 'researches', query: { sort: this.filter } })
      }
    },

    /**
     * Navigate to the Networks route with optional sorting query parameter.
     */
    network () {
      if (this.filter == null) {
        this.$router.push({ name: 'networks' })
      } else {
        this.$router.push({ name: 'networks', query: { sort: this.filter } })
      }
    }
  }
}
</script>
