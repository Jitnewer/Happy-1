<template>
  <div class="container-footer">
    <div class="topFooter">
      <div class="topFooterChild">
      <p class="blue-text">Follow Us</p>
      <p class="white-text">On social media</p>
      </div>
    </div>
    <div class="bottomFooter">
      <div class="bottomFooterChild">
        <span class="yellow-text">© Happy</span> <span class="white-text2">Hospitality collective </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer.vue',
  emits: ['loginAdmin', 'loginUser']
}
</script>

<style scoped>
.container {
  width: 100%;
}

.topFooter p {
  margin-left: 50px;
}

.yellow-text {
  color: #FECC32;
}
.blue-text {
  color: #1F93A5;
}
.white-text {
  color: whitesmoke;
}
</style>
