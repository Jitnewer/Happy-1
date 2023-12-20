<template>
  <div class="container-admin admin-event" v-if="!selectedCarousel">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <p>></p>
      <router-link :to="{ name: 'adminCarousel' }">Carousel</router-link>
    </div>
    <div class="title">
      <h1>Carousel</h1>
    </div>
    <div class="filters">
      <input type="text" class="search-filter" id="nameFilter" placeholder="Search for carousels.." title="Type in a name"
             v-model="filter.search">
      <input class="date-filter" type="date" id="dateFilter" v-model="filter.date">
      <button class="create-btn" @click="activateCreateCarousel">Create</button>
    </div>
    <div class="carousels">
      <div class="carousel" v-for="carousel in filteredCarousel" :key="carousel.id">
        <div class="carousel-left">
          <img :src="require(`../../../assets/img/${carousel.image}`)" alt="Carousel Image">
        </div>
        <div class="carousel-right">
          <div class="carousel-right-main">
            <div class="carousel-right-left">
              <h2>{{ carousel.text }}</h2>
            </div>
            <div class="carousel-right-right">
              <p>{{ parseDate(carousel.date)  }}</p>
              <p>{{ carousel.timeBegin.slice(0, 5) }} - {{ carousel.timeEnd.slice(0, 5) }}</p>
            </div>
          </div>
          <div class="carousel-right-bottom">
            <button @click="setSelectedCarousel(carousel.id)">Edit</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Carousel } from '@/models/carousel'

export default {
  name: 'AdminCarouselView',
  inject: ['carouselService'],
  data () {
    return {
      selectedCarousel: null,
      create: false,
      filter: {
        search: '',
        date: null
      },
      carousel: []
    }
  },
  methods: {
    setSelectedCarousel (carousel) {
      this.$router.push({ name: 'adminCarouselDetail', params: { id: carousel } })
    },
    activateCreateCarousel () {
      this.selectedCarousel = new Carousel()
      this.selectedCarousel.id = 0
      this.selectedCarousel.image = 'imagePlaceholder.jpg'
      this.create = true
      // this.$router.push(this.$route.matched[0].path + '/' + this.selectedEvent.id)
      this.$router.push({ name: 'adminCarouselDetail', params: { id: this.selectedCarousel.id } })
    },
    parseDate (dateString) {
      const dateObject = new Date(dateString)
      const day = dateObject.getDate()
      const month = dateObject.getMonth() + 1
      const year = dateObject.getFullYear()

      return `${day}-${month}-${year}`
    }
  },
  computed: {
    filteredCarousel () {
      if (!this.filter.date && !this.filter.search) {
        // If neither date nor search filter is set, return all events
        return this.carousel
      }

      // Filter events based on date and/or search filter
      return this.carousel.filter(event => {
        // Check if the event date matches the date filter (if set)
        const dateMatch = !this.filter.date || event.date === this.filter.date

        // Check if the event name contains the search filter (if set)
        const searchMatch = !this.filter.search || event.name.toLowerCase().includes(this.filter.search.toLowerCase())

        // Return true if both date and search filters match, or if neither filter is set
        return dateMatch && searchMatch
      })
    }
  },
  async created () {
    // if (localStorage.getItem('admin') === 'false') this.$router.push({ path: '/PageNotFound' })
    this.carousel = await this.carouselService.asyncFindAll()
  },
  watch: {
    '$route' (to, from) {
      console.log(to)
      const carouselId = parseInt(to.params.id)
      if (carouselId === 0) {
        return
      }
      if (carouselId) {
        this.selectedCarousel = this.carousel.find(carousel => carousel.id === carouselId)
      } else {
        this.selectedCarousel = null
      }
    }
  }
}
</script>

<style scoped>
.container-admin {
  margin-left: 1rem;
  margin-top: 1rem;
  width:100%
}
</style>
