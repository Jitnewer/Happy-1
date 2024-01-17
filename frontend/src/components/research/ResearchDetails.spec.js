import { flushPromises, mount } from '@vue/test-utils'
import ResearchDetails from '@/components/research/ResearchDetails.vue'
import { reactive } from 'vue'
import { CachedRESTAdaptorWithFetch } from '@/services/CachedRESTAdaptorWithFetch'
import CONFIG from '../../../app-config'
import { Research } from '@/models/research'
import router from '@/router'

let wrapper
const mockResearches = [{
  id: 1,
  title: 'Research Title',
  dateTime: '2019-01-01T00:00:00.000Z',
  image: '/img.png',
  firstParagraph: 'Research first paragraph',
  paragraphs: [{
    id: 1,
    title: 'Research Title',
    content: 'Research paragraph 1'
  }, {
    id: 2,
    title: 'Research Title2',
    content: 'Research paragraph 2'
  }],
  theme: 'Research theme'
},
{
  id: 2,
  title: 'Research Title 2',
  dateTime: '2019-01-01T00:00:00.000Z',
  image: '/img.png',
  firstParagraph: 'Research first paragraph2',
  paragraphs: [{
    id: 1,
    title: 'Research Title',
    content: 'Research paragraph 1'
  }, {
    id: 2,
    title: 'Research Title2',
    content: 'Research paragraph 2'
  }],
  theme: 'Research theme'
}]

const mockGetResearches = jest.fn(() => {
  return mockResearches
})

const mockGetResearchById = jest.fn(() => {
  return mockResearches[0]
})

const storeMock = {
  commit: jest.fn(),
  getters: {
    getResearches: mockGetResearches
  }
}

jest.mock('@/services/CachedRESTAdaptorWithFetch', () => {
  return {
    CachedRESTAdaptorWithFetch: jest.fn().mockImplementation(() => {
      return {
        entity: mockGetResearchById,
        asyncFindAll: mockGetResearches,
        asyncFindById: mockGetResearchById
      }
    })
  }
})

beforeEach(async () => {
  wrapper = await mount(ResearchDetails, {
    global: {
      provide: {
        researchService: reactive(new CachedRESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/researches', Research.copyConstructor))
      },
      mocks: {
        $store: storeMock, // Provide the Vuex store mock
        $route: {
          query: {
            sort: null
          },
          params: {
            id: '1' // Simulate the route parameter as being /1
          }
        }
      },
      plugins: [router]
    }
  })
})

afterEach(function () {
  wrapper.unmount()
  wrapper = null
})

it('Renders the research details correctly', () => {
  // https://test-utils.vuejs.org/api/#global-mocks for the mocks option
  expect(wrapper.find('.research-title').text()).toBe('Research Title')
  expect(wrapper.find('.challenge-time').text()).toBe('dinsdag 1 januari 2019, 01:00') // Check if the date is formatted correctly
  expect(wrapper.find('.research-image').attributes('src')).toBe('https://ik.imagekit.io/happy1hva/img.png') // Check if the image is looked for at the CND with the correct /img.png path
  expect(wrapper.find('.research-firstp').text()).toBe('Research first paragraph')
  expect(wrapper.findAll('.research-ptitle').length).toBe(2)
  expect(wrapper.findAll('.research-p').length).toBe(2)
  expect(wrapper.findAll('.research-ptitle').at(0).text()).toBe('Research Title')
  expect(wrapper.findAll('.research-ptitle').at(1).text()).toBe('Research Title2')
  expect(wrapper.findAll('.research-p').at(0).text()).toBe('Research paragraph 1')
  expect(wrapper.findAll('.research-p').at(1).text()).toBe('Research paragraph 2')
})

// it('should not render container if id is not found', async () => {
//   wrapper.vm.$route.params.id = 3
//   console.log(wrapper.vm.$route.params.id)
//   wrapper.vm.researchService.entinty = mockResearches.filter(research => research.id === 3)[0]
//   await flushPromises()
//   console.log(wrapper.html())
//   expect(wrapper.find('.container').exists()).toBe(false)
// })

// Another test to check if we get a 404 error when we try to access a research that does not exist using the url
