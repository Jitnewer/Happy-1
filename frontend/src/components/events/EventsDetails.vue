<template>
  <div class="breadcrum" v-if="event">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'events' }">Events</router-link>
    <p>></p>
    <router-link :to="{ name: 'event', params: { id: this.event.id } }">Event / {{ event.id }}</router-link>
  </div>
  <div v-if="event" class="container">
    <div class="event-main">
      <div class="event-title">
        <button @click="back">Back</button>
        <h1>Event</h1>
      </div>
      <div class="detail-event">
        <div class="detail-event-left">
          <img :src="event.image ? `https://ik.imagekit.io/happy1hva${event.image}` : ''" alt="Image">
        </div>
        <div class="detail-event-right-main">
          <div class="detail-event-right">
            <div class="detail-event-right-left">
              <h1>{{ event.name}}</h1>
              <h3>{{ event.location}}</h3>
            </div>
            <div class="detail-event-right-right" v-if="event.timeBegin && event.timeEnd">
              <p>{{ parseDate(event.date)  }}</p>
              <p>{{ event.timeBegin.slice(0, 5) }} - {{ event.timeEnd.slice(0, 5) }}</p>
              <p> {{ formattedPrice(event) }}</p>
            </div>
          </div>
          <div class="detail-event-right-bottom">
            <h3>Info</h3>
            <p>{{ event.info }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EventsDetails.vue',
  inject: ['eventsService'],
  data () {
    return {
      filter: this.$route.query.sort
    }
  },
  async created () {
    await this.eventsService.asyncFindById(this.$route.params.id)
  },
  methods: {
    formattedPrice (event) {
      if (event.price !== null) {
        return `€${event.price.toFixed(2).replace('.', ',')},-`
      } else {
        return 'N/A'
      }
    },
    parseDate (dateString) {
      const dateObject = new Date(dateString)
      const day = dateObject.getDate()
      const month = dateObject.getMonth() + 1
      const year = dateObject.getFullYear()

      return `${day}-${month}-${year}`
    },
    async back () {
      this.$router.push({ name: 'events', query: { sort: this.filter } })
    }
  },
  computed: {
    event () {
      return this.eventsService.entities
    }
  }
}
</script>
