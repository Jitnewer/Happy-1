<template>
  <div class="container-admin">
    <div class="breadcrum-admin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <span>></span>
      <router-link :to="{ name: 'adminNewsletters' }">Newsletter Users</router-link>
    </div>
    <div class="challenges-admin">
      <div class="title-button">
        <h1>Newsletter Users</h1>
        <button @click="create()" class="admin-create">Create</button>
      </div>
      <table v-if="newsletters">
        <thead>
        <tr>
          <th>Id</th>
          <th>Email</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="newsletter in newsletters" :key="newsletter.id">
          <td>{{ newsletter.id }}</td>
          <td>{{ newsletter.email }}</td>
          <td>
            <div class="table-buttons">
              <button class="edit" @click="edit(newsletter.id)">Edit</button>
              <button class="delete" @click="remove(newsletter)">Delete</button>
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
  name: 'NewslettersAdmin.vue',
  inject: ['newsletterServiceSuperuser'],
  data () {
    return {
    }
  },
  methods: {
    create () {
      this.$router.push({ name: 'adminNewsletterCreate' })
    },
    async remove (newsletter) {
      try {
        await this.newsletterServiceSuperuser.asyncDeleteById(newsletter.id)
        await this.newsletterServiceSuperuser.asyncFindAll()
      } catch (e) {
        console.error(e)
      }
    },
    async updateNewsletters () {
      try {
        await this.newsletterServiceSuperuser.asyncFindAll()
      } catch (e) {
        console.error(e)
      }
    },
    edit (id) {
      this.$router.push({ name: 'adminNewsletterEdit', params: { id: id } })
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
    }
  },
  computed: {
    newsletters () {
      return this.newsletterServiceSuperuser.entities
    }
  }
}

</script>
