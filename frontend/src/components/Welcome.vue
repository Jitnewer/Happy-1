<template>
<div class="container">
  <Carousel class="carousel" v-slot="{ currentSlide }">
    <Slide v-for="(slide, index) in carouselSlides" :key="index">
      <div v-show="currentSlide === index + 1" class="slide">
        <img :src="require(`@/assets/img/carousel/${slide}.jpg`)" alt="">
      </div>
    </Slide>
  </Carousel>
</div>
</template>

<script>
import Carousel from '../components/Carousel.vue'
import Slide from '../components/Slide.vue'
export default {
  name: 'Welcome.vue',
  components: { Carousel, Slide },
  data () {
    return {
      carouselSlides: ['bg-1', 'bg-2', 'bg-3']
    }
  }
}
</script>

<style scoped>
.carousel {
  position: relative;
  max-height: 600px;
  max-width: 600px;
}
.slide-info {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  max-height: 100%;
  height: 100%;
}
 /*img {*/
 /*  min-width: 100%;*/
 /*  height: 100%;*/
 /*  object-fit: cover;*/
 /*}*/
</style>
