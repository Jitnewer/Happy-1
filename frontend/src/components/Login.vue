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
import { mapState } from 'vuex'

export default {
  name: 'login.vue',
  inject: ['sessionSBService'],
  computed: {
    ...mapState(['loggedIn', 'loggedInAsAdmin'])
  },
  data () {
    return {
      email: '',
      password: '',
      signOut: this.$route.query.signOut
    }
  },
  methods: {
    async login () {
      try {
        const user = await this.sessionSBService.asyncSignIn({
          mail: this.email,
          password: this.password
        })
        console.log(user)

        if (user !== null) {
          const userType = user.body.userType

          if (userType === User.userTypes.Admin) {
            this.$store.commit('setLoggedInAsAdmin', true)
            this.$router.push({ path: '/admin' })
            this.$forceUpdate()
          } else if (userType === User.userTypes.SuperUser) {
            this.$store.commit('setLoggedInAsSuperUser', true)
            this.$router.push({ path: '/admin' })
            this.$forceUpdate()
          } else {
            this.$store.commit('setLoggedIn', true)

            this.$router.push({ path: '/home' })
            this.$forceUpdate()
          }
        }
      } catch (e) {
        console.error(e)
      }
    }
  },
  created () {
    if (this.signOut) {
      this.$store.commit('setLoggedInAsAdmin', false)
      this.$store.commit('setLoggedIn', false)
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
}

.container-sign-up {
  width: 330px;
  background: #fff;
  border-radius: 5px;
  text-align: center;
  padding: 50px 35px 10px 35px;
  box-shadow: 0px 3px 40px #000;
}

.header-sign-up {
  font-size: 35px;
  font-weight: 600;
  margin: 0 0 30px 0;
}

.container-sign-up .form-sign-up-1 {
  width: 100%;
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
  outline: none;
  width: 100%;
  padding: 0.5rem;
  font-size: 12px;
  box-shadow: var(--shadow);
  border: none;
}

.sign-up-input:focus-visible {
  outline: none;
}

.field-btn button {
  width: 100%;
  height: 50px;
  margin-bottom: 10px;
  margin-top: -20px;
  border: none;
  background: #0992A4;
  color: #fff;
  font-size: 18px;
  font-weight: 500;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  transition: 0.3s ease;
  box-shadow: var(--shadow);

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
