<script>
export default {
  name: 'PageNotFound',
  emits: ['loginAdmin', 'loginUser'],
  data () {
    return {
    }
  },
  created () {
    console.log(this.$route)
  }
}
</script>

<template>
  <div>
    <h1>ERROR</h1>
    <h2>Page not found</h2>
  </div>
</template>

<style scoped>
h1 {
  color: black;
}
</style>
