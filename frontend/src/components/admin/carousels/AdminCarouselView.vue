<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminCarousels' }">Carousel Slides</router-link>
    </div>
    <div class="challenges-admin">
      <div class="title-button">
        <h1>Carousel Slides</h1>
        <button @click="create()" class="admin-create">Create</button>
      </div>
      <table v-if="carousels">
        <thead>
        <tr>
          <th>Id</th>
          <th>Title</th>
          <th class="datetime">DateTime</th>
          <th class="image">Image</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="carousel in carousels" :key="carousel.id">
          <td>{{ carousel.id }}</td>
          <td>{{ carousel.title}}</td>
          <td class="datetime">{{ parseDate(carousel.date) }}</td>
          <td class="image"><img :src="carousel.image ? require(`../../../${carousel.image}`) : ''" alt="Carousel Image"></td>
          <td>
            <div class="table-buttons">
              <button class="edit" @click="edit(carousel.id)">Edit</button>
              <button class="delete" @click="remove(carousel)">Delete</button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      </div>
    </div>
</template>

<script>
export default {
  name: 'CarouselAdmin.vue',
  inject: ['carouselService', 'carouselServiceSuperUser', 'fileUploadService'],
  data () {
    return {
      filter: this.$route.query.sort,
      showFilter: false,
      selectedCarousel: null
    }
  },
  methods: {
    create () {
      this.$router.push({ name: 'adminCarouselCreate' })
    },
    paragraphs (carousel) {
      this.showParagraphs = !this.showParagraphs
      this.selectedCarousel = carousel
    },
    back () {
      this.showParagraphs = false
    },
    async remove (carousel) {
      try {
        console.log('Removing carousel with id:', carousel.id)
        await this.carouselServiceSuperUser.asyncDeleteById(carousel.id)
        console.log('Carousel removed successfully.')
        await this.fileUploadService.asyncDeleteImage(carousel.image)
        console.log('Image deleted successfully.')
        await this.carouselService.asyncFindAll()
        console.log('Updated carousel list.')
      } catch (e) {
        console.error(e)
      }
    },
    async updateCarousel () {
      try {
        await this.carouselService.asyncFindAll()
      } catch (e) {
        console.error(e)
      }
    },
    edit (id) {
      this.$router.push({ name: 'adminCarouselEdit', params: { id: id } })
    },
    parseDate (dateString) {
      const dateObject = new Date(dateString)
      const day = dateObject.getDate()
      const month = dateObject.getMonth() + 1
      const year = dateObject.getFullYear()

      return `${day}-${month}-${year}`
    }
  },
  async created () {
    await this.updateCarousel()
  },
  watch: {
    $route (to, from) {
      if (to.fullPath !== from.fullPath) {
        this.updateCarousel()
      }
    }
  },
  computed: {
    carousels () {
      return this.carouselService.entities
    },
    sortedParagraphs () {
      if (this.selectedCarousel) {
        // Sort paragraphs based on id
        return this.selectedCarousel.paragraphs.slice().sort((a, b) => a.id - b.id)
      }
      return []
    }
  }
}

</script>
