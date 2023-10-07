<template>
  <div class="carousel">
    <slot :currentSlide="currentSlide"/>

    <div class="navigate">
      <div class="toggle-page left">
        <i @click="prevSlide" class="fa-solid fa-arrow-left" style="color: #000000;"></i>
      </div>
      <div class="toggle-page right">
        <i @click="nextSlide" class="fa-solid fa-arrow-left fa-rotate-180" style="color: #000000;"></i>
      </div>
      <div class="pagination">
      <span @click="goToSlide(index)" v-for="(slide, index) in getSlideCount"
            :key="index"
            :class="{active : index + 1 === currentSlide}">

      </span>
      </div>
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
  padding: 0 16px;
  height: 100%;
  width: 100%;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
}

.toggle-page {
  display: flex;
  flex: 1;
}

.right {
  justify-content: flex-end;
}

i {
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  background-color: blue;
}
.pagination {
  position: absolute;
  bottom: 24px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
}
 span{
   cursor: pointer;
   width: 20px;
   height: 20px;
   border-radius: 50%;
   background-color: black;
   box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
 }
 .active {
   background-color: blue;
 }
</style>
