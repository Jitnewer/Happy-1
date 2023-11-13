<template>
  <div class="container">
    <div>
      <h1>
        <span class="yellow-text">SUSTANCE</span> <span class="blue-text">FOR GOOD</span>
      </h1>
      <p>
        Happy Hospitality is a collective that sets the market in motion around sustainability within hospitality
        through
        innovation and collaboration. We connect users and chain partners by initiating innovation hubs and continuing
        to
        innovate from there for a sustainable catering industry.
      </p>
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
    <div>
      <h1>
        <span class="yellow-text">OUR</span> <span class="blue-text">THEMES</span>
      </h1>
      <p>Explore sustainability themes in hospitality: Food Waste, Single-Used Plastic, Distribution, Energy & Protein
        Transition, and Water Sustainability. Click to learn more and join us in creating a greener future for the
        catering industry.
      </p>
      <div class="themeContainer">
        <div class="theme">
          <button>
            <h2>
              <span class="yellow-text">Food</span> <span class="blue-text">Waste</span>
            </h2>
            <h4>Addressing food waste in the hospitality industry involves implementing sustainable practices to
              minimize
              excess food disposal, promoting efficiency, and contributing to a more responsible and environmentally
              friendly approach.</h4>
            <img class="themeImg" src="../../assets/img/foodWasteT.png" alt="">
          </button>
        </div>
        <div class="theme">
          <button>
            <h2>
              <span class="yellow-text">Distrib</span><span class="blue-text">ution</span></h2>
            <h4>Sustainable distribution practices in the hospitality sector aim to optimize supply chains, reduce
              carbon
              footprints, and enhance efficiency, contributing to a more environmentally conscious and socially
              responsible industry.</h4>
            <img class="themeImg" src="@/assets/img/Distribution.webp" alt="">
          </button>
        </div>
        <div class="theme">
          <button>
            <h2><span class="yellow-text">Energy</span> <span class="blue-text">Transition</span></h2>
            <h4>Embracing energy transition within hospitality involves adopting renewable energy sources,
              energy-efficient
              technologies, and sustainable practices to minimize environmental impact and contribute to a greener, more
              sustainable future.</h4>
            <img class="themeImg" src="@/assets/img/energyT.png" alt="">
          </button>
        </div>
        <div class="theme">
          <button>
            <h2><span class="yellow-text">Single Us</span><span class="blue-text">ed Plastic</span></h2>
            <h4>Tackling the issue of single-use plastic in hospitality focuses on reducing reliance on disposable
              plastic
              items, adopting eco-friendly alternatives, and fostering a commitment to minimizing plastic
              pollution.</h4>
            <img class="themeImg" src="@/assets/img/plasticT.png" alt="">
          </button>
        </div>
        <div class="theme">
          <button>
            <h2><span class="yellow-text">Protein</span> <span class="blue-text">Transition</span></h2>
            <h4>The protein transition in the hospitality industry emphasizes a shift towards sustainable and
              plant-based
              protein options, reducing reliance on traditional meat sources and promoting environmentally friendly
              alternatives.</h4>
            <img class="themeImg" src="@/assets/img/proteinT.avif" alt="">
          </button>
        </div>
        <div class="theme">
          <button>
            <h2>
              <span class="yellow-text">Wat</span><span class="blue-text">er</span>
            </h2>
            <h4>Promoting water sustainability in hospitality encompasses implementing water-efficient technologies,
              minimizing wastage, and making conscious choices to reduce the environmental impact of water usage in the
              industry.</h4>
            <img class="themeImg" src="@/assets/img/waterT.webp" alt="">
          </button>
        </div>
      </div>
      <h1>
        <span class="yellow-text">OUR</span> <span class="blue-text">PILLARS</span>
      </h1>
      <div class="pillarContainer">
        <button>
        <div class="pillar">
            <div class="pillarText">
              <h1>
                <span class="blue-text">Challenges</span>
              </h1>
              <h3>
                Explore sustainability challenges in the hospitality industry with our initiative.
              </h3>
              <h3><span class="blue-text"> > Read more</span> </h3>
            </div>
            <div class="pillarImg">
              <img class="pillarImgContent" src="@/assets/img/challenges2.jpeg" alt="">
            </div>
        </div>
        </button>
        <button>
          <div class="pillar">
            <div class="pillarText">
              <h1>
                <span class="blue-text">Network</span>
              </h1>
              <h3>
                Discover the challenges and innovations shaping eco-friendly networking practices within the hospitality
                industry.
              </h3>
              <h3><span class="blue-text"> > Read more</span> </h3>
            </div>
            <div class="pillarImg">
              <img class="pillarImgContent" src="@/assets/img/network.jpg" alt="">
            </div>
          </div>
        </button>
        <button>
          <div class="pillar">
            <div class="pillarText">
              <h1>
                <span class="blue-text">Research</span>
              </h1>
              <h3>
                Explore the challenges and advancements in sustainable research practices within the hospitality sector.

              </h3>
              <h3><span class="blue-text"> > Read more</span> </h3>
            </div>
            <div class="pillarImg">
              <img class="pillarImgContent" src="@/assets/img/research.jpg" alt="">
            </div>
          </div>
        </button>
      </div>
    </div>
    <div class="aboutUs">
      <h1>
        <span class="blue-text">ABOUT HAPPY HOSPITALITY</span> <span class="yellow-text">& </span><span
        class="blue-text">US</span>
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

</style>
