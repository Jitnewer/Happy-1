<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span> > </span>
      <router-link :to="{ name: 'adminSubscribers' }">Subscribers</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminSubscriberCreate' }">Create</router-link>
    </div>
    <div class="challenge-create">
      <div class="title-button-create">
        <h1>Create Subscriber</h1>
        <button @click="back()"  class="back-button">Back</button>
      </div>
      <div>
        <form @submit.prevent="create" class="challenge-create-form"  v-if="subscriber">
          <div class="form-label">
            <p>Email</p>
            <input type="email" v-model="subscriber.email" :class="{
            'invalid-input': subscriber.email && (!isEmailValid || emailAlreadyExists),
            'valid-input': subscriber.email && isEmailValid && !emailAlreadyExists
          }" @input="validateEmail(subscriber.email)" >
            <p class="errorMessage" v-if="!isEmailValid && subscriber.email">Invalid email</p>
            <p class="errorMessage" v-if="emailAlreadyExists && subscriber.email">Email is already subscribed</p>
          </div>
          <button type="submit" :disabled="!validateEmail(subscriber.email)">Save</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>

import { mapState } from 'vuex'

export default {
  name: 'SubscriberAdminCreate.vue',
  inject: ['subscriberServiceSuperuser'],
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
    validateEmail (email) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      this.isEmailValid = emailRegex.test(email)

      // Reset emailAlreadyExists when the input changes
      if (email !== this.subscriber.emailAlreadyExists) {
        this.emailAlreadyExists = null
      }

      return this.isEmailValid
    },
    async create () {
      try {
        const response = await this.subscriberServiceSuperuser.asyncSave(this.subscriber)
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
        this.$store.commit('setError', true)
        this.$store.commit('setErrorMessage', e.toJSON().error)
        setTimeout(() => {
          this.$store.commit('setError', false)
          this.$store.commit('setErrorMessage', null)
        }, 8000)
        this.emailAlreadyExists = true
        this.subscriber.emailAlreadyExists = this.subscriber.email // Store the email that triggered the error
      }
    },
    back () {
      this.$router.push({ name: 'adminSubscribers' })
    }
  },
  computed: {
    subscribers () {
      return this.subscriberServiceSuperuser.entities
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
