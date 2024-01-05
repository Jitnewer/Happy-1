<script>
import { Research } from '@/models/research'
export default {
  name: 'SuperUserView',
  data () {
    return {
      filter: {
        search: '',
        tag: null
      },
      newsList: [],
      selectedNews: null,
      isSelected: false,
      createNews: false,
      idUse: 400
    }
  },
  methods: {
    create () {
      this.selectedNews = new Research()
      this.selectedNews.id = (this.idUse + 1)
      this.idUse++
      this.selectedNews.image = require('../../../assets/challengePic/imagePlaceholder.jpg')
      this.selectedNews.image = require('../../../../assets/images/imagePlaceholder.jpg')
      this.createNews = true
      this.isSelected = !this.isSelected
      this.$router.push(this.$route.matched[0].path + '/' + this.selectedNews.id)
    },
    setByUrl () {
      for (let i = 0; i < this.newsList.length; i++) {
        if (parseInt(this.$route.params.id) === this.newsList[i].id) {
          this.selectedNews = this.newsList[i]
          this.isSelected = !this.isSelected
          break
        }
      }
    },
    saveNews (news) {
      const indexToSave = this.findIndex(news)
      if (indexToSave >= 0) {
        this.newsList.splice(indexToSave, 1, news)
      } else {
        this.newsList.push(news)
      }

      this.createNews = false
      this.isSelected = false
      this.selectedNews = null
      this.$router.push(this.$route.matched[0])
    },
    findIndex (newsToFind) {
      return this.newsList.findIndex(news => news.id === newsToFind.id)
    },
    selectNews (news) {
      this.$router.push(this.$route.matched[0].path + '/' + news.id)
    },
    closeNews () {
      this.selectedNews = null
      this.isSelected = !this.isSelected
      this.createNews = false
      this.$router.push(this.$route.matched[0])
    },
    deleteNews (newsToDelete) {
      const indexToDelete = this.newsList.findIndex(news => news.id === newsToDelete.id)
      if (indexToDelete !== -1) {
        this.newsList.splice(indexToDelete, 1)
      }

      this.selectedNews = null // Clear the selected event
      this.isSelected = !this.isSelected // Update the isSelected flag
      this.createNews = false
      this.$router.push(this.$route.matched[0])
    }
  },
  created () {
    // if (localStorage.getItem('admin') !== 'true') this.$router.push({ path: '/PageNotFound' })

    for (let i = 0; i < 2; i++) {
      this.newsList.push(Research.createSampleNews(i))
    }
  },
  watch: {
    '$route' () {
      this.setByUrl()
    }
  }
}
</script>

<template>
  <div class="container" v-if="!isSelected">
    <div class="title">
      <h2>Research</h2>
    </div>
    <div class="filters">
      <input type="text" class="search-filter" id="nameFilter" placeholder="Search for events.." title="Type in a name"
             v-model="filter.search">
      <button @click="create" class="create-btn">Create</button>
    </div>
    <div class="news-container">
      <div class="news" v-for="news in newsList" :key="news.id" @click="selectNews(news)">
        <img :src="news.image">
        <div class="news-info">
          <div class="black-overlay"></div>
          <h3 class="news-title"> {{ news.title }} </h3>
          <div class="extra">
            <p> {{ news.author }} </p>
            <p> {{ news.publishDate }} </p>
          </div>
          <div class="news-tags">
            <div class="tag" v-for="tag in news.tags" :key="tag"> {{ tag }} </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <router-view class="news-details-view" v-if="isSelected" :selectedNews="selectedNews" :create="createNews"
               @deselect-news="closeNews" @delete-news="deleteNews" @save-news="saveNews"/>
</template>

<style scoped>
.container {
  width: 100%;
  padding: 20px;
}

h1, h2, h4, h5, h6 {
  //margin: 0;
  //color: black;
}
</style>
