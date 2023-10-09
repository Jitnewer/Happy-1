<script setup>
</script>

<template>
  <!--  <div class="sign-up-specific">-->
  <!--    <h1 class="h1-sign-up">Sign Up</h1>-->
  <!--    <div class="options-reg">-->
  <!--      <button class="button-left-reg">Sign Up As Entrepreneur</button></div>-->
  <!--    <span class="options-span"></span>-->
  <!--    <div> <button class="button-right-reg">Sign up as General Partner </button></div>-->
  <!--  </div>-->
  <div class="sign-up-body">
    <div class="container-sign-up">
      <div class="header-sign-up">Sign Up Entrepreneur</div>
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
      <div class="form-sign-up-1">
        <form action="#">
          <div class="sign-up-page">
            <div class="title-sign-up-form">Basic Info:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">First Name:</div>
              <input type="text" class="sign-up-input" required>
            </div>

            <div class="field-sign-up" >
              <div class="label-sign-up">Last Name:</div>
              <input type="text" class="sign-up-input" required>
            </div>

            <div class="field-sign-up">
              <div class="label-sign-up">Type of industry:</div>
              <select class="select-gender-sign-up">
                <option>Horeca</option>
                <option>Bar</option>
                <option>Hotel</option>
              </select>
            </div>

            <div class="field-btn">
              <button>Next</button>
            </div>
          </div>

          <div class="sign-up-page" id="user_detail2" >
            <div class="title-sign-up-form">Contact Info:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Email Address:</div>
              <input type="email" class="sign-up-input" required>
            </div>

            <div class="field-sign-up">
              <div class="label-sign-up">Postal code:</div>
              <input type="text" class="sign-up-input" required>
            </div>

            <div class="field-btn">
              <button class="prev-1">Previous</button>
              <button class="next-1">Next</button>
            </div>
          </div>
          <div class="sign-up-page" id="user_detail3">
            <div class="title-sign-up-form">Date of Birth:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Dates:</div>
              <input type="date" class="sign-up-input">
            </div>

            <div class="field-sign-up">
              <div class="label-sign-up">Gender:</div>
              <select class="select-gender-sign-up">
                <option>Male</option>
                <option>Female</option>
                <option>Prefer not to tell</option>
              </select>
            </div>

            <div class="field-btn">
              <button class="prev-2">Previous</button>
              <button class="next-2">Next</button>
            </div>
          </div>
          <div class="sign-up-page">
            <div class="title-sign-up-form">Login Details:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Username:</div>
              <input type="text" class="sign-up-input" required>
            </div>

            <div class="field-sign-up">
              <div class="label-sign-up">Password:</div>
              <input type="password" class="sign-up-input" required>
            </div>

            <div class="field-sign-up">
              <div class="label-sign-up">Confirm password:</div>
              <input type="password" class="sign-up-input" required>
            </div>

            <div class="field-btn">
              <button class="prev-3">Previous</button>
              <button class="submit">Submit</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!--  <div class="body">-->
  <!--<div class="container-reg">-->
  <!--  <div class="title">Registration</div>-->
  <!--  <form action="#">-->
  <!--    <div class="user-details">-->
  <!--      <div class="input-box">-->
  <!--        <span class="details">Full Name</span>-->
  <!--        <input type="text" placeholder="Enter your name" required>-->
  <!--      </div>-->
  <!--      <div class="input-box">-->
  <!--        <span class="details">Username</span>-->
  <!--        <input type="text" placeholder="Enter your username" required>-->
  <!--      </div>-->
  <!--      <div class="input-box">-->
  <!--        <span class="details">Email</span>-->
  <!--        <input type="text" placeholder="Enter your email" required>-->
  <!--      </div>-->
  <!--      <div class="input-box">-->
  <!--        <span class="details">Phone Number</span>-->
  <!--        <input type="text" placeholder="Enter your number" required>-->
  <!--      </div>-->
  <!--      <div class="input-box">-->
  <!--        <span class="details">Password</span>-->
  <!--        <input type="text" placeholder="Enter your password" required>-->
  <!--      </div>-->
  <!--      <div class="input-box">-->
  <!--        <span class="details">Confirm Password</span>-->
  <!--        <input type="text" placeholder="Confirm your password" required>-->
  <!--      </div>-->

  <!--      <div class="gender-details">-->
  <!--        <input type="radio" name="gender" id="dot-1">-->
  <!--        <input type="radio" name="gender" id="dot-2">-->
  <!--        <input type="radio" name="gender" id="dot-3">-->
  <!--        <span class="gender-title">Gender</span>-->
  <!--        <div class="gender-category">-->
  <!--          <label for="dot-1">-->
  <!--            <span class="dot one"></span>-->
  <!--            <span class="gender">Male</span>-->
  <!--          </label>-->
  <!--          <label for="dot-2">-->
  <!--            <span class="dot two"></span>-->
  <!--            <span class="gender">Female</span>-->
  <!--          </label>-->
  <!--        </div>-->
  <!--      </div>-->
  <!--      <div class="button">-->
  <!--        <input type="submit" value="Register">-->
  <!--      </div>-->
  <!--    </div>-->
  <!--  </form>-->
  <!--</div>-->
  <!--  </div>-->
</template>

<style scoped>
.sign-up-body {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: indianred;
}

.container-sign-up {
  width: 400px;
  background: #fff;
  border-radius: 5px;
  text-align: center;
  padding: 50px 35px 10px 35px;
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

.select-gender-sign-up {
  width: 100%;
  padding-left: 10px;
  font-size: 17px;
  font-weight: 500;
}

.field-btn button {
  width: 100%;
  height: 50px;
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

.span-sign-up-form-check span{
  font-weight: 500;
  font-size: 17px;
  line-height: 25px;
  position: absolute;
  left: 50%;
  transform: translate(-50%);
}
.bullet-sign-up-form:before,
.bullet-sign-up-form:after{
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
.step .bullet-sign-up-form:after{
  background: #d43f8d;
  transform: scaleX(0);
  transform-origin: left;
  animation: animate 0.3s linear forwards;
}
@keyframes animate {
  100%{
    transform: scaleX(1);
  }
}
.check-sign-up{
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
