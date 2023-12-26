<script>
export default {
  name: 'ProfileEvents',
  props: ['user'],
  inject: ['eventsService', 'userEventsService', 'userEventsService2'],
  data () {
    return {
      userEvents: [],
      showSignOut: false,
      selectedEventsSignOut: null,
      signInOut: false,
      signOutComplete: false
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
    },
    toggleSignOut (event) {
      this.showSignOut = !this.showSignOut
      if (this.selectedEventsSignOut !== null) {
        this.selectedEventsSignOut = null
      } else {
        this.selectedEventsSignOut = event
      }
    },
    async signOut () {
      this.showSignOut = false
      this.signInOut = true

      // First timeout: Add participant after 10 seconds
      setTimeout(() => {
        this.userEventsService.asyncRemoveEntityFromEntity(this.user.id, this.selectedEventsSignOut.id, 'removeUserFromEvent')
      }, 10000)

      // Second timeout: Hide signInIn and set signInComplete after 10 seconds
      setTimeout(() => {
        this.signInOut = false
        this.signOutComplete = true

        // Third timeout: Reset selectedEventsSignIn after 5 seconds
        setTimeout(() => {
          this.signOutComplete = false
          const index = this.userEvents.findIndex(userEvent => this.selectedEventsSignOut.id === userEvent.id)
          if (index !== -1) {
            this.userEvents.splice(index, 1)
          }
          this.selectedEventsSignOut = null
          this.$forceUpdate()
        }, 3000)
      }, 7000)
    }
  },
  async created () {
    try {
      if (this.user.id !== null) {
        this.userEvents = await this.userEventsService2.asyncFindEventByUser(this.user.id)
      } else {
        const userId = this.$route.params.id
        if (userId) {
          this.user = await this.userEventsService2.asyncFindEventByUser(userId)
          this.selectedCopy = { ...this.user }
          console.log(this.selectedCopy)
        }
      }
    } catch (e) {
      console.error(e)
    }
  }
}
</script>

<template>
  <div>
    <div class="events-user">
      <div class="event" v-for="event in userEvents" :key="event.id">
        <div class="event-left">
          <img :src="require(`../../${event.image}`)" alt="Event Image">
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
          <div class="event-right-bottom">
            <button class="event-sign-out" @click="toggleSignOut(event)">Sign Out</button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="showSignOut" class="sign-in">
      <div class="sign-in-title">
        <h1>Are you sure you want to sign Out?</h1>
      </div>
      <div class="sign-in-buttons">
        <button @click="toggleSignOut">Cancel</button>
        <button @click="signOut">Sign Out</button>
      </div>
    </div>
    <div v-if="signInOut">
      <div class="loading-animation">
        <div class="loadingio-spinner-spinner-p60giii2jcd"><div class="ldio-4bagb6lp0r">
          <div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div>
        </div></div>
      </div>
    </div>
    <div v-if="signOutComplete">
      <div class="sign-in-complete">
        <div class="success-checkmark">
          <div class="check-icon">
            <span class="icon-line line-tip"></span>
            <span class="icon-line line-long"></span>
            <div class="icon-circle"></div>
            <div class="icon-fix"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
