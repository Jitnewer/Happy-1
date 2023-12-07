<template>
  <div class="carousel">
    <slot :currentSlide="currentSlide"/>

    <div class="navigate">
      <div class="toggle-page left">
        <i @click="prevSlide" class="fa-solid fa-arrow-left" style="color: white;"></i>
      </div>
      <div class="toggle-page right">
        <i @click="nextSlide" class="fa-solid fa-arrow-left fa-rotate-180" style="color: white;"></i>
      </div>
    </div>
    <div class="pagination">
      <span class="carouselPagination" @click="goToSlide(index)" v-for="(slide, index) in getSlideCount"
            :key="index"
            :class="{active : index + 1 === currentSlide}">

      </span>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'

export default {
  name: 'Carousel',
  emits: ['loginAdmin', 'loginUser'],
  setup () {
    const currentSlide = ref(1)
    const getSlideCount = ref(null)
    const autoPlayEnabled = ref(true)
    const timeoutSlide = ref(8000)
    const nextSlide = () => {
      if (currentSlide.value === getSlideCount.value) {
        currentSlide.value = 1
        return
      }
      currentSlide.value += 1
    }
    const prevSlide = () => {
      if (currentSlide.value === 1) {
        currentSlide.value = getSlideCount.value
        return
      }
      currentSlide.value -= 1
    }
    const goToSlide = (index) => {
      currentSlide.value = index + 1
    }
    const autoPlay = () => {
      setInterval(() => {
        nextSlide()
      }, timeoutSlide.value)
    }
    if (autoPlayEnabled.value) {
      autoPlay()
    }
    onMounted(() => {
      getSlideCount.value = document.querySelectorAll('.slide').length
    })

    return { currentSlide, nextSlide, prevSlide, getSlideCount, goToSlide }
  }
}
</script>

<style scoped>

</style>
