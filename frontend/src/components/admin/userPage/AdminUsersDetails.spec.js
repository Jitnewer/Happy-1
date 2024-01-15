import { flushPromises, mount } from '@vue/test-utils'
import AdminUsersView from '@/components/admin/userPage/AdminUsersView.vue'
import { reactive } from 'vue'
import { User } from '@/models/user'
import { createMemoryHistory, createRouter } from 'vue-router'
import { InMemmoryEntityService } from '@/services/InMemmoryEntityService'
import welcomeAdmin from '@/components/admin/welcomeAdmin.vue'
import AdminUsersDetail from '@/components/admin/userPage/AdminUsersDetail.vue'

let wrapper
let validUser
let theUserService

const myRoutes = [
  {
    path: '/admin',
    name: 'admin',
    component: welcomeAdmin
  },
  {
    path: '/admin/users',
    component: AdminUsersView,
    name: 'users',
    children: [
      {
        path: ':id',
        name: 'userDetail',
        component: AdminUsersDetail,
        params: 1
      }
    ]
  }
]

beforeEach(async function () {
  const router = createRouter({
    history: createMemoryHistory(),
    routes: myRoutes
  })
  validUser = new User(0, null, 'assets/profilePic/profilepic.png',
    'Bram', 'Groot', 'TimTest@gmail.com', 'N/A', 0, 'Happy Hospitality', 'N/A',
    User.status.Active, User.userTypes.Admin, 'N/A', 'passwordtest', 'Happy Hospitality'
  )

  theUserService = new InMemmoryEntityService(1, User.createSampleUser)
  wrapper = await mount(AdminUsersDetail, {
    props: {
      selectedUser: validUser,
      create: true // or true based on your requirement
    },
    global: {
      provide: {
        usersServiceAdmin: reactive(theUserService)
      },
      plugins: [router]
    }
  })

  await wrapper.vm.$router.isReady()
})

it('Can save User', async () => {
  const confirmMock = jest.spyOn(window, 'confirm').mockReturnValue(true)
  expect(wrapper.exists()).toBe(true)
  const nUsers = theUserService.asyncFindAll().length

  // Setup create prop
  wrapper.setProps({
    create: true
  })

  // Find saveButton
  const saveButton = wrapper.findAll('button').filter(b => b.text() === 'Save')

  expect(saveButton.length,
    'Cannot have more than 1 save button'
  ).toBe(1)

  await saveButton[0].trigger('click')
  await wrapper.vm.$nextTick()
  await flushPromises()

  expect(confirmMock).toHaveBeenCalledWith('Are you sure you want to change the access of this user?')
  expect(confirmMock).toHaveBeenCalledTimes(1)

  const users = await theUserService.asyncFindAll()
  expect(users.length).toBe(nUsers + 1)
})

it('Can update user', async function () {
  const confirmMock = jest.spyOn(window, 'confirm').mockReturnValue(true)

  expect(wrapper.exists()).toBe(true)

  const nUser = theUserService.asyncFindById(1)
  nUser.firstname = 'Thijs'

  // Find saveButton
  const saveButton = wrapper.findAll('button').filter(b => b.text() === 'Save')

  expect(saveButton.length,
    'Cannot have more than 1 save button'
  ).toBe(1)

  await saveButton[0].trigger('click')
  await wrapper.vm.$nextTick()
  await flushPromises()

  expect(confirmMock).toHaveBeenCalledWith('Are you sure you want to change the access of this user?')
  expect(confirmMock,
    'Confirmation was shown multiple times').toHaveBeenCalledTimes(1)

  const users = await theUserService.asyncFindById(1)
  expect(users,
    'User was not updated properly').toEqual(nUser)
})
