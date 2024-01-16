import { mount } from '@vue/test-utils'
import { createRouter, createWebHistory } from 'vue-router'
import AdminCarouselCreate from '@/components/admin/carousels/AdminCarouselCreate.vue'
import { FileUploadAdapter } from '@/services/FileUploadAdapter.js'

// Create a Vue Router instance
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
    // Mount the component with Vue Router as a plugin
    const wrapper = mount(AdminCarouselCreate, {
      global: {
        plugins: [router]
      }
    })

    expect(wrapper.exists()).toBe(true)
  })

  it('validates title correctly', () => {
    // Mount the component with Vue Router as a plugin
    const wrapper = mount(AdminCarouselCreate, {
      global: {
        plugins: [router]
      }
    })

    // Invalid title
    wrapper.vm.validateTitle('This title isWay to Long For A Carousel So This Should Give Me A False Message Cuz its over 82 letters Soooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo')
    expect(wrapper.vm.isTitleValid).toBe(false)

    // Valid title
    wrapper.vm.validateTitle('valid title')
    expect(wrapper.vm.isTitleValid).toBe(true)
  })

  // Nested test suite for FileUploadAdapter
  describe('FileUploadAdapter', () => {
    it('asyncUploadCarouselPic uploads carousel image successfully', async () => {
      // Mocking fetch function
      global.fetch = jest.fn(() =>
        Promise.resolve({
          json: () => Promise.resolve({ success: true })
        })
      )

      // Create a FileUploadAdapter instance
      const fileUploadAdapter = new FileUploadAdapter('/resource-url')
      const file = new File(['file'], 'carousel-image.png', { type: 'image/png' })
      const carouselId = '123'

      const response = await fileUploadAdapter.asyncUploadCarouselPic(file, carouselId)

      // Assertions
      expect(response).toEqual({ success: true })

      // Ensure fetch is called with the correct parameters
      expect(global.fetch).toHaveBeenCalledWith('/resource-url/carouselPic', {
        method: 'POST',
        body: expect.any(FormData)
      })

      // Reset fetch mock
      global.fetch.mockClear()
      delete global.fetch
    })
  })
})
