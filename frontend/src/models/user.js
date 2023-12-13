export class User {
  static userTypes = {
    Admin: 'ADMIN',
    Entrepreneur: 'ENTREPRENEUR',
    Partner: 'PARTNER',
    SuperUser: 'SUPERUSER'
  };

  static status = {
    Active: 'ACTIVE',
    Inactive: 'INACTIVE',
    Banned: 'BANNED',
    Unbanned: 'UNBANNED' // Adding the opposite status
  }

  constructor (id, userEvents, profilePic, firstname, lastname, mail, gender, age, companyType, tag, status, userType, postalCode, password, companyName) {
    this.id = id
    this.profilePic = profilePic
    this.firstname = firstname
    this.lastname = lastname
    this.mail = mail
    this.gender = gender
    this.age = age
    this.companyType = companyType
    this.tag = tag
    this.status = status
    this.userType = userType
    this.postalCode = postalCode
    this.password = password
    this.companyName = companyName
  }

  static copyConstructor (user) {
    if (!user) return null

    // You can add more logic here to deep clone attributes if needed.
    return new User(
      user.id,
      user.userEvents,
      user.profilePic,
      user.firstname,
      user.lastname,
      user.mail,
      user.gender,
      user.age,
      user.companyType,
      user.tag,
      user.status,
      user.userType,
      user.postalCode,
      user.password,
      user.companyName
    )
  }

  static createSampleUser () {
    const id = Math.floor((Math.random() * 100) + 1)
    const image = require('./../assets/img/profilepic.png')

    const firstname = ['Tim', 'Mylo', 'Pim', 'Eva']
    const lastname = ['Groot', 'Commandeur', 'Voortuin', 'Mase']
    const names = ['Tim Groot', 'Mylo', 'Pim', 'Eva']
    const randomIndex = Math.floor(Math.random() * names.length)
    const randomName = names[randomIndex]

    const userTypeValues = Object.values(this.userTypes)
    const randomUserType = userTypeValues[randomIndex]

    const mail = randomName + '@gmail.com'
    const gender = 'male'
    let tag
    if (randomUserType === 'PARTNER') {
      tag = 'Chain-partner'
    } else {
      tag = 'N/A'
    }

    const randomStatusNumb = Math.random()
    const status = randomStatusNumb < 0.5 ? 'ACTIVE' : 'INACTIVE'
    const age = Math.floor((Math.random() * 50) + 50)
    const companyType = 'Catering'
    const postalCode = '1242 DA'

    return new User(id, image, firstname[randomIndex], lastname[randomIndex], mail, gender, age, companyType, tag, status, randomUserType, postalCode)
  }

  static generateId (usedIdList) {
    let newId
    do {
      // Generate a new ID (you can use any method you prefer)
      newId = Math.floor((Math.random() * 100) + 1) // Replace with your preferred method to generate IDs

      // If the newId is in usedIds, repeat the loop to generate a new one
    } while (usedIdList.includes(newId))

    // Once a unique ID is generated, return it
    return newId
  }

  equals (userCopy, create) {
    if (create) {
      return (
        this.userType !== userCopy.userType ||
        this.name !== userCopy.name ||
        this.mail !== userCopy.mail
      )
    }
    return (
      this.userType !== userCopy.userType
    )
  }
}
