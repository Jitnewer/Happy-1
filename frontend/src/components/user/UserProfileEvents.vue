<script>
export default {
  name: 'ProfileEvents',
  props: ['user'],
  inject: ['eventsService', 'userEventsService'],
  data () {
    return {
      userEvents: []
    }
  },
  methods: {
    parseDate (dateString) {
      const dateObject = new Date(dateString)
      const day = dateObject.getDate()
      const month = dateObject.getMonth() + 1
      const year = dateObject.getFullYear()

      return `${day}-${month}-${year}`
    },
    formattedPrice (event) {
      if (event.price !== null) {
        return `€${event.price.toFixed(2)},-`
      } else {
        return 'N/A'
      }
    }
  },
  async created () {
    for (let i = 0; i < this.user.userEvents.length; i++) {
      this.userEvents.push(this.user.userEvents[i].event)
    }
  }
}
</script>

<template>
  <div>
    <div class="events-user">
      <div class="event" v-for="event in userEvents" :key="event.id">
        <div class="event-left">
          <img :src="require(`../../assets/images/${event.image}`)" alt="Event Image">
        </div>
        <div class="event-right">
          <div class="event-right-main">
            <div class="event-right-left">
              <h1>{{ event.city }}</h1>
              <h2>{{ event.name }}</h2>
              <h3>{{ event.location }}</h3>
            </div>
            <div class="event-right-right">
              <p>{{ parseDate(event.date)  }}</p>
              <p>{{ event.timeBegin.slice(0, 5) }} - {{ event.timeEnd.slice(0, 5) }}</p>
              <p> {{ formattedPrice(event) }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
