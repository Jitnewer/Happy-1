<template>
<div class="Slide">
  <transition name="slide">
    <slot/>
  </transition>
</div>
</template>

<script>
export default {
  name: 'Slide'
}
</script>

<style scoped>

</style>
