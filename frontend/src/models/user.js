export class User {
  static userTypes = {
    Admin: 'ADMIN',
    Entrepreneur: 'ENTREPRENEUR',
    Partner: 'PARTNER',
    SuperUser: 'SUPERUSER'
  };

  constructor (id, profilePic, name, mail, gender, age, companyType, tag, status, userType, postalCode) {
    this.id = id
    this.profilePic = profilePic
    this.name = name
    this.mail = mail
    this.gender = gender
    this.age = age
    this.companyType = companyType
    this.tag = tag
    this.status = status
    this.userType = userType
    this.postalCode = postalCode
  }

  static copyConstructor (user) {
    if (!user) return null

    const copy = new User(
      user.id,
      user.profilePic,
      user.name,
      user.mail,
      user.gender,
      user.age,
      user.companyType,
      user.tag,
      user.status,
      user.userType,
      user.postalCode
    )
    // You can add more logic here to deep clone attributes if needed.
    return copy
  }

  static createSampleUser () {
    const id = Math.floor((Math.random() * 100) + 1)
    const image = require('../assets/img/profilepic.png')

    const names = ['Tim Groot', 'Mylo', 'Pim', 'Eva']
    let randomIndex = Math.floor(Math.random() * names.length)
    const randomName = names[randomIndex]

    randomIndex = Math.floor(Math.random() * names.length)
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
    const status = randomStatusNumb < 0.5 ? 'active' : 'inactive'
    const age = Math.floor((Math.random() * 50) + 50)
    const companyType = 'Catering'
    const postalCode = '1242 DA'

    return new User(id, image, randomName, mail, gender, age, companyType, tag, status, randomUserType, postalCode)
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
