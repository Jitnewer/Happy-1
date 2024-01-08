
export class Newsletter {
  constructor (id, email) {
    this.id = id
    this.email = email
  }

  static copyConstructor (newsletter) {
    if (!newsletter) return null
    return new Newsletter(
      newsletter.id,
      newsletter.email
    )
  }
}
