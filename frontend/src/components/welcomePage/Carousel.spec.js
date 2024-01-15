import { mount } from '@vue/test-utils'
import Carousel from '@/components/welcomePage/Carousel.vue'

describe('Carousel.vue', () => {
  it('renders correctly and starts auto-playing', async () => {
    const wrapper = mount(Carousel, {
      props: {
        autoPlayEnabled: true,
        slideCount: 3
      }
    })

    // assertions
    expect(wrapper.find('.carousel').exists()).toBe(true)
    expect(wrapper.findAll('.carouselPagination')).toHaveLength(3)

    // Wait for auto-play interval
    await new Promise(resolve => setTimeout(resolve, 1000))

    // Assert that the current slide has changed
    expect(wrapper.vm.currentSlide.value).not.toBe(1)
  })
})
