<template>
  <div class="container-admin" v-if="copiedNewsletter">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminNewsletters' }">Newsletter Users</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminNewsletterEdit', query: { id: $route.params.id } }">Edit {{ copiedNewsletter.id }}</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
        <h1>Edit Newsletter</h1>
        <button @click="back()" class="back-button">Back</button>
      </div>
      <div>
        <form @submit.prevent="edit" class="challenge-create-form" v-if="copiedNewsletter">
          <div class="form-label">
            <p>Name</p>
            <input type="email" v-model="copiedNewsletter.email" :class="{
            'invalid-input': copiedNewsletter.email && (!isEmailValid || emailAlreadyExists),
            'valid-input': copiedNewsletter.email && isEmailValid && !emailAlreadyExists
          }" @input="validateEmail(copiedNewsletter.email)" >
            <p class="errorMessage" v-if="!isEmailValid && copiedNewsletter.email">Invalid email</p>
            <p class="errorMessage" v-if="emailAlreadyExists && copiedNewsletter.email">Email is already subscribed</p>
          </div>
          <button type="submit" :disabled="!newsletterEdited || !validateEmail(copiedNewsletter.email) || emailAlreadyExistss">Save</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NewsletterAdminEdit.vue',
  inject: ['newsletterService', 'newsletterServiceSuperuser'],
  data () {
    return {
      filter: this.$route.query.sort,
      copiedNewsletter: null,
      newsletterEdited: false,
      isEmailValid: null,
      isSaved: false,
      emailAlreadyExists: null
    }
  },
  methods: {
    validateEmail (email) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      this.isEmailValid = emailRegex.test(email)

      if (email !== this.newsletter.emailAlreadyExists) {
        this.emailAlreadyExists = null
      }

      return this.isEmailValid
    },
    async edit () {
      if (this.validateEmail(this.newsletter.email)) {
        try {
          await this.newsletterServiceSuperuser.asyncSave(this.copiedNewsletter)
          this.isSaved = true
          this.$router.push({ name: 'adminNewsletters' })
        } catch (e) {
          console.error(e)
          this.emailAlreadyExists = true
          this.newsletter.emailAlreadyExists = this.newsletter.email
        }
      }
    },
    checkIfNewsletterEdited () {
      if (this.newsletter && this.copiedNewsletter) {
        this.newsletterEdited = this.newsletter.email !== this.copiedNewsletter.email
      }
    },
    back () {
      this.$router.push({ name: 'adminNewsletters' })
    },
    async updateNewsletters () {
      try {
        await this.newsletterServiceSuperuser.asyncFindById(this.$route.params.id)
        this.copiedNewsletter = JSON.parse(JSON.stringify(this.newsletter))
      } catch (e) {
        console.error(e)
      }
    }
  },
  computed: {
    newsletter () {
      return this.newsletterServiceSuperuser.entities
    }
  },
  async created () {
    await this.updateNewsletters()
  },
  watch: {
    $route (to, from) {
      if (to.fullPath !== from.fullPath) {
        this.updateNewsletters()
      }
    },
    copiedNewsletter: {
      handler () {
        this.checkIfNewsletterEdited()
      },
      deep: true
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
