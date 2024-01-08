<template>
  <div v-if="success" class="success" :class="{'successLoggedIn': loggedIn}">
    <div class="success-pop-up">
      <p>{{ successMessage }}</p>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'successPopUp.vue',
  computed: {
    ...mapState(['success', 'successMessage', 'loggedIn'])
  }
}
</script>
