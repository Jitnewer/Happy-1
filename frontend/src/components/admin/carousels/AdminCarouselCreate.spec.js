import { mount } from '@vue/test-utils'
import { createRouter, createWebHistory } from 'vue-router'
import AdminCarouselCreate from '@/components/admin/carousels/AdminCarouselCreate.vue'
import { FileUploadAdapter } from '@/services/FileUploadAdapter.js'

// Mock your inject dependencies (carouselService, carouselServiceSuperUser, fileUploadService) here if needed.

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/admin', name: 'admin' },
    { path: '/admin/carousels', name: 'adminCarousels' },
    { path: '/admin/carousel/create', name: 'adminCarouselCreate' }
  ]
})

describe('AdminCarouselCreate.vue', () => {
  it('renders the component', () => {
    const wrapper = mount(AdminCarouselCreate, {
      global: {
        plugins: [router]
      }
      // Provide your inject dependencies here as mocks if needed.
    })

    expect(wrapper.exists()).toBe(true)
  })

  it('validates title correctly', () => {
    const wrapper = mount(AdminCarouselCreate, {
      global: {
        plugins: [router]
      }
      // Provide your inject dependencies here as mocks if needed.
    })

    // Invalid title
    wrapper.vm.validateTitle('This title isWay to Long For A Carousel So This Should Give Me A False Message Cuz its over 82 letters Soooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo')
    expect(wrapper.vm.isTitleValid).toBe(false)

    // Valid title
    wrapper.vm.validateTitle('valid title')
    expect(wrapper.vm.isTitleValid).toBe(true)
  })
  describe('FileUploadAdapter', () => {
    it('asyncUploadCarouselPic uploads carousel image successfully', async () => {
      // Mocking fetch function
      global.fetch = jest.fn(() =>
        Promise.resolve({
          json: () => Promise.resolve({ success: true })
        })
      )

      const fileUploadAdapter = new FileUploadAdapter('/your-resource-url')
      const file = new File(['(⌐□_□)'], 'carousel-image.png', { type: 'image/png' })
      const carouselId = '123'

      // Calling the method
      const response = await fileUploadAdapter.asyncUploadCarouselPic(file, carouselId)

      // Assertions
      expect(response).toEqual({ success: true })

      // Ensure fetch is called with the correct parameters
      expect(global.fetch).toHaveBeenCalledWith('/your-resource-url/carouselPic', {
        method: 'POST',
        body: expect.any(FormData)
      })

      // Reset fetch mock
      global.fetch.mockClear()
      delete global.fetch
    })
  })
})
