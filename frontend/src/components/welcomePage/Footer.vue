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
        <div class="columnFooter">
          <button class="footerButton" @click="openEmail">
            <div class="footerInformation"> <p class="blue-text"><i class="fas fa-envelope"></i> Email:</p></div>
            <p class="blue-text">hello@happyhospitality.online</p>
          </button>
          <button class="footerButton" @click="callPhone">
            <div class="footerInformation"> <p class="blue-text"><i class="fas fa-phone"></i> Phone:</p> </div>
            <p class="blue-text">0223 659844</p>
          </button>
        </div>
        <div class="columnFooter">
          <button class="footerButton" @click="searchAdress">
            <div class="footerInformation"><p class="blue-text"><i class="fas fa-map-marker-alt"></i> Address:</p></div>
            <p class="blue-text">Neptunusstraat 4, 1131 WJ Volendam</p>
          </button>
        </div>
      </div>
    </div>
    <div class="subscribeForm">
      <p class="white-text">Subscribe to our subscriber</p>
      <form @submit.prevent="create">
        <div class="flex-form">
        <div class="form-flex">
          <input type="email" placeholder="Your email address" v-model="subscriber.email" :class="{
            'invalid-input': subscriber.email && (!isEmailValid || emailAlreadyExists),
            'valid-input': subscriber.email && isEmailValid && !emailAlreadyExists
          }" @input="validateEmail(subscriber.email)"/>
          <button type="submit" class="subscribeButton" :disabled="!validateEmail(subscriber.email) && emailAlreadyExists">Subscribe</button>
        </div>
        <p class="errorMessage" v-if="!isEmailValid && subscriber.email">Invalid email</p>
        <p class="errorMessage" v-if="emailAlreadyExists && subscriber.email">Email is already subscribed</p>
        </div>
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
import news from '@/components/News.vue'
import ErrorPopUp from '@/components/errorPopUp.vue'
import CustomError from '@/CustomError'

export default {
  name: 'Footer.vue',
  computed: {
    news () {
      return news
    }
  },
  inject: ['subscriberService', 'subscriberServiceSuperuser'],
  data () {
    return {
      subscriber: {
        email: ''
      },
      isEmailValid: null,
      emailAlreadyExists: null
    }
  },
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
    },
    validateEmail (email) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      this.isEmailValid = emailRegex.test(email)

      if (email !== this.subscriber.emailAlreadyExists) {
        this.emailAlreadyExists = null
      }

      return this.isEmailValid
    },
    async create () {
      if (this.isEmailValid) {
        try {
          const response = await this.subscriberService.asyncSave(this.subscriber)
          this.$store.commit('setSuccess', true)
          this.$store.commit('setSuccessMessage', response.message)
          this.subscriber.email = ''
          setTimeout(() => {
            this.$store.commit('setSuccess', false)
            this.$store.commit('setSuccessMessage', null)
          }, 8000)

          window.scrollTo({ top: 0, behavior: 'smooth' })
        } catch (e) {
          if (e instanceof CustomError) {
            console.error(e.toJSON())
            this.emailAlreadyExists = true
            this.subscriber.emailAlreadyExists = this.subscriber.email
            this.$store.commit('setError', true)
            this.$store.commit('setErrorMessage', e.toJSON().error)
            setTimeout(() => {
              this.$store.commit('setError', false)
              this.$store.commit('setErrorMessage', null)
            }, 8000)
          }
          console.error(e)
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
