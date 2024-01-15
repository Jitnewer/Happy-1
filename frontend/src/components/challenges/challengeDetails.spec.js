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
  { path: '/challenge/:id', name: 'challenge', component: ChallengeDetails },
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
    await router.push({ name: 'challenge', params: { id: challengeServiceMock.entities[0].id } })

    wrapper = await mount(ChallengeDetails, {
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
  test('renders challenge in the template', async () => {
    const challengeElement = wrapper.find('.detail-challenge')
    expect(challengeElement.find('h2').text()).toBe(challengeServiceMock.entities.title)

    expect(challengeElement.find('p').text()).toBe(wrapper.vm.formattedDateTime(challengeServiceMock.entities.dateTime))
  })
  test('pressing the back button to go to the challenges page', async () => {
    // Trigger the mocked function
    wrapper.find('button').trigger('click')

    // Assert that the router's push method was called with the expected path
    expect(wrapper.vm.$router.push).toHaveBeenCalledWith({
      name: 'challenges',
      query: { sort: wrapper.vm.filter }
    })
  })
})
