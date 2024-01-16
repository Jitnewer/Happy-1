import { flushPromises, mount } from '@vue/test-utils'
import AdminUsersView from '@/components/admin/userPage/AdminUsersView.vue'
import { reactive } from 'vue'
import { User } from '@/models/user'
import { createMemoryHistory, createRouter } from 'vue-router'
import { InMemmoryEntityService } from '@/services/InMemmoryEntityService'
import welcomeAdmin from '@/components/admin/welcomeAdmin.vue'
import AdminUsersDetail from '@/components/admin/userPage/AdminUsersDetail.vue'
import ProfilePage from '@/components/admin/userPage/ProfilePage.vue'
import UserProfileInfo from '@/components/user/UserProfileInfo.vue'
import UserProfileEvents from '@/components/user/UserProfileEvents.vue'

let wrapper
let validUser

const myRoutes = [
  {
    path: '/admin',
    name: 'admin',
    props: false,
    component: welcomeAdmin
  },
  {
    path: '/admin/users',
    component: AdminUsersView,
    name: 'users',
    props: true,
    children: [
      {
        path: ':id',
        name: 'userDetail',
        component: AdminUsersDetail,
        props: true
      }
    ]
  },
  {
    path: '/profile/:id',
    name: 'profileView',
    component: ProfilePage,
    children: [
      {
        path: 'info',
        name: 'profileInfoView',
        component: UserProfileInfo
      },
      {
        path: 'events',
        name: 'profileEventsView',
        component: UserProfileEvents
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

  const theUserService = new InMemmoryEntityService(1, User.createSampleUser)
  wrapper = await mount(AdminUsersView, {
    global: {
      provide: {
        usersServiceAdmin: reactive(theUserService),
        fileUploadService: null,
        usersService: reactive(theUserService)
      },
      plugins: [router]
    }
  })

  await wrapper.vm.$router.isReady()
})

it('Can delete a User if confirmed', async function () {
  const confirmMock = jest.spyOn(window, 'confirm').mockReturnValue(true)

  // find the button to click
  const button = wrapper.findAll('button').filter(b => b.text() === 'Delete')

  // check how many users you start with
  const nRows = wrapper.findAll('tr').length
  const nUsers = wrapper.vm.users.length

  expect(button.length,
    'Each user row does not have a delete button')
    .toEqual(nRows - 1) // Excluding the table head row

  for (let i = 0; i < button.length; i++) {
    expect(button[i].element.disabled,
      'Button with label "Delete" has been disabled')
      .toBeFalsy()
  }

  await button[1].trigger('click')

  // Check whether a confirmation popup is shown
  expect(confirmMock).toHaveBeenCalledWith('Are you sure you want to delete this user?')
  expect(confirmMock).toHaveBeenCalledTimes(1)

  // Check whether a user is deleted properly
  const users = wrapper.vm.users.length
  expect(users,
    'User has not been deleted properly')
    .toEqual(nUsers - 1)

  // Check whether the table rows have been refreshed
  const rows = wrapper.findAll('tr').length - 1 // Exclude table header row
  expect(rows,
    'Deleting user does not refresh table rows')
    .toEqual(nRows - 2)

  // Clear confirm mock to prevent multiple calls
  confirmMock.mockClear()
})

it('cannot delete a User if not confirmed', async function () {
  const confirmMock = jest.spyOn(window, 'confirm').mockReturnValue(false)

  // find the button to click
  const button = wrapper.findAll('button').filter(b => b.text() === 'Delete')

  // check how many users you start with
  const nRows = wrapper.findAll('tr').length
  const nUsers = wrapper.vm.users.length

  expect(button.length,
    'Each user row does not have a delete button')
    .toEqual(nRows - 1) // Excluding the table head row

  for (let i = 0; i < button.length; i++) {
    expect(button[i].element.disabled,
      'Button with label "Delete" has been disabled')
      .toBeFalsy()
  }

  await button[1].trigger('click')

  // Check whether a confirmation popup is shown
  expect(confirmMock).toHaveBeenCalledWith('Are you sure you want to delete this user?')
  expect(confirmMock).toHaveBeenCalledTimes(1)

  // Check whether a user is deleted properly
  const users = wrapper.vm.users.length
  expect(users,
    'User has been deleted without confirming')
    .toEqual(nUsers)

  // Check whether the table rows have been refreshed
  const rows = wrapper.findAll('tr').length - 1 // Exclude table header row
  expect(rows,
    'not confirming refreshes table rows')
    .toEqual(nRows - 1)

  // Clear confirm mock to prevent multiple calls
  confirmMock.mockClear()
})

it('View button should redirect to profilePage route', async function () {
  const button = await wrapper.findAll('button').filter(b => b.text() === 'View')

  const nRows = wrapper.findAll('tr').length - 1 // excluding table head
  const users = wrapper.vm.users

  expect(button.length,
    'Each user row does not have a delete button')
    .toEqual(nRows) // Excluding the table head row

  for (let i = 0; i < button.length; i++) {
    expect(button[i].element.disabled,
      'Button with label "Delete" has been disabled')
      .toBeFalsy()

    button[i].trigger('click')

    await wrapper.vm.$nextTick()
    await flushPromises()

    // Check whether the right param id is passed through
    expect(wrapper.vm.$route.params.id,
      'route has incorrect :id parameter')
      .toBe(users[i].id.toString())

    // Check whether route is redirected properly
    expect(wrapper.vm.$route.path,
      'Button does not redirect to profile route')
      .toContain('/profile/' + users[i].id)

    const profileComponent = wrapper.findComponent(ProfilePage)
    expect(profileComponent.exists).toBeTruthy()
  }
})
