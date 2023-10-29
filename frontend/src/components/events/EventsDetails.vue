<template>
  <div v-if="selectedEvent" class="container">
    <div class="event-main">
      <div class="event-title">
        <button @click="back">Back</button>
        <h1>Event</h1>
      </div>
      <div class="detail-event">
        <div class="detail-event-left">
          <img :src="selectedEvent.image" alt="Image">
        </div>
        <div class="detail-event-right-main">
          <div class="detail-event-right">
            <div class="detail-event-right-left">
              <h1>{{ selectedEvent.name}}</h1>
              <h3>{{ selectedEvent.location}}</h3>
            </div>
            <div class="detail-event-right-right">
              <p>{{ selectedEvent.date }}</p>
              <p>{{ selectedEvent.timeBegin }}-{{ selectedEvent.timeEnd }}</p>
              <p> {{ formattedPrice(selectedEvent) }}</p>
            </div>
          </div>
          <div class="detail-event-right-bottom">
            <h3>Info</h3>
            <p>{{ selectedEvent.info }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EventsDetails.vue',
  props: ['selectedEvent'],
  methods: {
    formattedPrice (event) {
      if (event.price !== null) {
        return `€${event.price.toFixed(2)},-`
      } else {
        return 'N/A'
      }
    },
    back () {
      this.$router.push({ name: 'events' })
    }
  }
}
</script>
