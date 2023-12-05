<script>
export default {
  name: 'registration.vue',
  inject: ['loginAndRegisterService'],
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
        profilePic: '',
        firstname: '',
        lastname: '',
        gender: '',
        mail: '',
        userType: '',
        tag: '',
        postalCode: '',
        password: '',
        age: '',
        status: '',
        companyType: ''
      },
      // Validation GP forms
      isEmailGPValid: true,
      isCompanyNameGPValid: true,
      isContactNameGPValid: true,
      isPostalCodeGPValid: true,
      isUsernameGPValid: true,
      isPasswordGPValid: true,
      // validation EP forms
      isFirstnameEPValid: true,
      isLastnameEPValid: true,
      isEmailEPValid: true,
      isPostalCodeEPValid: true,
      isUsernameEPValid: true,
      isPasswordEPValid: true
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
      // Validate the form before moving to the next step
      if (this.validateFormGP1()) {
        this.currentForm++
      } if (this.validateFormGP2()) {
        this.currentForm++
      } if (this.validateFormGP4()) {
        this.currentForm++
      } if (this.validateFormEP1()) {
        this.currentForm++
      } if (this.validateFormEP2()) {
        this.currentForm++
      } if (this.validateFormEP4()) {
        this.currentForm++
      }
    },
    goToPrevFormEntrepreneur () {
      this.currentEntrepreneurForm--
    },
    goToNextFormEntrepreneur () {
      this.currentEntrepreneurForm++
    },
    async sendForm () {
      await this.loginAndRegisterService.asyncRegister(this.user)
    },
    validateFormGP1 () {
      // Add regex validation for each field
      if (!/[a-z0-9._%+]+@[a-z0-9.]+\.[a-z]{2,}$/.test(this.user.mail)) {
        this.isEmailGPValid = false
        return false
      }
      // Add similar validations for other fields
      return true // If all validations pass
    },
    validateFormGP2 () {
      // Add regex validation for each field
      if (!/^[1-9][0-9]{3}\s?[a-zA-Z]{2}$/.test(this.user.postalCode)) {
        this.isPostalCodeGPValid = false
        return false
      }
      // Add similar validations for other fields
      return true // If all validations pass
    },
    validateFormGP4 () {
      // Add regex validation for each field
      if (!/.{8,}$/.test(this.user.mail)) {
        this.isUsernameGPValid = false
        return false
      }
      if (!/ (?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/.test(this.user.password)) {
        this.isPasswordGPValid = false
        return false
      }
      // Add similar validations for other fields
      return true // If all validations pass
    },
    validateFormEP1 () {
      // Add regex validation for each field
      if (!/^[A-Za-z]+$/.test(this.user.firstname)) {
        this.isFirstnameEPValid = false
        return false
      }
      if (!/^[A-Za-z]+$/.test(this.user.lastname)) {
        this.isLastnameEPValid = false
        return false
      }
      if (!/[a-z0-9._%+]+@[a-z0-9.]+\.[a-z]{2,}$/.test(this.user.mail)) {
        this.isEmailEPValid = false
        return false
      }
      // Add similar validations for other fields
      return true // If all validations pass
    },
    validateFormEP2 () {
      // Add regex validation for each field
      if (!/^[1-9][0-9]{3}\s?[a-zA-Z]{2}$/.test(this.user.postalCode)) {
        this.isPostalCodeEPValid = false
        return false
      }
      // Add similar validations for other fields
      return true // If all validations pass
    },
    validateFormEP4 () {
      // Add regex validation for each field
      if (!/^[a-zA-Z0-9_]{3,20}$/.test(this.user.mail)) {
        this.isUsernameEPValid = false
        return false
      }
      if (!/ (?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/.test(this.user.password)) {
        this.isPasswordEPValid = false
        return false
      }
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
  <div class="sign-up-body">
    <div class="container-sign-up">
      <div class="header-sign-up">Sign Up</div>
      <div class="form-sign-up-as-1">
        <form action="#">
          <!--              <div class="sign-up-page">-->
          <!--                <div class="title-sign-up-form">Here you can sign up as:</div>-->
          <!--                <div class="field-sign-up">-->
          <!--                <div class="field-btn-sign-up-as">-->
          <!--                  <button>Entrepreneur</button>-->
          <!--                  <button>General Partner</button>-->
          <!--                </div>-->
          <!--                </div>-->
          <!--              </div>-->
        </form>
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
              <p class="p-sign-up-form">Submit</p>
              <div class="bullet-sign-up-form">
                <span class="span-sign-up-form-check">4</span>
              </div>
              <div class="check-sign-up">&#10003;</div>
            </div>
          </div>
          <div class="form-sign-up-1" v-if="currentForm ===1">
            <div class="sign-up-page">
              <div class="title-sign-up-form">Basic Info:</div>
              <div class="field-sign-up" id="formValidation1">
                <div class="label-sign-up">Company Name:</div>
                <input type="text" class="sign-up-input" required v-model="user.companyName">
              </div>
              <div class="errorMessageRegistrationForm" v-if="!isCompanyNameGPValid">Company name must contain no more than 8 characters</div>
              <div class="field-sign-up">
                <div class="label-sign-up">First Name:</div>
                <input type="text" class="sign-up-input" required v-model="user.firstname">
              </div>
              <div class="errorMessageRegistrationForm" v-if="!isContactNameGPValid">Firstname must contain no more than 8 characters</div>
              <div class="field-sign-up">
                <div class="label-sign-up">Last Name:</div>
                <input type="text" class="sign-up-input" required v-model="user.lastname">
              </div>
              <div class="errorMessageRegistrationForm" v-if="!isContactNameGPValid">Lastname must contain no more than 8 characters</div>
              <div class="field-sign-up">
                <div class="label-sign-up">Gender:</div>
                <select class="select-gender-sign-up" v-model="user.gender">
                  <option>Male</option>
                  <option>Female</option>
                  <option>Prefer not to tell</option>
                </select>
              </div>
              <div class="field-sign-up">
                <div class="label-sign-up">Email Address:</div>
                <input type="email" class="sign-up-input" v-model="user.mail" required>
              </div>
              <div class="errorMessageRegistrationForm" v-if="!isEmailGPValid">Please enter a valid emailaddress</div>
              <div class="field-btn">
                <button @click.prevent="goToNextForm">Next</button>
              </div>
            </div>
          </div>

          <div class="sign-up-page" id="user_detail2" v-if="currentForm ===2">
            <div class="title-sign-up-form">Contact Info:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Type of industry:</div>
              <select class="select-gender-sign-up" v-model="user.userType">
                <option>Horeca</option>
                <option>Bar</option>
                <option>Restaurant</option>
              </select>
            </div>
            <div class="field-sign-up">
              <div class="label-sign-up">Type of partner:</div>
              <select class="select-gender-sign-up" v-model="user.tag">
                <option>Chain partner</option>
                <option>Knowledge partner</option>
              </select>
            </div>
            <div class="field-sign-up">
              <div class="label-sign-up">Postal code:</div>
              <input type="text" class="sign-up-input" required v-model="user.postalCode">
            </div>
            <div class="errorMessageRegistrationForm" v-if="!isPostalCodeGPValid">Please enter a valid Dutch postal code</div>
            <div class="field-btn">
              <button class="prev-1" @click="goToPrevForm">Previous</button>
              <button class="next-1" @click.prevent="goToNextForm">Next</button>
            </div>
          </form>
<form class="sign-up-page" v-if="currentForm ===4">
            <div class="title-sign-up-form">Login Details:</div>
<div class="field-sign-up">
              <div class="label-sign-up">Username:</div>
              <input type="text" class="sign-up-input" required v-model="user.mail">
            </div>
            <div class="errorMessageRegistrationForm" v-if="!isUsernameGPValid">Must contain no more than 8 characters</div>
            <div class="field-sign-up">
<div class="label-sign-up">Password:</div>
              <input type="password" class="sign-up-input" required v-model="user.password">
            </div>
            <div class="errorMessageRegistrationForm" v-if="!isPasswordGPValid">Must contain at least one  number and one uppercase and lowercase letter,
              and at least 8 or more characters</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Confirm password:</div>
              <input type="password" class="sign-up-input" required>
            </div>
            <div class="errorMessageRegistrationForm">AAAAAALLLLLEEEERTTT</div>
            <div class="field-btn">
              <button class="prev-3" @click="goToPrevForm">Previous</button>
              <button class="submit" @click="sendForm">Submit</button>
            </div>
          </div>
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
            <p class="p-sign-up-form">Submit</p>
            <div class="bullet-sign-up-form">
              <span class="span-sign-up-form-check">4</span>
            </div>
            <div class="check-sign-up">&#10003;</div>
          </div>
        </div>
        <div class="form-sign-up-1" v-if="this.currentEntrepreneurForm === 1">
          <div class="sign-up-page">
            <div class="title-sign-up-form">Basic Info:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">First Name:</div>
              <input type="text" class="sign-up-input" required v-model="user.firstname">
            </div>
            <div class="errorMessageRegistrationForm" v-if="!isFirstnameEPValid">Must only contain letters</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Last Name:</div>
              <input type="text" class="sign-up-input" required v-model="user.lastname">
            </div>
            <div class="errorMessageRegistrationForm" v-if="!isLastnameEPValid">Must only contain letters</div>
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
        </div>

        <div class="sign-up-page" id="user_detail2" v-if="this.currentEntrepreneurForm === 2">
          <div class="title-sign-up-form">Contact Info:</div>
          <div class="field-sign-up">
            <div class="label-sign-up">Email Address:</div>
            <input type="email" class="sign-up-input" v-model="user.mail" required>
          </div>
          <div class="errorMessageRegistrationForm" v-if="!isEmailEPValid">Please enter a valid emailaddress</div>
          <div class="field-sign-up">
            <div class="label-sign-up">Postal code:</div>
            <input type="text" class="sign-up-input" required v-model="user.postalCode">
          </div>
          <div class="errorMessageRegistrationForm" v-if="!isPostalCodeGPValid"> Please enter a valid Dutch postal code</div>
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

        <div class="sign-up-page" v-if="this.currentEntrepreneurForm === 4">
          <div class="title-sign-up-form">Login Details:</div>
          <div class="field-sign-up">
            <div class="label-sign-up">Username:</div>
            <input type="email" class="sign-up-input" required v-model="user.mail">
          </div>
          <div class="errorMessageRegistrationForm" v-if="!isUsernameEPValid">Username must contain between 3 and 20 characters</div>
          <div class="field-sign-up">
            <div class="label-sign-up">Password:</div>
            <input type="password" class="sign-up-input" v-model="user.password" required>
          </div>
          <div class="errorMessageRegistrationForm" v-if="!isPasswordEPValid">
            Must contain at least one  number and one uppercase and lowercase letter,
            and at least 8 or more characters</div>
          <div class="field-sign-up">
            <div class="label-sign-up">Confirm password:</div>
            <input type="password" class="sign-up-input" required >
          </div>
          <div class="errorMessageRegistrationForm">AAAAAALLLLLEEEERTTT</div>
          <div class="field-btn">
            <button class="prev-3" @click="goToPrevFormEntrepreneur">Previous</button>
            <button class="submit" @click="sendForm">Submit</button>
          </div>
        </div>
      </div>
    </div>
    <form action="#">
      <!--              <div class="sign-up-page">-->
      <!--                <div class="title-sign-up-form">Here you can sign up as:</div>-->
      <!--                <div class="field-sign-up">-->
      <!--                <div class="field-btn-sign-up-as">-->
      <!--                  <button>Entrepreneur</button>-->
      <!--                  <button>General Partner</button>-->
      <!--                </div>-->
      <!--                </div>-->
      <!--              </div>-->
    </form>
  </div>
</template>

<style scoped>
.errorMessageRegistrationForm{
  color: red;
  font-size: 15px;
  height: 13px;
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
  height: 900px;
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
  background: #000;
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
  width: 400%;
  height: 400px;
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
  background: #000;
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
