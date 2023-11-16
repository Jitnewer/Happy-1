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
        <span class="yellow-text">© Happy</span> <span class="white-text">Hospitality collective </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer.vue'
}
</script>

<style scoped>
.container {
  width: 100%;
}
.bottomFooter{
  align-items: center;
  justify-content: center;
  background-color: #1F93A5;
  height: 30px;
  padding: 15px;
}
.topFooter {
  width: 100%;
  height: 150px;
  display: flex;
  flex-direction: column;
  background-color: #FECC30;
  align-items: flex-start;
  justify-content: flex-start;
  margin-top: 70px;
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
