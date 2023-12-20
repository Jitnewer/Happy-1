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
              <div class="bullet-sign-up-form">
                <span class="span-sign-up-form-check">1</span>
              </div>
              <div class="check-sign-up">&#10003;</div>
            </div>
            <div class="step">
              <p class="p-sign-up-form">Contact</p>
              <div class="bullet-sign-up-form">
                <span class="span-sign-up-form-check">2</span>
              </div>
              <div class="check-sign-up">&#10003;</div>
            </div>
            <div class="step">
              <p class="p-sign-up-form">Birth</p>
              <div class="bullet-sign-up-form">
                <span class="span-sign-up-form-check">3</span>
              </div>
              <div class="check-sign-up">&#10003;</div>
            </div>
            <div class="step">
              <p class="p-sign-up-form">Submit</p>
              <div class="bullet-sign-up-form">
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
            <div class="bullet-sign-up-form">
              <span class="span-sign-up-form-check">1</span>
            </div>
            <div class="check-sign-up">&#10003;</div>
          </div>
          <div class="step">
            <p class="p-sign-up-form">Contact</p>
            <div class="bullet-sign-up-form">
              <span class="span-sign-up-form-check">2</span>
            </div>
            <div class="check-sign-up">&#10003;</div>
          </div>
          <div class="step">
            <p class="p-sign-up-form">Birth</p>
            <div class="bullet-sign-up-form">
              <span class="span-sign-up-form-check">3</span>
            </div>
            <div class="check-sign-up">&#10003;</div>
          </div>
          <div class="step">
            <p class="p-sign-up-form">Submit</p>
            <div class="bullet-sign-up-form">
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

<style scoped>
#input-file-regform{
  display: none;
}
.label-upload-image-regform{
  display: block;
  width: 200px;
  background: #e3362c;
  color: #fff;
  padding: 12px;
  margin: 10px ;
  border-radius: 5px;
  cursor: pointer
;

}
.image-card-regform img{
  width: 90px;
  height: 90px;
  position: absolute;
  right: 10px;

}

.sign-up-input.invalid-input {
  border: 1px solid red;
}

.sign-up-input {
  outline: none;
}
.sign-up-input.invalid-input:focus-visible {
  outline: none
}

.errorMessageRegistrationForm{
  color: red;
  display: flex;
  font-size: smaller;
}
.sign-up-input.valid-input {
  border: 1px solid limegreen;
}

.sign-up-input.valid-input:focus-visible {
  outline: none
}

/* The switch - the box around the slider */
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

/* Hide default HTML checkbox */
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}

/* -----------------------------------------------------------------r */
/*Sign up form */
.sign-up-body {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}

