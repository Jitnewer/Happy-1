<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminSubscribers' }">Subscribers</router-link>
    </div>
    <div class="challenges-admin">
      <div class="title-button">
        <h1>Subscribers</h1>
        <button @click="create()" class="admin-create">Create</button>
        <button @click="sendNewsletter()" class="admin-create">Send Newsletter</button>
      </div>
      <table v-if="subscribers">
        <thead>
        <tr>
          <th>Id</th>
          <th>Email</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="subscriber in subscribers" :key="subscriber.id">
          <td>{{ subscriber.id }}</td>
          <td>{{ subscriber.email }}</td>
          <td>
            <div class="table-buttons">
              <button class="edit" @click="edit(subscriber.id)">Edit</button>
              <button class="delete" @click="remove(subscriber)">Delete</button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SubscribersAdmin.vue',
  inject: ['subscriberServiceSuperuser'],
  data () {
    return {
    }
  },
  methods: {
    create () {
      this.$router.push({ name: 'adminSubscriberCreate' })
    },
    async sendNewsletter () {
      for (let i = 0; i < this.subscribers.length; i++) {
        try {
          const response = await this.subscriberServiceSuperuser.asyncSendNewsletter(this.subscribers[i])
          this.$store.commit('setSuccess', true)
          this.$store.commit('setSuccessMessage', response.message)
          setTimeout(() => {
            this.$store.commit('setSuccess', false)
            this.$store.commit('setSuccessMessage', null)
          }, 8000)
        } catch (e) {
          console.error(e.toJSON())
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
    async remove (subscriber) {
      try {
        const response = await this.subscriberServiceSuperuser.asyncDeleteById(subscriber.id)
        console.log(response)
        await this.subscriberServiceSuperuser.asyncFindAll()
        this.$store.commit('setSuccess', true)
        this.$store.commit('setSuccessMessage', response.message)
        setTimeout(() => {
          this.$store.commit('setSuccess', false)
          this.$store.commit('setSuccessMessage', null)
        }, 8000)
      } catch (e) {
        console.error(e.toJSON())
        this.error = true
        this.$store.commit('setError', true)
        this.$store.commit('setErrorMessage', e.toJSON().error)
        setTimeout(() => {
          this.$store.commit('setError', false)
          this.$store.commit('setErrorMessage', null)
        }, 8000)
      }
    },
    async updateSubscribers () {
      try {
        await this.subscriberServiceSuperuser.asyncFindAll()
      } catch (e) {
        console.error(e.toJSON())
        this.$store.commit('setError', true)
        this.$store.commit('setErrorMessage', e.toJSON().error)
        setTimeout(() => {
          this.$store.commit('setError', false)
          this.$store.commit('setErrorMessage', null)
        }, 8000)
      }
    },
    edit (id) {
      this.$router.push({ name: 'adminSubscriberEdit', params: { id: id } })
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
    }
  },
  computed: {
    subscribers () {
      return this.subscriberServiceSuperuser.entities
    }
  }
}

</script>
