export class Carousel {
  constructor (id, text, image, dateTime) {
    this.id = id
    this.text = text
    this.image = image
    this.date = dateTime
  }

  static copyConstructor (carousel) {
    if (!carousel) return null

    return new Carousel(
      carousel.id,
      carousel.text,
      carousel.image,
      carousel.dateTime
    )
  }
}
