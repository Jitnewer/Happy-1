import { createMemoryHistory, createRouter } from 'vue-router'
import { mount } from '@vue/test-utils'
import Welcome from '@/components/welcomePage/Welcome.vue'
import 'jest-localstorage-mock'
import Challenges from '@/components/challenges/Challenges.vue'
import ChallengeDetails from '@/components/challenges/ChallengeDetails.vue'

import ResearchDetails from '@/components/research/ResearchDetails.vue'
import Researches from '@/components/research/Researches.vue'
import NetworkDetails from '@/components/networks/NetworkDetails.vue'
import Networks from '@/components/networks/Networks.vue'
import News from '@/components/News.vue'

const myRoutes = [
  { path: '/challenge/:id', name: 'challenge', component: ChallengeDetails },
  { name: 'challenges', component: Challenges },
  { path: '/researches/:id', name: 'research', component: ResearchDetails },
  { name: 'researches', component: Researches },
  { path: '/networks/:id', name: 'network', component: NetworkDetails },
  { name: 'networks', component: Networks },
  { name: 'welcome', component: Welcome },
  { name: 'news', query: {}, params: {} }

]

const storeMock = {
  commit: jest.fn()
}
describe('Challenges.vue', () => {
  let wrapper

  beforeEach(async () => {
    const router = createRouter({
      history: createMemoryHistory(),
      routes: myRoutes
    })
    wrapper = await mount(News, {
      global: {
        mocks: {
          $store: storeMock,
          $router: { push: jest.fn() } // Mock the router push method
        },
        plugins: [router]
      }
    })
  })

  // Define the test for rendering challenges in the template
  test('renders news blocks in the template', async () => {
    // Trigger the mocked function
    const challengeElement = wrapper.findAll('.news-block')
    expect(challengeElement.length).toBe(3)
  })
  test('clicking on the challenge article', async () => {
    // Trigger the mocked function
    const challengeElement = wrapper.findAll('.news-block')
    challengeElement[0].trigger('click')
    // Assert that the router's push method was called with the expected path
    expect(wrapper.vm.$router.push).toHaveBeenCalledWith({
      name: 'challenges'
    })
  })

  test('clicking on the reserach article', async () => {
    // Trigger the mocked function
    const challengeElement = wrapper.findAll('.news-block')
    challengeElement[1].trigger('click')
    // Assert that the router's push method was called with the expected path
    expect(wrapper.vm.$router.push).toHaveBeenCalledWith({
      name: 'researches'
    })
  })

  test('clicking on the network article', async () => {
    // Trigger the mocked function
    const challengeElement = wrapper.findAll('.news-block')
    challengeElement[2].trigger('click')
    // Assert that the router's push method was called with the expected path
    expect(wrapper.vm.$router.push).toHaveBeenCalledWith({
      name: 'networks'
    })
  })
})
