<template>
  <div class="Slide">
    <transition name="slide">
      <div v-show="isCurrent">
        <slot/>
        <p v-if="slideText">{{ slideText }}</p>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'Slide',
  emits: ['loginAdmin', 'loginUser'],
  props: {
    slideText: String,
    isCurrent: Boolean
  }
}
</script>

<style scoped>
/* Voeg hier je stijlen toe indien nodig */
</style>
