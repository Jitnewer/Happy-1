export class User {
  constructor (id, firstname, lastname, email, phoneNumber, admin, hashedPassword) {
    this.id = id
    this.firstname = firstname
    this.lastname = lastname
    this.email = email
    this.phoneNumber = phoneNumber
    this.admin = admin
    this.hashedPassword = hashedPassword
  }

  static copyConstructor (user) {
    if (!user) return null

    // You can add more logic here to deep clone attributes if needed.
    return new User(
      user.id,
      user.firstname,
      user.lastname,
      user.email,
      user.phoneNumber,
      user.admin,
      user.hashedPassword
    )
  }
}
