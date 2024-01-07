<template>
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'news' }">News</router-link>
  </div>
  <div class="container news-container-user">
    <h1>News Articles</h1>
    <div class="news-user">
      <h3>Choose between of these 3 articles sorts</h3>
      <div class="news-blocks">
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
  name: 'News.vue',
  data () {
    return {
      filter: this.$route.query.sort
    }
  },
  methods: {
    challenge () {
      if (this.filter == null) {
        this.$router.push({ name: 'challenges' })
      } else {
        this.$router.push({ name: 'challenges', query: { sort: this.filter } })
      }
    },
    research () {
      if (this.filter == null) {
        this.$router.push({ name: 'researches' })
      } else {
        this.$router.push({ name: 'researches', query: { sort: this.filter } })
      }
    },
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
