import { createMemoryHistory, createRouter } from 'vue-router'
import { mount } from '@vue/test-utils'
import { reactive } from 'vue'
import Welcome from '@/components/welcomePage/Welcome.vue'
import 'jest-localstorage-mock'
import { Challenge } from '@/models/challenge'
import Challenges from '@/components/challenges/Challenges.vue'
import ChallengeDetails from '@/components/challenges/ChallengeDetails.vue'
import { InMemoryEntitiesService } from '@/services/InMemoryEntitiesService'
import { CachedRESTAdaptorWithFetch } from '@/services/CachedRESTAdaptorWithFetch'
import CONFIG from '../../../app-config'

const myRoutes = [
  { name: 'challenges', component: Challenges },
  { name: 'challenge', component: ChallengeDetails },
  { name: 'welcome', component: Welcome },
  { name: 'news', query: {}, params: {} }

]

const storeMock = {
  commit: jest.fn()
}
describe('Challenges.vue', () => {
  let wrapper
  const challengeServiceMock = new InMemoryEntitiesService(1000, Challenge.createChallengeSample)
  const challengeService = new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/challenges', Challenge.copyConstructor)
  beforeEach(async () => {
    const router = createRouter({
      history: createMemoryHistory(),
      routes: myRoutes
    })
    wrapper = await mount(Challenges, {
      global: {
        mocks: {
          $store: storeMock,
          $router: { push: jest.fn() } // Mock the router push method
        },
        provide: {
          challengeServiceMock: reactive(challengeServiceMock),
          challengeService: reactive(challengeService)
        },
        plugins: [router]
      }
    })
  })

  // Define the test for rendering challenges in the template
  test('renders challenges in the template', async () => {
    // Trigger the mocked function
    const challengeElements = wrapper.findAll('.challenge')
    // Check if the event details are rendered correctly
    challengeService.entities.forEach((challenge, index) => {
      challengeElements.forEach((challengeElement, index) => {
        expect(challengeElement.find('h3').text()).toBe(challenge.title)
      // Add more assertions based on your template structure
      })
    })
  })
  test('selecting a challenge navigates to the correct path', async () => {
    // Trigger the mocked function
    const challengeElements = wrapper.findAll('.challenge')

    // Choose a specific challenge element (for example, the first one)
    const selectedChallenge = challengeElements[0]

    // Get the challenge data
    const challengeData = challengeService.entities[0]

    // Click on the selected challenge
    await selectedChallenge.trigger('click')

    // Assert that the router's push method was called with the expected path
    expect(wrapper.vm.$router.push).toHaveBeenCalledWith({
      name: 'challenge',
      params: { id: challengeData.id },
      query: { sort: wrapper.vm.filter }
    })
  })
})
