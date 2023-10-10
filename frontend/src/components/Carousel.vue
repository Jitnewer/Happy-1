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
      <span @click="goToSlide(index)" v-for="(slide, index) in getSlideCount"
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
  setup () {
    const currentSlide = ref(1)
    const getSlideCount = ref(null)
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
    onMounted(() => {
      getSlideCount.value = document.querySelectorAll('.slide').length
    })

    return { currentSlide, nextSlide, prevSlide, getSlideCount, goToSlide }
  }
}
</script>

<style scoped>
.navigate {
  position: relative;
  display: flex;
  width: auto;
}
.left {
  margin-right: 88%;
}
.toggle-page {
  display: flex;
  flex: 1;
  margin-top: 38%;
}

i {
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  background-color: #1F93A5;;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  position: absolute;
  top: calc(50% + 155px);
  left: 50%;
  transform: translate(-50%, -50%);
}

span {
  cursor: pointer;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: #1F93A5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
}

.active {
  background-color: #FECC32;
}
</style>
