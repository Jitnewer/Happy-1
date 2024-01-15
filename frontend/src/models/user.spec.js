import { User } from '@/models/user'

let user1
let user2

beforeEach(function () {
  user1 = new User(1, [], 'profilePic.png', 'Mike', 'Groot', 'mikegroot@mail.com', 'male', 30, 'happyHospitality', 'N/A', User.status.Active, User.userTypes.Admin, '1623AD', 'password', 'happyHospitality')
})

it('clones a proper copy', () => {
  const copy = User.copyConstructor(user1)

  expect(copy,
    'copy of user1 does not equal the original'
  ).toStrictEqual(user1)
})

it('creates a proper user', () => {
  user2 = User.createSampleUser()

  expect(user2.firstname).toBeTruthy()
  expect(user2.lastname).toBeTruthy()
  expect(user2.mail).toBeTruthy()
})
