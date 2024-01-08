
export class Subscriber {
  constructor (id, email) {
    this.id = id
    this.email = email
  }

  static copyConstructor (newsletter) {
    if (!newsletter) return null
    return new Subscriber(
      newsletter.id,
      newsletter.email
    )
  }
}
