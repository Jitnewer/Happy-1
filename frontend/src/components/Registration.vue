<template>
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'register' }">Register</router-link>
  </div>
  <div class="sign-up-body">
    <div class="container-sign-up">
      <div class="header-sign-up">Sign Up</div>
      <div class="form-sign-up-as-1">
        <label class="switch">
          <input type="checkbox" @click="toggleChecked">
          <span class="slider round"></span>
        </label>
        <div v-if="generalPartner">
          <div class="header-sign-up">General Partner</div>
          <div class="progress-bar-sign-up">
            <div class="step">
              <p class="p-sign-up-form">Name</p>
              <div class="bullet-sign-up-form" :class="{'check-green': currentForm >1,'check-yellow': currentForm ===1}">
                <span class="span-sign-up-form-check">1</span>
              </div>
              <div class="check-sign-up">&#10003;</div>
            </div>
            <div class="step">
              <p class="p-sign-up-form">Contact</p>
              <div class="bullet-sign-up-form" :class="{'check-green': currentForm >2,'check-yellow': currentForm ===2,'check-red': currentForm ===1}">
                <span class="span-sign-up-form-check">2</span>
              </div>
              <div class="check-sign-up">&#10003;</div>
            </div>
            <div class="step">
              <p class="p-sign-up-form">Birth</p>
              <div class="bullet-sign-up-form" :class="{'check-green': currentForm >3,'check-yellow': currentForm ===3,'check-red': currentForm ===1}">
                <span class="span-sign-up-form-check">3</span>
              </div>
              <div class="check-sign-up">&#10003;</div>
            </div>
            <div class="step">
              <p class="p-sign-up-form">Submit</p>
              <div class="bullet-sign-up-form" :class="{'check-green': currentForm >4,'check-yellow': currentForm ===4,'check-red': currentForm ===1}">
                <span class="span-sign-up-form-check">4</span>
              </div>
              <div class="check-sign-up">&#10003;</div>
            </div>
          </div>
          <form class="form-sign-up-1" v-if="currentForm ===1" @submit.prevent="goToNextForm">
            <div class="sign-up-page">
              <div class="title-sign-up-form">Basic Info:</div>
              <div class="field-sign-up" id="formValidation1">
                <div class="label-sign-up">Company Name:</div>
                <input type="text" class="sign-up-input" required v-model="user.companyName" :class="{'invalid-input': user.companyName && !isCompanyNameValid,'valid-input': user.companyName && isCompanyNameValid}"
                       @input="validateCompanyName(user.companyName)"
                />
              </div>
              <div class="errorMessageRegistrationForm" v-if="!isCompanyNameValid && user.companyName">Invalid company name</div>
              <div class="field-sign-up">
                <div class="label-sign-up">First Name:</div>
                <input type="text" class="sign-up-input" required v-model="user.firstname" :class="{'invalid-input': user.firstname && !isFirstnameValid, 'valid-input': user.firstname && isFirstnameValid }"
                       @input="validateFirstName(user.firstname)"/>
              </div>
              <div class="errorMessageRegistrationForm" v-if="!isFirstnameValid && user.firstname">Invalid first name</div>
              <div class="field-sign-up">
                <div class="label-sign-up">Last Name:</div>
                <input type="text" class="sign-up-input" required v-model="user.lastname" :class="{'invalid-input': user.lastname && !isLastnameValid, 'valid-input': user.lastname && isLastnameValid }"
                       @input="validateLastName(user.lastname)"/>
              </div>
              <div class="errorMessageRegistrationForm" v-if="!isLastnameValid && user.lastname">Invalid first name</div>
              <div class="field-btn">
                <button type="submit">Next</button>
              </div>
            </div>
          </form>

          <form class="sign-up-page" id="user_detail2" v-if="currentForm ===2">
            <div class="title-sign-up-form">Contact Info:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Type of industry:</div>
              <select class="select-gender-sign-up" v-model="user.companyType">
                <option>Catering</option>
                <option>Marketing</option>
                <option>Finance</option>
              </select>
            </div>
            <div class="field-sign-up">
              <div class="label-sign-up">Postal code:</div>
              <input type="text" class="sign-up-input" required v-model="user.postalCode" :class="{'invalid-input': user.postalCode && !isPostalCodeValid,'valid-input': user.postalCode && isPostalCodeValid}"
                     @input="validatePostalCode(user.postalCode)">
            </div>
            <div class="errorMessageRegistrationForm" v-if="!isPostalCodeValid && user.postalCode">Please enter a valid Dutch postal code</div>
            <div class="field-btn">
              <button class="prev-1" @click="goToPrevForm">Previous</button>
              <button class="next-1" @click.prevent="goToNextForm">Next</button>
            </div>
          </form>
          <form class="sign-up-page" id="user_detail3" v-if="currentForm ===3">
            <div class="title-sign-up-form">Date of Birth:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Age:</div>
              <input type="number" class="sign-up-input" v-model="user.age" :class="{'invalid-input': user.age && !isAgeValid,'valid-input': user.age && isAgeValid}"
                     @input="validateAge(user.age)">
            </div>
            <div class="errorMessageRegistrationForm" v-if="!isAgeValid && user.age">Please enter a valid age. You must be 18 or older </div>

            <div class="field-sign-up">
              <div class="label-sign-up">Gender:</div>
              <select class="select-gender-sign-up" v-model="user.gender">
                <option>Male</option>
                <option>Female</option>
                <option>Prefer not to tell</option>
              </select>
            </div>
            <div class="field-btn">
              <button class="prev-2" @click="goToPrevForm">Previous</button>
              <button class="next-2" @click.prevent="goToNextForm">Next</button>
            </div>
          </form>
