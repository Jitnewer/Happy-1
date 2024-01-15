<template>
  <div class="carousel">
    <!-- Slot for content, exposing current slide to the parent -->
    <slot :currentSlide="currentSlide" />

    <!-- Navigation arrows for moving to the next and previous slides -->
    <div class="navigate">
      <div class="toggle-page left">
        <i @click="prevSlide" class="fa-solid fa-arrow-left" style="color: white;"></i>
      </div>
      <div class="toggle-page right">
        <i @click="nextSlide" class="fa-solid fa-arrow-left fa-rotate-180" style="color: white;"></i>
      </div>
    </div>

    <!-- Pagination dots for each slide -->
    <div class="pagination">
      <span
        class="carouselPagination"
        @click="goToSlide(index)"
        v-for="(slide, index) in getSlideCount"
        :key="index"
        :class="{ active: index + 1 === currentSlide }"
      ></span>
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
    // Reactive references
    const currentSlide = ref(1)
    const getSlideCount = ref(props.slideCount)
    const timeoutSlide = ref(6000)
    let intervalId

    // Function to move to the next slide
    const nextSlide = () => {
      clearInterval(intervalId)
      if (currentSlide.value === getSlideCount.value) {
        currentSlide.value = 1
        return
      }
      currentSlide.value += 1
      autoPlay()
    }

    // Function to move to the previous slide
    const prevSlide = () => {
      clearInterval(intervalId)
      if (currentSlide.value === 1) {
        currentSlide.value = getSlideCount.value
        return
      }
      currentSlide.value -= 1
      autoPlay()
    }

    // Function to go to a specific slide
    const goToSlide = (index) => {
      clearInterval(intervalId)
      currentSlide.value = index + 1
      autoPlay()
    }

    // Function to start autoplay of the slides
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

    // Watch for changes in the slideCount prop
    watch(() => props.slideCount, (newCount) => {
      getSlideCount.value = newCount
    })

    return { currentSlide, nextSlide, prevSlide, getSlideCount, goToSlide }
  }
}
</script>

<style scoped>

</style>
