<template>
  <div class="containerHomepage">
    <div>
      <h1 class="welcomeH1">
        <span class="yellow-text">SUSTANCE</span> <span class="blue-text">FOR GOOD</span>
      </h1>
      <p class="welcomeP">
        Happy Hospitality is a collective that sets the market in motion around sustainability within hospitality
        through
        innovation and collaboration. We connect users and chain partners by initiating innovation hubs and continuing
        to
        innovate from there for a sustainable catering industry.
      </p>
    </div>
    <div class="carouselHolder">
      <Carousel class="carousel" v-slot="{ currentSlide }" :slideCount="carouselSlides.length">
        <Slide v-for="(slide, index) in carouselSlides" :key="index" :slide-text="slide.text"
               :is-current="index + 1">
          <div v-show="currentSlide === index + 1" class="slide">
            <div class="welcomeMsg">
              <h2 class="blue-text"> {{ formattedDateTime(slide.dateTime) }} </h2>
              <h2 class="yellow-text"> {{ slide.text }}</h2>
              <button>
                <span class="blue-text"> > Lees meer </span>
              </button>
            </div>
            <img class="carouselImg" :src="require(`../../${slide.image}`)" alt="Image">
          </div>
        </Slide>
      </Carousel>
    </div>
    <h1 class="welcomeH1" data-aos="fade-up">
      <span class="yellow-text">OUR</span> <span class="blue-text">PILLARS</span>
    </h1>
    <div class="pillarContainer">
      <button @click="challenges">
        <div class="pillar" data-aos="fade-up">
          <div class="pillarText">
            <h1 class="welcomeH1">
              <span class="blue-text">Challenges</span>
            </h1>
            <h3>
              Explore sustainability challenges in the hospitality industry with our initiative.
            </h3>
            <h3><span class="blue-text"> > Read more</span></h3>
          </div>
          <div class="pillarImg">
            <img class="pillarImgContent" src="../../assets/img/homepage/challenges2.jpeg" alt="">
          </div>
        </div>
      </button>
      <button @click="network">
        <div class="pillar" data-aos="fade-up">
          <div class="pillarText">
            <h1 class="welcomeH1">
              <span class="blue-text">Network</span>
            </h1>
            <h3>
              Discover the challenges and innovations shaping eco-friendly networking practices within the
              hospitality
              industry.
            </h3>
            <h3><span class="blue-text"> > Read more</span></h3>
          </div>
          <div class="pillarImg">
            <img class="pillarImgContent" src="../../assets/img/homepage/network.jpg" alt="">
          </div>
        </div>
      </button>
      <button @click="research">
        <div class="pillar" data-aos="fade-up">
          <div class="pillarText">
            <h1 class="welcomeH1">
              <span class="blue-text">Research</span>
            </h1>
            <h3>
              Explore the challenges and advancements in sustainable research practices within the hospitality
              sector.

            </h3>
            <h3><span class="blue-text"> > Read more</span></h3>
          </div>
          <div class="pillarImg">
            <img class="pillarImgContent" src="../../assets/img/homepage/research.jpg" alt="">
          </div>
        </div>
      </button>
    </div>
    <div>
      <h1 class="welcomeH1">
        <span class="yellow-text">OUR</span> <span class="blue-text">THEMES</span>
      </h1>
      <p class="welcomeP">
        Explore sustainability themes in hospitality: Food Waste, Single-Used Plastic, Distribution, Energy &
        Protein
        Transition, and Water Sustainability. Click to learn more and join us in creating a greener future for the
        catering industry.
      </p>
      <div class="themeContainer">
        <div class="theme" data-aos="fade-up">
          <button class="themeButton" @click="foodWasteNews">
            <h2>
              <span class="yellow-text">Food</span> <span class="blue-text">Waste</span>
            </h2>
            <h4 class="themeH4">Combat hospitality food waste with sustainable practices, efficiency, and eco-friendly approaches
              for a responsible industry.</h4>
            <img class="themeImg" src="../../assets/img/homepage/foodWaste.png" alt="">
          </button>
        </div>
        <div class="theme" data-aos="fade-up">
          <button class="themeButton" @click="distributionNews">
            <h2>
              <span class="yellow-text">Distrib</span><span class="blue-text">ution</span></h2>
            <h4 class="themeH4">Enhance hospitality sustainability with optimized supply chains, reduced carbon footprints,
              and efficient, socially responsible practices.</h4>
            <img class="themeImg" src="@/assets/img/homepage/truck.png" alt="">
          </button>
        </div>
        <div class="theme" data-aos="fade-up">
          <button class="themeButton" @click="energyTransitionNews">
            <h2><span class="yellow-text">Energy</span> <span class="blue-text">Transition</span></h2>
            <h4 class="themeH4">Embrace energy transition in hospitality through renewables, energy-efficient tech, and
              sustainable practices for a greener future.</h4>
            <img class="themeImg" src="@/assets/img/homepage/energy2.png" alt="">
          </button>
        </div>
        <div class="theme" data-aos="fade-up">
          <button class="themeButton" @click="singleUsedPlasticNews">
            <h2><span class="yellow-text">Single Us</span><span class="blue-text">ed Plastic</span></h2>
            <h4 class="themeH4">Combatting hospitality's single-use plastic issue involves reducing reliance, adopting eco-friendly
              alternatives, and minimizing plastic pollution commitment.</h4>
            <img class="themeImg" src="@/assets/img/homepage/plastic.png" alt="">
          </button>
        </div>
        <div class="theme" data-aos="fade-up">
          <button class="themeButton" @click="proteinTransitionNews">
            <h2><span class="yellow-text">Protein</span> <span class="blue-text">Transition</span></h2>
            <h4 class="themeH4">Hospitality's protein transition emphasizes sustainable, plant-based options, reducing reliance on
              traditional meat, and promoting eco-friendly alternatives.</h4>
            <img class="themeImg" src="@/assets/img/homepage/protein.png" alt="">
          </button>
        </div>
        <div class="theme" data-aos="fade-up">
          <button class="themeButton" @click="waterNews">
            <h2>
              <span class="yellow-text">Wat</span><span class="blue-text">er</span>
            </h2>
            <h4 class="themeH4">Promote water sustainability in hospitality with efficient technologies, reduced wastage, and
              conscious choices to minimize environmental impact.</h4>
            <img class="themeImg" src="@/assets/img/homepage/water.png" alt="">
          </button>
        </div>
      </div>
    </div>
    <div class="aboutUs" data-aos="fade-up">
      <h1 class="welcomeH1">
        <span class="blue-text">ABOUT HAPPY HOSPITALITY</span> <span class="yellow-text">& </span><span
        class="blue-text">US</span>
      </h1>
      <p class="welcomeP">
        In the hospitality sector, sustainability in water management is key. From sourcing to consumption, making
        conscious choices like adopting water-efficient technologies and minimizing wastage can significantly reduce
        environmental impact. By embracing eco-friendly practices, establishments not only contribute to conservation
        but also bolster their reputation as responsible players in the evolving hospitality landscape.
      </p>
      <div class="imageHolder" data-aos="fade-up">
        <img class="homePageImg" src="../../assets/img/homepage/happy2.webp" alt="">
        <img class="homePageImg" src="../../assets/img/homepage/happ3.webp" alt="">
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
  inject: ['carouselService'],
  components: { Carousel, Slide },
  data () {
    return {
    }
  },
  async mounted () {
    await this.carouselService.asyncFindAll()
    console.log('carouselSlides:', this.carouselSlides)
  },
  methods: {
    getFormattedDate (dateString) {
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('nl-NL', options)
    },
    formattedDateTime (dateTime) {
      const today = new Date()
      const challengeDate = new Date(dateTime)

      const formattedTime = challengeDate.toLocaleTimeString('nl-NL', { hour: '2-digit', minute: '2-digit' })

      if (
        challengeDate.getDate() === today.getDate() &&
    challengeDate.getMonth() === today.getMonth() &&
    challengeDate.getFullYear() === today.getFullYear()
      ) {
        // Vandaag, (tijd)
        return `Vandaag, ${formattedTime}`
      } else if (
        challengeDate.getDate() === today.getDate() - 1 &&
    challengeDate.getMonth() === today.getMonth() &&
    challengeDate.getFullYear() === today.getFullYear()
      ) {
        // Gisteren, (tijd)
        return `Gisteren, ${formattedTime}`
      } else {
        // Maandag, (tijd), Donderdag (tijd)
        return `${this.getFormattedDate(dateTime)}, ${formattedTime}`
      }
    },
    foodWasteNews () {
      this.$router.push({ name: 'news', query: { sort: 'FOOD_WASTE' } })
    },
    distributionNews () {
      this.$router.push({ name: 'news', query: { sort: 'DISTRIBUTION' } })
    },
    energyTransitionNews () {
      this.$router.push({ name: 'news', query: { sort: 'ENERGY_TRANSITION' } })
    },
    singleUsedPlasticNews () {
      this.$router.push({ name: 'news', query: { sort: 'SINGLE_USED_PLASTIC' } })
    },
    proteinTransitionNews () {
      this.$router.push({ name: 'news', query: { sort: 'PROTEIN_TRANSITION' } })
    },
    waterNews () {
      this.$router.push({ name: 'news', query: { sort: 'WATER' } })
    },
    challenges () {
      this.$router.push({ name: 'challenges' })
    },
    network () {
      this.$router.push({ name: 'networks' })
    },
    research () {
      this.$router.push({ name: 'researches' })
    }
  },
  computed: {
    carouselSlides () {
      return this.carouselService.entities
    }
  }
}
</script>

<style scoped>

</style>
