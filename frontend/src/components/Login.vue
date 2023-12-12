<template>
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'login' }">Login</router-link>
  </div>
  <div class="sign-up-body">
    <div class="container-sign-up">
      <div class="header-sign-up">Login:</div>
      <div class="form-sign-up-1">
        <form @submit.prevent="login">
          <div class="sign-up-page">
            <div class="title-sign-up-form">Login with your email:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Email:</div>
              <input v-model="email" type="email" class="sign-up-input" required>
            </div>

            <div class="field-sign-up">
              <div class="label-sign-up">Password:</div>
              <input v-model="password" type="password" class="sign-up-input" required>
            </div>

            <div class="field-btn">
              <button type="submit" class="submit">Log in</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { User } from '@/models/user'

export default {
  name: 'login.vue',
  inject: ['loginAndRegisterService'],
  emits: ['loginAdmin', 'loginUser'],
  data () {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    async login () {
      try {
        const user = await this.loginAndRegisterService.asyncLogin(this.email, this.password)
        if (user !== null) {
          console.log(user)
          if (user.userType === User.userTypes.Admin) {
            localStorage.setItem('email', this.email)
            localStorage.setItem('admin', 'true')
            localStorage.setItem('profileId', `${user.id}`)

            this.$emit('loginAdmin')

            this.$router.push({ path: '/admin' })
          } else {
            localStorage.setItem('email', this.email)
            localStorage.setItem('admin', 'false')
            localStorage.setItem('profileId', `${user.id}`)
            this.$router.push({ path: '/home' })
            this.$emit('loginUser')
          }
        }
      } catch (e) {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>
/*Sign up form */
.sign-up-body {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}

.container-sign-up {
  width: 330px;
  background: #fff;
  border-radius: 5px;
  text-align: center;
  padding: 50px 35px 10px 35px;
  box-shadow: 0px 3px 40px #000;
  margin-top: 60px;
}

.header-sign-up {
  font-size: 35px;
  font-weight: 600;
  margin: 0 0 30px 0;
}

.container-sign-up .form-sign-up-1 {
  width: 100%;
  overflow: hidden;
}

.form-sign-up-1 form {
  display: flex;
  width: 400%;
}

.form-sign-up-1 form .sign-up-page {
  width: 25%;
}

.title-sign-up-form {
  text-align: left;
  font-size: 25px;
  font-weight: 500;
}

.field-sign-up {
  height: 45px;
  width: 330px;
  margin: 45px 0;
  display: flex;
  position: relative;
}

.label-sign-up {
  position: absolute;
  top: -30px;
  font-weight: 500;
}

.sign-up-input {
  height: 100%;
  width: 100%;
  border: 1px solid lightgrey;
  border-radius: 5px;
  font-size: 18px;
  padding-left: 15px;
}

.field-btn button {
  width: 100%;
  height: 50px;
  margin-bottom: 10px;
  margin-top: -20px;
  border: none;
  background: #0992A4;
  border-radius: 5px;
  color: #fff;
  font-size: 18px;
  font-weight: 500;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  transition: 0.3s ease;
}

.field-btn button:hover {
  background: #000;
}

.span-sign-up-form-check span{
  font-weight: 500;
  font-size: 17px;
  line-height: 25px;
  position: absolute;
  left: 50%;
  transform: translate(-50%);
}
</style>
