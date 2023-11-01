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
  <div>
    <h1>
      <span class="yellow-text">OUR</span> <span class="blue-text">THEMES</span>
    </h1>
    <div class="theme">
      <button>
        <img class="themeImg" src="@/assets/img/restaurant1.png" alt="">
      </button>
      <h5>In the dynamic world of hospitality, integrating sustainability practices into water management is a pivotal
        step towards a greener and more responsible industry. From sourcing to consumption, every aspect of water usage
        offers an opportunity for positive environmental impact. This journey involves conscious choices, such as
        adopting water-efficient technologies, minimizing wastage, and embracing eco-friendly purification methods.
        By navigating the waters of sustainability in the hospitality sector, establishments can not only contribute
        to environmental conservation but also enhance their reputation as responsible stewards in the ever-evolving
        landscape of hospitality.</h5>
    </div>

  </div>

    <div class="carouselHolder">
      <Carousel class="carousel" v-slot="{ currentSlide }">
        <Slide v-for="(slide, index) in carouselSlides" :key="index">
          <div v-show="currentSlide === index + 1" class="slide">
            <img class="homePageImg" :src="require(`@/assets/img/carousel/${slide}.jpg`)" alt="">
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
        In the hospitality sector, sustainability in water management is key. From sourcing to consumption, making
        conscious choices like adopting water-efficient technologies and minimizing wastage can significantly reduce
        environmental impact. By embracing eco-friendly practices, establishments not only contribute to conservation
        but also bolster their reputation as responsible players in the evolving hospitality landscape.
      </p>
      <div class="imageHolder">
        <img class="homePageImg" src="@/assets/img/restaurant1.png" alt="">
        <img class="homePageImg" src="@/assets/img/restaurant2.jpg" alt="">
      </div>
    </div>
    <div>

    </div>
  </div>
</template>

<script>
import Carousel from './Carousel.vue'
import Slide from './Slide.vue'
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
.theme {
  display: flex;
  align-items: center;
  border: 2px solid black;
}
.theme button {
  background: none;
  border: none;
}
.theme .themeImg {
  width: 100%;
  height: 100%;

}

.theme h5{
  color: black;
  margin-left: 20px;
}
</style>