<form class="sign-up-page" v-if="currentForm ===4">
            <div class="title-sign-up-form">Login Details:</div>
<div class="field-sign-up">
  <div class="label-sign-up">Email Address:</div>
  <input type="text" class="sign-up-input" v-model="user.mail" required :class="{'invalid-input': user.mail && !isEmailValid, 'valid-input': user.mail && isEmailValid }"
         @input="validateEmail(user.mail)"/>
</div>
  <div class="errorMessageRegistrationForm" v-if="!isEmailValid && user.mail">Please enter a valid emailaddress</div>
  <div class="field-sign-up">
<div class="label-sign-up">Password:</div>
              <input type="password" class="sign-up-input" required v-model="user.password" :class="{'invalid-input': user.password && !isPasswordValid, 'valid-input': user.password && isPasswordValid }"
                     @input="validatePassword(user.password)"/>
            </div>
            <div class="errorMessageRegistrationForm" v-if="!isPasswordValid && user.password">Must contain at least one  number and one uppercase and lowercase letter,
              and at least 8 or more characters</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Confirm password:</div>
              <input type="password" class="sign-up-input" required v-model="confirmedPassword" :class="{'invalid-input': confirmedPassword && !isConfirmedPasswordValid, 'valid-input':confirmedPassword && isConfirmedPasswordValid }"
                     @input="validateConfirmedPassword(confirmedPassword)"/>
            </div>
  <div class="errorMessageRegistrationForm" v-if="!isConfirmedPasswordValid && confirmedPassword">Password must be the same</div>
            <div class="field-btn">
              <button class="prev-3" @click="goToPrevForm">Previous</button>
              <button class="submit" @click="sendForm">Submit</button>
            </div>
          </form>
        </div>
      </div>
      <div v-if="entrepreneur">
        <div class="header-sign-up">Entrepreneur</div>
        <div class="progress-bar-sign-up">
          <div class="step">
            <p class="p-sign-up-form">Name</p>
            <div class="bullet-sign-up-form" :class="{'check-green': currentForm >1,'check-yellow': currentForm ===1}">
              <span class="span-sign-up-form-check">1</span>
            </div>
            <div class="check-sign-up">&#10003;</div>
          </div>
          <div class="step">
            <p class="p-sign-up-form">Contact</p>
            <div class="bullet-sign-up-form" :class="{'check-green': currentForm >2,'check-yellow': currentForm ===2,'check-red': currentForm ===1}">
              <span class="span-sign-up-form-check">2</span>
            </div>
            <div class="check-sign-up">&#10003;</div>
          </div>
          <div class="step">
            <p class="p-sign-up-form">Birth</p>
            <div class="bullet-sign-up-form" :class="{'check-green': currentForm >3,'check-yellow': currentForm ===3,'check-red': currentForm ===1}">
              <span class="span-sign-up-form-check">3</span>
            </div>
            <div class="check-sign-up">&#10003;</div>
          </div>
          <div class="step">
            <p class="p-sign-up-form">Submit</p>
            <div class="bullet-sign-up-form" :class="{'check-green': currentForm >4,'check-yellow': currentForm ===4,'check-red': currentForm ===1}">
              <span class="span-sign-up-form-check">4</span>
            </div>
            <div class="check-sign-up">&#10003;</div>
          </div>
        </div>
        <form class="form-sign-up-1" v-if="this.currentEntrepreneurForm === 1">
          <div class="sign-up-page">
            <div class="title-sign-up-form">Basic Info:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">First Name:</div>
              <input type="text" class="sign-up-input" required v-model="user.firstname" :class="{'invalid-input': user.firstname && !isFirstnameValid, 'valid-input': user.firstname && isFirstnameValid }"
                     @input="validateFirstName(user.firstname)"/>
            </div>
            <div class="errorMessageRegistrationForm" v-if="!isFirstnameValid && user.firstname">Invalid first name</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Last Name:</div>
              <input type="text" class="sign-up-input" required v-model="user.lastname" :class="{'invalid-input': user.lastname && !isLastnameValid, 'valid-input': user.lastname && isLastnameValid }"
                     @input="validateLastName(user.lastname)"/>
            </div>
            <div class="errorMessageRegistrationForm" v-if="!isLastnameValid && user.lastname">Invalid first name</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Type of industry:</div>
              <select class="select-gender-sign-up" v-model="user.userType">
                <option>Horeca</option>
                <option>Bar</option>
                <option>Hotel</option>
              </select>
            </div>
            <div class="field-btn">
              <button @click.prevent="goToNextFormEntrepreneur">Next</button>
            </div>
          </div>
        </form>

        <form class="sign-up-page" id="user_detail2" v-if="this.currentEntrepreneurForm === 2">
          <div class="title-sign-up-form">Contact Info:</div>
          <div class="field-sign-up">
            <div class="label-sign-up">Postal code:</div>
            <input type="text" class="sign-up-input" required v-model="user.postalCode" :class="{'invalid-input': user.postalCode && !isPostalCodeValid,'valid-input': user.postalCode && isPostalCodeValid}"
                   @input="validatePostalCode(user.postalCode)">
          </div>
          <div class="errorMessageRegistrationForm" v-if="!isPostalCodeValid && user.postalCode">Please enter a valid Dutch postal code</div>
          <form class="field-sign-up">
            <div class="label-sign-up">Gender:</div>
            <select class="select-gender-sign-up" v-model="user.gender">
              <option>Male</option>
              <option>Female</option>
              <option>Prefer not to tell</option>
            </select>
          </form>
          <div class="field-btn">
            <button class="prev-1" @click="goToPrevFormEntrepreneur">Previous</button>
            <button class="next-1" @click.prevent="goToNextFormEntrepreneur">Next</button>
          </div>
        </form>
        <form class="sign-up-page" id="user_detail3" v-if="this.currentEntrepreneurForm === 3">
          <div class="title-sign-up-form">Date of Birth:</div>
          <div class="field-sign-up">
            <div class="label-sign-up">Age:</div>
            <input type="number" class="sign-up-input" v-model="user.age" :class="{'invalid-input': user.age && !isAgeValid,'valid-input': user.age && isAgeValid}"
                   @input="validateAge(user.age)">
          </div>
          <div class="errorMessageRegistrationForm" v-if="!isAgeValid && user.age">Please enter a valid age. You must be 18 or older </div>

          <div class="field-btn">
            <button class="prev-2" @click="goToPrevFormEntrepreneur">Previous</button>
            <button class="next-2" @click.prevent="goToNextFormEntrepreneur">Next</button>
          </div>
        </form>

        <form class="sign-up-page" v-if="this.currentEntrepreneurForm === 4">
          <div class="title-sign-up-form">Login Details:</div>
          <div class="field-sign-up">
            <div class="label-sign-up">Email Address:</div>
            <input type="email" class="sign-up-input" v-model="user.mail" required :class="{'invalid-input': user.mail && !isEmailValid, 'valid-input': user.mail && isEmailValid }"
                   @input="validateEmail(user.mail)"/>
          </div>
          <div class="errorMessageRegistrationForm" v-if="!isEmailValid && user.mail">Please enter a valid emailaddress</div>
          <div class="field-sign-up">
            <div class="label-sign-up">Password:</div>
            <input type="password" class="sign-up-input" v-model="user.password" required :class="{'invalid-input': user.password && !isPasswordValid, 'valid-input': user.password && isPasswordValid }"
                   @input="validatePassword(user.password)"/>
          </div>
          <div class="errorMessageRegistrationForm" v-if="!isPasswordValid && user.password">Must contain at least one  number and one uppercase and lowercase letter,</div>
          <div class="field-sign-up">
            <div class="label-sign-up">Confirm password:</div>
            <input type="password" class="sign-up-input" required :class="{'invalid-input': confirmedPassword && !isConfirmedPasswordValid, 'valid-input':confirmedPassword && isConfirmedPasswordValid }"
                   @input="validateConfirmedPassword(confirmedPassword)"/>
          </div>
          <div class="errorMessageRegistrationForm" v-if="!isConfirmedPasswordValid && confirmedPassword">Password must be the same</div>
          <div class="field-btn">
            <button class="prev-3" @click="goToPrevFormEntrepreneur">Previous</button>
            <button class="submit" @click="sendForm">Submit</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'registration.vue',
  inject: ['sessionSBService'],
  data () {
    return {
      entrepreneur: false,
      generalPartner: true,
      sliderValue: 0,
      currentForm: 1,
      currentEntrepreneurForm: 1,
      // GeneralPartner
      user: {
        companyName: '',
        profilePic: null,
        firstname: '',
        lastname: '',
        gender: '',
        mail: '',
        userType: '',
        tag: '',
        postalCode: '',
        password: '',
        age: '',
        status: 'ACTIVE',
        companyType: ''
      },
      confirmedPassword: '',
      // Validation GP forms
      isEmailValid: null,
      isCompanyNameValid: null,
      isContactNameGPValid: null,
      isPostalCodeValid: null,
      isUsernameValid: null,
      isPasswordValid: null,
      // validation EP forms
      isFirstnameValid: null,
      isLastnameValid: null,
      isAgeValid: null,
      isConfirmedPasswordValid: null
    }
  },
  methods: {
    toggleChecked (e) {
      this.sliderValue = e.target.checked
      console.log(this.sliderValue)
      console.log('Dit is de waarde van partner: ' + this.generalPartner)
      console.log('Dit is de waarde van entrepreneur: ' + this.entrepreneur)
    },
    goToPrevForm () {
      this.currentForm--
    },
    goToNextForm () {
      console.log(this.currentForm)
      if (this.currentForm === 1) {
        if (this.validateForm1()) {
          this.currentForm = 2
        }
      }
      if (this.currentForm === 2) {
        if (this.validateForm2()) {
          this.currentForm = 3
        }
      }
      if (this.currentForm === 3) {
        if (this.validateForm3()) {
          this.currentForm = 4
        }
      }
      if (this.currentForm === 4) {
        if (this.validateForm4()) {
          this.$router.push({ name: 'welcome' })
        }
      }
    },
    goToPrevFormEntrepreneur () {
      this.currentEntrepreneurForm--
    },
    goToNextFormEntrepreneur () {
      this.currentEntrepreneurForm++
    },
    async sendForm () {
      try {
        if (this.generalPartner) {
          this.user.userType = 'PARTNER'
          this.user.tag = 'Chain-Partner'
        }
        if (this.entrepreneur) {
          this.user.userType = 'ENTREPRENEUR'
          this.user.tag = 'N/A'
        }
        console.log(this.user)
        await this.sessionSBService.asyncRegister(this.user)
        this.$router.push({ path: '/login' })
      } catch (e) {
        console.log(e)
      }
    },
    validateCompanyName (companyName) {
      // Regular expression for validating company name with max length of 40
      const companyNameRegex = /^[a-zA-Z0-9\s]{1,40}$/
      console.log(companyNameRegex.test(companyName))
      // Test the company name against the regex
      this.isCompanyNameValid = companyNameRegex.test(companyName)
      return this.isCompanyNameValid
    },
    validateFirstName (firstName) {
      const firstNameRegex = /^[A-Za-z]+$/
      console.log(firstNameRegex.test(firstName))
      this.isFirstnameValid = firstNameRegex.test(firstName)
      return this.isFirstnameValid
    },
    validateLastName (lastName) {
      const lastNameRegex = /^[A-Za-z]+$/
      console.log(lastNameRegex.test(lastName))
      this.isLastnameValid = lastNameRegex.test(lastName)
      return this.isLastnameValid
    },
    validatePostalCode (postalCode) {
      const postalCodeRegex = /^[1-9][0-9]{3}\s?[a-zA-Z]{2}$/
      console.log(postalCodeRegex.test(postalCode))
      this.isPostalCodeValid = postalCodeRegex.test(postalCode)
      return this.isPostalCodeValid
    },
    validateAge (age) {
      const ageRegex = /^(1[8-9]|[2-9][0-9]|100)$/
      console.log(ageRegex.test(age))
      this.isAgeValid = ageRegex.test(age)
      return this.isAgeValid
    },
    validateEmail (mail) {
      const emailRegex = /[a-z0-9._%+]+@[a-z0-9.]+\.[a-z]{2,}$/
      console.log(emailRegex.test(mail))
      this.isEmailValid = emailRegex.test(mail)
      return this.isEmailValid
    },
    validatePassword (password) {
      const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/
      console.log(passwordRegex.test(password))
      this.isPasswordValid = passwordRegex.test(password)
      return this.isPasswordValid
    },
    validateConfirmedPassword (confirmedPassword) {
      this.isConfirmedPasswordValid = (this.user.password === confirmedPassword)

      return this.isConfirmedPasswordValid
    },
    validateForm1 () {
      // Validate the company name
      const isCompanyNameValid = this.validateCompanyName(this.user.companyName)
      const isFirstNameValid = this.validateFirstName(this.user.firstname)
      const isLastNameValid = this.validateLastName(this.user.lastname)
      // Return true only if all validations pass
      return isCompanyNameValid && isFirstNameValid && isLastNameValid
    },
    validateForm2 () {
      // Validate the company name
      const isPostalCodeValid = this.validatePostalCode(this.user.postalCode)
      // Return true only if all validations pass
      return isPostalCodeValid
    },
    validateForm3 () {
      // Validate the company name
      const isAgeValid = this.validateAge(this.user.age)
      // Return true only if all validations pass
      return isAgeValid
    },
    validateForm4 () {
      // Validate the company name
      const isEmailValid = this.validateEmail(this.user.mail)
      const isPasswordValid = this.validatePassword(this.user.password)
      const isConfirmedPasswordValid = this.validateConfirmedPassword(this.confirmedPassword)

      // Return true only if all validations pass
      return isEmailValid && isPasswordValid && isConfirmedPasswordValid
    }
  },
  watch: {
    sliderValue (value) {
      console.log(value)
      if (value) {
        this.generalPartner = false
        this.entrepreneur = true
      } else {
        this.entrepreneur = false
        this.generalPartner = true
      }
    }
  }
}
</script>
