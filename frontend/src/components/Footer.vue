<template>
  <div class="container-footer">
    <div class="topFooter">
      <div class="topFooterLeft">
        <p class="blue-text">Follow Us</p>
        <p class="white-text">On social media</p>
        <div class="social-icons">
          <button class="iconButtonHomepage" @click="goToInstagram"><i class="fab fa-instagram"></i></button>
          <button class="iconButtonHomepage" @click="goToLinkedIn"><i class="fab fa-linkedin"></i></button>
        </div>
      </div>
      <div class="topFooterRight">
        <div class="column">
          <button class="footerButton" @click="openEmail">
            <div class="footerInformation"> <p class="blue-text"><i class="fas fa-envelope"></i> Email:</p></div>
            <p class="blue-text">hello@happyhospitality.online</p>
          </button>
          <button class="footerButton" @click="callPhone">
            <div class="footerInformation"> <p class="blue-text"><i class="fas fa-phone"></i> Phone:</p> </div>
            <p class="blue-text">0223 659844</p>
          </button>
        </div>
        <div class="column">
          <button class="footerButton" @click="searchAdress">
            <div class="footerInformation"><p class="blue-text"><i class="fas fa-map-marker-alt"></i> Address:</p></div>
            <p class="blue-text">Neptunusstraat 4, 1131 WJ Volendam</p>
          </button>
        </div>
      </div>
    </div>
    <div class="subscribeForm">
      <p class="white-text">Subscribe to our newsletter</p>
      <form>
        <input type="email" placeholder="Your email address"/>
        <button type="submit" class="subscribeButton">Subscribe</button>
      </form>
    </div>
    <div class="bottomFooter">
      <div class="bottomFooterLeft">
        <span class="yellow-text">© Happy</span> <span class="white-text">Hospitality Collective</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer.vue',
  methods: {
    openEmail () {
      window.location.href = 'mailto:hello@happyhospitality.online'
    },
    callPhone () {
      window.location.href = 'tel:0223659844'
    },
    searchAdress () {
      window.open('https://www.google.com/maps/search/Neptunusstraat+4,+1131+WJ+Volendam')
    },
    goToLinkedIn () {
      window.open('https://www.linkedin.com/company/happy-hospitality-collective')
    },
    goToInstagram () {
      window.open('http://instagram.com/', '_blank')
    }
  }
}
</script>

<style scoped>
.container-footer {
  width: 100%;
  background-color: #1F93A5;
  color: whitesmoke;
}

.bottomFooter {
  align-items: center;
  justify-content: center;
  background-color: #1F93A5;
  height: 30px;
  padding: 15px;
}

.topFooter {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: row;
  background-color: #FECC30;
  margin-top: 70px;
}

.topFooterLeft {
  text-align: left;
  margin-left: 5%;
  margin-top: 10px;
  width: 40%;
}

.topFooterRight {
  width: 60%;
  text-align: left;
  display: flex;
}

.column {
  flex: 1;
  margin-right: 40px; /* Aangepaste marge om kolommen te scheiden */
  margin-top: 10px;
  padding-bottom: 50px;
}

.topFooterRight p {
  margin: 5px 5px 5px 5px; /* Aangepaste marge voor tussenruimte tussen alinea's */
}
.footerInformation {
  margin-top: 15px;
}

.subscribeForm {
  margin-top: 20px;
  margin-left: 20px;
  align-items: center;
  justify-content: center;
}

.subscribeForm p {
  margin-bottom: 10px;
  font-size: 20px;
}

.subscribeForm form {
  display: flex;
}

.subscribeForm input {
  padding: 8px;
  margin-right: 10px;
  width: 25%;
}

.subscribeButton {
  padding: 15px;
  background-color: #1F93A5;
  color: whitesmoke;
  border: none;
  cursor: pointer;
  outline: none;
  font-size: 20px;
}

.subscribeButton:hover {
  background-color: var(--main-color-hover);
}

.social-icons {
  display: flex;
}

.iconButtonHomepage {
  border: none;
  background: none;
  cursor: pointer;
  outline: none;
  padding: 0;
}

.iconButtonHomepage i {
  margin-right: 10px;
  font-size: 1.5rem;
  background-color: #1F93A5;
  color: whitesmoke;
  border-radius: 50%;
  padding: 10px;
}

.iconButtonHomepage:hover {
  transform: scale(1.2);
}

.social-icons i {
  margin-right: 10px;
  margin-bottom: 10px;
  font-size: 1.5rem;
  background-color: #1F93A5;
  color: var(--main-color-hover);
  border-radius: 50%;
  padding: 10px;
}
.footerButton {
  background: none;
  border: none;
  cursor: pointer;
  outline: none;
  font-size: 17px;
  text-align: left;
  line-height: 25px;
  font-family: 'Poppins', sans-serif;
}
.footerButton:hover{
  transform: scale(1.08);
}

.yellow-text {
  color: #FECC32;
}

.blue-text {
  color: #1F93A5;
}

.white-text {
  color: whitesmoke;
}
</style>