.container-sign-up {
  width: 400px;
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
  margin-bottom: 10px;
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

.select-gender-sign-up {
  width: 100%;
  padding-left: 10px;
  font-size: 17px;
  font-weight: 500;
  border: 1px solid lightgrey;
  border-radius: 5px;
}

.select-gender-sign-up:focus-visible {
  outline: none;
}

.field-btn {
  display: flex;
}

.field-btn button {
  width: 100%;
  height: 60px;
  margin-left: 5px;
  margin-bottom: 10px;
  margin-top: 30px;
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
  background:#FECC32;
}

.prev-1 {
  margin-right: 3px;
  font-size: 17px;
}

.next-1 {
  margin-right: 3px;
  font-size: 17px;
}

.progress-bar-sign-up {
  display: flex;
  margin: 40px 0;
}

.step {
  position: relative;
  text-align: center;
  width: 100%;
}

.p-sign-up-form {
  font-size: 18px;
  font-weight: 500;
  color: #000;
  margin-bottom: 8px;
}

.bullet-sign-up-form {
  position: relative;
  height: 25px;
  width: 25px;
  border: 2px solid #000;
  display: inline-block;
  border-radius: 50%;
  font-weight: 500;
  font-size: 17px;
  line-height: 25px;
}

.span-sign-up-form-check span {
  font-weight: 500;
  font-size: 17px;
  line-height: 25px;
  position: absolute;
  left: 50%;
  transform: translate(-50%);
}

.bullet-sign-up-form:before,
.bullet-sign-up-form:after {
  display: none;
}

.step:last-child .bullet-sign-up-form:before,
.step:last-child.bullet-sign-up-form:after {
  position: absolute;
  content: '';
  bottom: 11px;
  right: -51px;
  height: 3px;
  width: 44px;
  background: #262626;
}

.step .bullet-sign-up-form:after {
  background: #0992A4;
  transform: scaleX(0);
  transform-origin: left;
  animation: animate 0.3s linear forwards;
}

@keyframes animate {
  100% {
    transform: scaleX(1);
  }
}

.check-sign-up {
  position: absolute;
  left: 50%;
  top: 70%;
  font-size: 15px;
  transform: translate(-50%, -50%);
  display: none;
}

.label-sign-upp {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  background: white;
  border-radius: 10px;
}

.h1-sign-up {
  text-align: center;
  padding: 0 0 20px 0;
  border-bottom: 1px solid silver;
  color: black;
}

.options-span {
  position: absolute;
  top: 60px;
  left: 0;
  width: 100%;
  height: 2px;
  background: #0992A4;
}

.button-left-reg, .button-right-reg {
  width: 100%;
  height: 50px;
  border: 1px solid;
  background: #0992A4;
  border-radius: 25px;
  font-size: 18px;
  color: #e9f4fb;
  font-weight: 700;
  cursor: pointer;
  outline: none;
}

.button-right-reg:hover {
  border-color: #0992A4;
  opacity: .5;
  transition: .5s;
}

.button-left-reg:hover {
  border-color: #0992A4;
  opacity: .5;
  transition: .5s;
}

.options-reg {
  margin-top: 20px;
  margin-bottom: 20px;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Poppins', sans-serif;
}

.body {
  display: flex;
  height: 100vh;

  padding: 10px;
  background: white;
}

/*Sign up AS form -  */
.form-sign-up-as-1 form .sign-up-page {
}

/*Sign up AS button -  */
.field-btn-sign-up-as button {
  width: 100%;
  height: 60px;
  margin-top: 50px;
  margin-bottom: 10px;
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

.field-btn-sign-up-as button:hover {
  background: #FECC32;
}

.container-reg {
  max-width: 700px;
  width: 100%;
  background: #FECC32;
  padding: 25px 30px;
  border-radius: 5px;
}

.container-reg .title {
  font-size: 25px;
  font-weight: 500;
  position: relative;
}

.container-reg .title::before {
  content: '';
  position: absolute;
  left: 0;
  bottom: 0;
  height: 3px;
  width: 30px;
  background: #0992a4;
  justify-content: center;
  align-items: center;
}

.container-reg form .user-details {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin: 20px 0 12px 0;
}

form .user-details .input-box {
  margin-bottom: 15px;
  width: calc(100% / 2 - 20px);
}

.user-details .input-box .details {
  display: block;
  font-weight: 500;
  margin-bottom: 5px;
}

.user-details .input-box input {
  height: 45px;
  width: 100%;
  outline: none;
  border-radius: 5px;
  border: 1px solid #ccc;
  padding-left: 15px;
  font-size: 16px;
  border-bottom-width: 2px;
  transition: a11 0.3s ease;
}

.user-details .input-box input:focus,
.user-details .input-box input:valid {
  border-color: #0992A4;
}

form .gender-details .gender-title {
  font-size: 20px;
  font-weight: 500;
}

form .gender-details .gender-category {
  display: flex;
  width: 80%;
  margin: 14px 0;
  justify-content: space-between;
}

.gender-details .gender-category label {
  display: flex;
  align-items: center;
}

.gender-details .gender-category .dot {
  height: 18px;
  width: 18px;
  background: #d9d9d9;
  border-radius: 50%;
  margin-right: 10px;
  border: 5px solid transparent;
  transition: all 0.3s ease;
}

#dot-1:checked ~ .gender-category label .one,
#dot-2:checked ~ .gender-category label .two {
  border-color: #d9d9d9;
  background: black;
}

form input[type="radio"] {
  display: none;
}

form .button {
  height: 45px;
  margin: 45px 0;
}

.button input {
  height: 100%;
  width: 100%;
  outline: none;
  color: #fff;
  border: none;
  font-size: 18px;
  font-weight: 500;
  letter-spacing: 1px;
  border-radius: 5px;
  background: #0992A4;
}

form .button input:hover {
  background: black;
}
</style>
