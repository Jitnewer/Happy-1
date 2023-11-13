<template>
  <div class="aboutUsContent">
    <div class="aboutUsTitle"><h1>OVER HAPPY HOSPITALITY <span class="yellow-text">&</span> ONS</h1></div>
    <div><h2>Duurzaamheid</h2></div>
    <div class="aboutUsRow">
      <div class="aboutUsText">
        <p>
          We hebben allemaal de klok wel horen luiden; de een heeft het over duurzaamheid, de ander over circulaire
          business modellen of een eerlijke manier van werken. De krant staat bol van nieuws over stikstof, uitstoot,
          co2 reductie sus en minder vlees zo. Onze geneugten worden aan banden gelegd, niet meer vliegen voor een
          weekendje, geen vlees meer, en nu ook nog eens de kachel uit. En het erge is dat het allemaal verband schijnt
          te hebben en we die verantwoordelijkheid samen moeten pakken en dragen. </p>
      </div>
      <img src="../assets/img/sustainability.jpg" class="aboutUsImage" alt="Image">
    </div>
    <div><h2>Horeca's</h2></div>
    <div class="aboutUsRow">
      <img src="../assets/img/horeca.jpg" class="aboutUsImage" alt="Image">
      <div class="aboutUsText"><p>
        Maar waar begin je dan als horeca ondernemer? Wie spreek je aan als je toeleverancier gewoonweg niet aan je
        duurzame vraag kan voldoen en jij morgen wel weer aan je klant vraag moet voldoen en er een volle menukaart
        aanwezig moet zijn? Hoe spreek je die accountmanager aan op het assortiment als je het gevoel hebt dat je alleen
        staat? En welke vragen moet die accountmanager terugstellen om goed te begrijpen waar je behoefte ligt op het
        gebied van duurzaamheid? Hoe kan je samenwerken zodat het gewoon eerlijk, duurzaam en vooral gezellig is in de
        horeca?
      </p></div>
    </div>
    <div><h2>Happy Hospitality!</h2></div>
    <div class="aboutUsRow">
      <div class="aboutUsText">
        <p>
          Happy Hospitality is een initiatief van Karin Lindenhovius, Ellekari Klaasse, Carlijn Wijnmalen-Groen en World
          Startup. We pakken de meest actuele duurzame thema’s, de grootste horeca problemen en gaan met elkaar werken aan
          constructieve oplossingen en innovaties.
          Dit doen we door het gesprek te faciliteren, een grootschalig onderzoek uit te zetten in de markt, de
          inzichten te vertalen naar concrete probleemstellingen, deze met experts te analyseren door met verschillende
          waardeproposities te komen, en deze vervolgens te testen in de markt.
          De inzichten en uitkomsten delen we met de markt, zodat iedereen kan mee leren en profiteren en we samen
          werken aan een gelukkige duurzame horeca markt.
        </p>
      </div>
      <img src="../assets/img/happy.webp" class="aboutUsImage" alt="Image">
    </div>
  </div>
</template>

<script>

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

<style>
p {
  //color: black !important;
}

h1, h2 {
  //color: #0992A4 !important;
}

h2 {
  //margin-top: 50px !important;
}

.aboutUsContent {
  display: flex;
  flex-direction: column;
}

.aboutUsRow {
  display: flex;
  flex-direction: row;
  width: 100%;
  //background-color: #FECC30;
  margin:  10px auto;
}

.aboutUsText {
  margin: auto;
  max-width: 50%;
//height: 200px;
}

.aboutUsImage {
  margin:auto;
  max-width: 40%;
//height: 200px;
}

.yellow-text {
  color: #FECC32;
}
.blue-text {
  color: #1F93A5;
}
</style>
