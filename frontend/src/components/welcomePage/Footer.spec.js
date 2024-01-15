import { mount } from '@vue/test-utils'
import Footer from '@/components/welcomePage/Footer.vue'

describe('Footer.vue', () => {
  it('renders the component', () => {
    const wrapper = mount(Footer)
    expect(wrapper.exists()).toBe(true)
  })

  it('validates email correctly', () => {
    const wrapper = mount(Footer)

    // Invalid email
    wrapper.vm.validateEmail('invalid-email')
    expect(wrapper.vm.isEmailValid).toBe(false)

    // Valid email
    wrapper.vm.validateEmail('valid@email.com')
    expect(wrapper.vm.isEmailValid).toBe(true)
  })

  it('calls searchAdress method correctly', () => {
    const wrapper = mount(Footer)
    const openSpy = jest.spyOn(window, 'open')

    wrapper.vm.searchAdress()
    expect(openSpy).toHaveBeenCalledWith('https://www.google.com/maps/search/Neptunusstraat+4,+1131+WJ+Volendam')
  })

  it('calls goToLinkedIn method correctly', () => {
    const wrapper = mount(Footer)
    const openSpy = jest.spyOn(window, 'open')

    wrapper.vm.goToLinkedIn()
    expect(openSpy).toHaveBeenCalledWith('https://www.linkedin.com/company/happy-hospitality-collective')
  })

  it('calls goToInstagram method correctly', () => {
    const wrapper = mount(Footer)
    const openSpy = jest.spyOn(window, 'open')

    wrapper.vm.goToInstagram()
    expect(openSpy).toHaveBeenCalledWith('http://instagram.com/', '_blank')
  })
})
