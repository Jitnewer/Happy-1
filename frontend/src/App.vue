<template>
  <nav-bar></nav-bar>
  <log-in></log-in>
</template>

<script>

import NavBar from '@/components/NavBar.vue'
import LogIn from '@/components/Log-in-.vue'

export default {
  name: 'App',
  components: {
    NavBar,
    LogIn
  }
}

</script>
