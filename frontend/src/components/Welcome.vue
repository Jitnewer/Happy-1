<template>
  <div class="container">
    <div>
      <h1>
        <span class="yellow-text">SUSTANCE</span> <span class="blue-text">FOR GOOD</span>
      </h1>
      <p>
        Happy Hospitality is a collective that sets the market in motion around sustainability within hospitality through
        innovation and collaboration. We connect users and chain partners by initiating innovation hubs and continuing to
        innovate from there for a sustainable catering industry.
      </p>
    </div>

    <div class="carouselHolder">
      <Carousel class="carousel" v-slot="{ currentSlide }">
        <Slide v-for="(slide, index) in carouselSlides" :key="index">
          <div v-show="currentSlide === index + 1" class="slide">
            <img :src="require(`@/assets/img/carousel/${slide}.jpg`)" alt="">
          </div>
        </Slide>
      </Carousel>
    </div>

    <div class="aboutUs">
      <h1>
        <span class="blue-text">OVER HAPPY HOSPITALITY</span> <span class="yellow-text">& </span><span
        class="blue-text">ONS</span>
      </h1>
      <p>
        We have all heard the bell tolling; one talks about sustainability, the other about circular business models or a
        fair way of working. The newspaper is full of news about nitrogen, emissions, CO2 reduction and less meat.
        Our pleasures are being restricted, no more flying for a weekend, no more meat, and now also out of the stove.
        And the bad thing is that it all seems to be related and we have to take and bear that responsibility together.
      </p>
      <div class="imageHolder">
        <img src="@/assets/img/restaurant1.png" alt="">
        <img src="@/assets/img/restaurant2.jpg" alt="">
      </div>
    </div>
    <div>

    </div>
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
.container {
  margin-top: 20px;
  margin-bottom: 20px;
  position: relative;
}
h1 {
  color: black;
  margin-bottom: 10px;
  margin-top: 70px;
}
p {
  color: black;
  margin-bottom: 100px;
  margin-top: 30px;
}
.yellow-text {
  color: #FECC32;
}
.blue-text {
  color: #1F93A5;
}
.carousel {
  max-width: 80%;
  margin: auto;
}
.carouselHolder {
  position: relative;
  margin-top: 20px;
  z-index: 1;
}
.slide {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  max-height: 100%;
  height: 100%;
}
img {
   width: 100%;
   object-fit: cover;
  aspect-ratio: 21/9;
  height: 350px;
  border: 1px solid black;
 }
.imageHolder img {
  aspect-ratio: 16/9;
  width: 50%;
}
.imageHolder {
  display: flex;
  gap: 30px;
  justify-content: center;
  align-items: center;
}
.aboutUs {
  margin-top: 150px;
}
</style>
