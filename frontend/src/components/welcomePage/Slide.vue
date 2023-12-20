<template>
  <div class="Slide">
    <transition name="slide">
      <div v-show="isCurrent">
        <slot/>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'Slide',
  props: {
    slideText: String,
    isCurrent: Boolean
  }
}
</script>

<style scoped>

</style>
