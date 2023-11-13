import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import '../src/assets/css/main.css'
import './assets/css/welcomePageAndFooter.css'
/* import the fontawesome core */
import { library, config } from '@fortawesome/fontawesome-svg-core'

/* import font awesome icon component */
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

/* import specific icons */
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'

/* add icons to the library */
library.add(faUserSecret)

const app = createApp(App)
app.component('font-awesome-icon', FontAwesomeIcon)

config.styleDefault = 'duotone'

app.config.productionTip = false

app.use(router)
app.mount('#app')
