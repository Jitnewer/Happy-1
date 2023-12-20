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
import { ref, onMounted, watch } from 'vue'

export default {
  name: 'Carousel',
  props: {
    autoPlayEnabled: {
      type: Boolean,
      default: true
    },
    slideCount: {
      type: Number,
      required: true
    }
  },
  setup (props) {
    const currentSlide = ref(1)
    const getSlideCount = ref(props.slideCount)
    const timeoutSlide = ref(6000)
    let intervalId

    const nextSlide = () => {
      clearInterval(intervalId)
      if (currentSlide.value === getSlideCount.value) {
        currentSlide.value = 1
        return
      }
      currentSlide.value += 1
      autoPlay()
    }
    const prevSlide = () => {
      clearInterval(intervalId)
      if (currentSlide.value === 1) {
        currentSlide.value = getSlideCount.value
        return
      }
      currentSlide.value -= 1
      autoPlay()
    }
    const goToSlide = (index) => {
      clearInterval(intervalId)
      currentSlide.value = index + 1
      autoPlay()
    }
    const autoPlay = () => {
      intervalId = setInterval(() => {
        if (currentSlide.value === getSlideCount.value) {
          currentSlide.value = 1
        } else {
          nextSlide()
        }
      }, timeoutSlide.value)
    }

    onMounted(() => {
      autoPlay()
    })

    watch(() => props.slideCount, (newCount) => {
      getSlideCount.value = newCount
    })

    return { currentSlide, nextSlide, prevSlide, getSlideCount, goToSlide }
  }
}
</script>

<style scoped>

</style>
