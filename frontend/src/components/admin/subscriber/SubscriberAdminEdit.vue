<template>
  <div class="container-admin" v-if="copiedSubscriber">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminSubscribers' }">Subscribers</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminSubscriberEdit', query: { id: $route.params.id } }">Edit {{ copiedSubscriber.id }}</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
        <h1>Edit Subscriber</h1>
        <button @click="back()"  class="back-button">Back</button>
      </div>
      <div>
        <form @submit.prevent="edit" class="challenge-create-form" v-if="copiedSubscriber">
          <div class="form-label">
            <p>Name</p>
            <input type="email" v-model="copiedSubscriber.email" :class="{
            'invalid-input': copiedSubscriber.email && (!isEmailValid || emailAlreadyExists),
            'valid-input': copiedSubscriber.email && isEmailValid && !emailAlreadyExists
          }" @input="validateEmail(copiedSubscriber.email)" >
            <p class="errorMessage" v-if="!isEmailValid && copiedSubscriber.email">Invalid email</p>
            <p class="errorMessage" v-if="emailAlreadyExists && copiedSubscriber.email">Email is already subscribed</p>
          </div>
          <button type="submit" :disabled="!subscriberEdited || !validateEmail(copiedSubscriber.email) || emailAlreadyExistss">Save</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'SubscriberAdminEdit.vue',
  inject: ['subscriberServiceSuperuser'],
  data () {
    return {
      filter: this.$route.query.sort,
      copiedSubscriber: null,
      subscriberEdited: false,
      isEmailValid: null,
      isSaved: false,
      emailAlreadyExists: null,
      error: false,
      errorMessage: null
    }
  },
  methods: {
    validateEmail (email) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      this.isEmailValid = emailRegex.test(email)

      if (email !== this.subscriber.emailAlreadyExists) {
        this.emailAlreadyExists = null
      }

      return this.isEmailValid
    },
    async edit () {
      if (this.validateEmail(this.subscriber.email)) {
        try {
          const response = await this.subscriberServiceSuperuser.asyncSave(this.copiedSubscriber)
          this.isSaved = true
          this.$store.commit('setSuccess', true)
          this.$store.commit('setSuccessMessage', response.message)
          setTimeout(() => {
            this.$store.commit('setSuccess', false)
            this.$store.commit('setSuccessMessage', null)
          }, 8000)
          this.$router.push({ name: 'adminSubscribers' })
        } catch (e) {
          console.error(e.toJSON())
          this.emailAlreadyExists = true
          this.subscriber.emailAlreadyExists = this.subscriber.email
          this.error = true
          this.$store.commit('setError', true)
          this.$store.commit('setErrorMessage', e.toJSON().error)
          setTimeout(() => {
            this.$store.commit('setError', false)
            this.$store.commit('setErrorMessage', null)
          }, 8000)
        }
      }
    },
    checkIfSubscriberEdited () {
      if (this.subscriber && this.copiedSubscriber) {
        this.subscriberEdited = this.subscriber.email !== this.copiedSubscriber.email
      }
    },
    back () {
      this.$router.push({ name: 'adminSubscribers' })
    },
    async updateSubscribers () {
      try {
        await this.subscriberServiceSuperuser.asyncFindById(this.$route.params.id)
        this.copiedSubscriber = JSON.parse(JSON.stringify(this.subscriber))
      } catch (e) {
        console.error(e.toJSON())
        this.$store.commit('setError', true)
        this.$store.commit('setErrorMessage', e.toJSON().error)
        setTimeout(() => {
          this.$store.commit('setError', false)
          this.$store.commit('setErrorMessage', null)
        }, 8000)
      }
    }
  },
  computed: {
    subscriber () {
      return this.subscriberServiceSuperuser.entities
    }
  },
  async created () {
    await this.updateSubscribers()
  },
  watch: {
    $route (to, from) {
      if (to.fullPath !== from.fullPath) {
        this.updateSubscribers()
      }
    },
    copiedSubscriber: {
      handler () {
        this.checkIfSubscriberEdited()
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
