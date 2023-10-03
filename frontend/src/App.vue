<template>
  <nav-bar></nav-bar>
  <registration-component></registration-component>
</template>

<script>

import NavBar from '@/components/NavBar.vue'
import RegistrationComponent from '@/components/RegistrationComponent.vue'


export default {
  name: 'App',
  components: {
    NavBar,
    RegistrationComponent
  }
}

</script>
