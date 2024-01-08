<template>
  <div v-if="error" class="errorDiv" :class="{'errorLoggedIn': loggedIn}">
    <div class="error-pop-up">
      <p>{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'errorPopUp.vue',
  computed: {
    ...mapState(['error', 'errorMessage', 'loggedIn'])
  }
}
</script>
