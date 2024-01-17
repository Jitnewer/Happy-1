import { flushPromises, mount } from '@vue/test-utils'
import Researches from '@/components/research/Researches.vue'
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
  theme: 'FOOD_WASTE'
}, {
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
  theme: 'WATER'
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
        entities: mockResearches,
        asyncFindAll: mockGetResearches,
        asyncFindById: mockGetResearchById
      }
    })
  }
})

beforeEach(async () => {
  wrapper = await mount(Researches, {
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
            id: null
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

it('should load all requested researches', async () => {
  expect(wrapper.findAll('.challenge').length).toBe(2)
})

it('should load filtered researches', async () => {
  wrapper.vm.researchService.entities = mockResearches.filter(r => r.theme === 'FOOD_WASTE')
  wrapper.vm.$route.query.sort = 'FOOD_WASTE'
  await flushPromises()
  expect(wrapper.findAll('.challenge').length).toBe(1)
})

it('should load no researches', async () => {
  wrapper.vm.researchService.entities = mockResearches.filter(r => r.theme === 'DISTRIBUTION')
  wrapper.vm.$route.query.sort = 'DISTRIBUTION'
  await flushPromises()
  expect(wrapper.findAll('.challenge').length).toBe(0)
})
