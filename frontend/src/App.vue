<template>
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </nav>
  <router-view/>
  <registration-component/>
</template>

<style>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
font-family: 'Poppins', sans-serif;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
body{
  display: flex;
  height: 100vh;
  justify-content: center;
  align-items: center;
}
.container{
  max-width: 700px;
  width: 100%;
  background: #fff;
}
nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
<script setup>
import RegistrationComponent from '@/components/RegistrationComponent.vue'
</script>
