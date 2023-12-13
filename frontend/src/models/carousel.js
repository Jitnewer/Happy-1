export class Carousel {
  constructor (id, text, image, date) {
    this.id = id
    this.text = text
    this.image = image
    this.date = date
  }

  get date () {
    return this.date
  }

  static copyConstructor (carousel) {
    if (!carousel) return null

    return new Carousel(
      carousel.id,
      carousel.text,
      carousel.image,
      carousel.text
    )
  }
}
