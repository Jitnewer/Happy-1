<template>
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'about' }">About Us</router-link>
  </div>
  <div class="aboutUsContainer">
    <div class="aboutUsContent">
      <h1 class="aboutLanding">
        <span class="blue-text">ABOUT </span>
        <span class="yellow-text">HAPPY HOSPITALITY </span>
        <span class="blue-text">& </span>
        <span class="yellow-text"> US </span>
      </h1>
      <div class="aboutBanner">
        <img src="../assets/img/forestscreen.jpg" width="6000" height="1701" alt="About Us Banner">
      </div>

      <div class="aboutArticle" data-aos="fade-up" data-aos-once="false">
        <h2>
          <span class="blue-text">Sustain</span>
          <span class="yellow-text">ablility</span>
        </h2>
        <p class="aboutP">
          We have all heard the same story; one person talks about sustainability, the other about circular business
          models or a fair way of working. The newspaper is full of news about nitrogen, emissions, co2 reduction and
          less consumption of meat. Our pleasures are curbed, no more flying for a weekend, no more meat, and now the
          heater is also out. And the worst thing is that it all seems to be related and we have to take and bear that
          responsibility together.
        </p>
        <!-- We could add a picture next to the paragraphs-->
      </div>

      <div class="aboutArticle" data-aos="fade-up">
        <h2>
          <span class="blue-text">Catering </span>
          <span class="yellow-text">industry</span>
        </h2>
        <p class="aboutP">
          But where do you start as a catering entrepreneur? Who do you hold accountable if your supplier simply cannot
          meet your sustainable demand and you have to meet your customer demand again tomorrow and there must be a full
          menu? How do you address that account manager on the assortment if you feel like you are alone? And what
          questions should that account manager ask back to understand where your needs are in terms of sustainability?
          How can you work together so that it is just fair, sustainable and above all fun in the hospitality industry?
        </p>
        <!-- We could add a picture next to the paragraphs-->
      </div>

      <div class="aboutArticle" data-aos="fade-up">
        <h2>
          <span class="blue-text">Happy </span>
          <span class="yellow-text">Hospitality!</span>
        </h2>
        <p class="aboutP">
          Happy Hospitality is an initiative of Karin Lindenhovius, Ellekari Klaasse, Carlijn Wijnmalen-Groen and World
          Startup. We tackle the most current sustainable themes, the biggest hospitality problems and work together on
          constructive solutions and innovations.
          We do this by facilitating the conversation, conducting a large-scale survey in the market, translating the
          insights into concrete problem statements, analyzing them with experts by coming up with different value
          propositions, and then testing them in the market.
          We share the insights and results with the market, so that everyone can learn and benefit and we work
          together on a happy sustainable hospitality market.
        </p>
        <!-- We could add a picture next to the paragraphs-->
      </div>
    </div>
  </div>
</template>

<script>
import AOS from 'aos'

export default {
  name: 'AboutUs',
  data () {
    return {}
  },
  created () {
    console.log('Hello World!')
  }
}

</script>

<style scoped>

</style>
