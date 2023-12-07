<script>
export default {
  name: 'UserProfileInfo',
  props: ['user'],
  emits: ['loginAdmin', 'loginUser']
}
</script>

<template>
  <div>
    <h3>Id</h3>
    <p> {{ user.id }} </p>
  </div>
  <div>
    <h3>Email</h3>
    <p> {{ user.mail }} </p>
  </div>
  <div>
    <h3>Age</h3>
    <p> {{ user.age }} </p>
  </div>
  <div>
    <h3>Gender</h3>
    <p> {{ user.gender }} </p>
  </div>
  <div>
    <h3>Company Type</h3>
    <p> {{ user.companyType }} </p>
  </div>
  <div>
    <h3>Company Name</h3>
    <p> {{ user.companyName }} </p>
  </div>
  <div>
    <h3>Postal Code</h3>
    <p> {{ user.postalCode }} </p>
  </div>
  <div>
    <h3>User type</h3>
    <div class="usertype-tag"> {{ user.userType }} </div>
  </div>
</template>

<style scoped>
p, h1 {
  color: black;
  margin: 0;
}
</style>
