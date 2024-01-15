import { mount } from '@vue/test-utils'
import NavBarLoggedIn from './NavBarLoggedIn.vue'
import { SessionSbService } from '@/services/SessionSbService'
import { shallowReactive } from 'vue'
import CONFIG from '../../../app-config'
import Login from '@/components/Login.vue'
import AboutUs from '@/components/AboutUs.vue'
import UserProfilePage from '@/components/user/UserProfilePage.vue'
import Welcome from '@/components/welcomePage/Welcome.vue'
import Events from '@/components/events/Events.vue'
import Challenges from '@/components/challenges/Challenges.vue'
import Researches from '@/components/research/Researches.vue'
import Networks from '@/components/networks/Networks.vue'
import { createMemoryHistory, createRouter } from 'vue-router'
import { User } from '@/models/user'

const myRoutes = [
  { name: 'login', component: Login },
  { name: 'about', component: AboutUs },
  { name: 'profilePage', component: UserProfilePage },
  { name: 'welcome', component: Welcome },
  { name: 'events', component: Events },
  { name: 'challenges', component: Challenges },
  { name: 'researches', component: Researches },
  { name: 'networks', component: Networks }
]

const USER_ID = 1
const USER_PROFILE_PIC = 'assets/img/profilepic.png'
const USER_FIRSTNAME = 'John'
const USER_LASTNAME = 'Doe'
const USER_MAIL = 'john.doe@example.com'
const USER_GENDER = 'male'
const USER_AGE = 30
const USER_COMPANY_TYPE = 'Some Company'
const USER_TAG = 'Some Tag'
const USER_STATUS = User.status.Active
const USER_TYPE = User.userTypes.Entrepreneur
const USER_POSTAL_CODE = '12345'
const USER_PASSWORD = 'password123'
const USER_COMPANY_NAME = 'Some Company'

const mockUser = new User(
  USER_ID,
  USER_PROFILE_PIC,
  USER_FIRSTNAME,
  USER_LASTNAME,
  USER_MAIL,
  USER_GENDER,
  USER_AGE,
  USER_COMPANY_TYPE,
  USER_TAG,
  USER_STATUS,
  USER_TYPE,
  USER_POSTAL_CODE,
  USER_PASSWORD,
  USER_COMPANY_NAME
)

const NAVBAR_LINK_HOME = 'a[href="/home"]'
const NAVBAR_LINK_CHALLENGES = 'a[href="/news/challenges"]'
const NAVBAR_LINK_RESEARCHES = 'a[href="/news/researches"]'
const NAVBAR_LINK_NETWORKS = 'a[href="/news/networks"]'
const NAVBAR_LINK_EVENTS = 'a[href="/?sort=asc"]'
const NAVBAR_LINK_ABOUT_US = 'a[href="/about"]'

const DROPDOWN_A_CLASS = '.dropdown a'
const DROPDOWN_CONTENT_CLASS = '.dropdown-content'
const PROFILE_DROPDOWN_CLASS = '.dropdown-profile'
const PROFILE_DROPDOWN_CONTENT_CLASS = '.dropdown-profile-content'
const PROFILE_DROPDOWN_CONTENT_LINK = '.dropdown-profile-content-link'

const storeMock = {
  commit: jest.fn(),
  getters: {
    user: mockUser
  }
}
describe('NavBarLoggedIn.vue', () => {
  let wrapper
  const sessionSBService = new SessionSbService(CONFIG.BACKEND_URL + '/authentication', CONFIG.JWT_STORAGE_ITEM)

  beforeEach(async () => {
    const router = createRouter({
      history: createMemoryHistory(),
      routes: myRoutes
    })
    wrapper = await mount(NavBarLoggedIn, {
      global: {
        mocks: {
          $store: storeMock, // Provide the Vuex store mock
          $router: { push: jest.fn() } // Mock the router push method

        },
        provide: {
          sessionSBService: shallowReactive(sessionSBService)
        },
        plugins: [router]
      },
      data () {
        return {
          user: mockUser // Set the mock user here
        }
      }
    })
  })

  afterEach(() => {
    wrapper.unmount()
  })

  test('renders navigation links', async () => {
    const homeLink = await wrapper.find(NAVBAR_LINK_HOME)
    const challengesLinks = await wrapper.find(NAVBAR_LINK_CHALLENGES)
    const researchesLink = await wrapper.find(NAVBAR_LINK_RESEARCHES)
    const networksLinks = await wrapper.find(NAVBAR_LINK_NETWORKS)
    const eventsLink = await wrapper.find(NAVBAR_LINK_EVENTS)
    const aboutUsLink = await wrapper.find(NAVBAR_LINK_ABOUT_US)

    expect(homeLink.exists(), 'home router link does not exist').toBe(true)
    expect(challengesLinks.exists(), 'challenges router link does not exist').toBe(true)
    expect(researchesLink.exists(), 'researches router link does not exist').toBe(true)
    expect(networksLinks.exists(), 'networks router link does not exist').toBe(true)
    expect(eventsLink.exists(), 'events router link does not exist').toBe(true)
    expect(aboutUsLink.exists(), 'about us router link does not exist').toBe(true)
  })

  test('toggles dropdown content on news link click', async () => {
    const newsLink = wrapper.find(DROPDOWN_A_CLASS)
    await newsLink.trigger('click')
    const dropdownContent = wrapper.find(DROPDOWN_CONTENT_CLASS)

    expect(dropdownContent.exists(), 'dropdown content does not exist').toBe(true)
  })

  test('toggles profile dropdown content on profile dropdown click', async () => {
    const profileDropdown = wrapper.find(PROFILE_DROPDOWN_CLASS)
    await profileDropdown.trigger('click')
    const profileDropdownContent = wrapper.find(PROFILE_DROPDOWN_CONTENT_CLASS)

    expect(profileDropdownContent.exists(), 'profile dropdown content does not exist').toBe(true)
  })

  test('calls handleLogout method on logout link click', async () => {
    const logoutLink = wrapper.findAll(PROFILE_DROPDOWN_CONTENT_LINK).find(link => link.text() === 'Logout')
    await logoutLink.trigger('click')

    expect(storeMock.commit, 'setLoggedIn is not been called en set to false').toHaveBeenCalledWith('setLoggedIn', false)
    expect(storeMock.commit, 'setLoggedInAsSuperUser is not been called en set to false').toHaveBeenCalledWith('setLoggedInAsSuperUser', false)
    expect(storeMock.commit, 'setLoggedInAsAdmin is not been called en set to false').toHaveBeenCalledWith('setLoggedInAsAdmin', false)

    expect(wrapper.vm.$router.push, 'login push is not called').toHaveBeenCalledWith({ path: '/login' })
  })
})
