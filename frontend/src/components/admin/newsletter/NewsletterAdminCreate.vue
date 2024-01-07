<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminNewsletters' }">Newsletter Users</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminNewsletterCreate' }">Create</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
        <h1>Create User Newsletter</h1>
        <button @click="back()" class="back-button">Back</button>
      </div>
      <div>
        <form @submit.prevent="create" class="challenge-create-form" v-if="newsletter">
          <div class="form-label">
            <p>Email</p>
            <input type="email" v-model="newsletter.email" :class="{
            'invalid-input': newsletter.email && (!isEmailValid || emailAlreadyExists),
            'valid-input': newsletter.email && isEmailValid && !emailAlreadyExists
          }" @input="validateEmail(newsletter.email)" >
            <p class="errorMessage" v-if="!isEmailValid && newsletter.email">Invalid email</p>
            <p class="errorMessage" v-if="emailAlreadyExists && newsletter.email">Email is already subscribed</p>
          </div>
          <button type="submit" :disabled="!validateEmail(newsletter.email) && emailAlreadyExists">Save</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EventAdminCreate.vue',
  inject: ['newsletterService', 'newsletterServiceSuperuser'],
  data () {
    return {
      newsletter: {
        email: ''
      },
      isEmailValid: null,
      emailAlreadyExists: null
    }
  },
  methods: {
    validateEmail (email) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      this.isEmailValid = emailRegex.test(email)

      // Reset emailAlreadyExists when the input changes
      if (email !== this.newsletter.emailAlreadyExists) {
        this.emailAlreadyExists = null
      }

      return this.isEmailValid
    },
    async create () {
      if (this.validateEmail(this.newsletter.email)) {
        try {
          await this.newsletterService.asyncSave(this.newsletter)
          this.isSaved = true
          this.$router.push({ name: 'adminNewsletters' })
        } catch (e) {
          console.error(e)
          this.emailAlreadyExists = true
          this.newsletter.emailAlreadyExists = this.newsletter.email // Store the email that triggered the error
        }
      }
    },
    back () {
      this.$router.push({ name: 'adminNewsletters' })
    }
  },
  computed: {
    newsletters () {
      return this.newsletterServiceSuperuser.entities
    }
  },
  beforeRouteUpdate (to, from, next) {
    if (!this.isSaved) {
      this.conformationAlert(() => {
        // Continue with the route update
        next()
      }, 'Are you sure you want to leave with unsaved changes?')
    } else {
      next()
    }
  }
}

</script>
