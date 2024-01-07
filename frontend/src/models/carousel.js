export class Carousel {
  constructor (id, title, image, dateTime) {
    this.id = id
    this.title = title
    this.image = image
    this.date = dateTime
  }

  static copyConstructor (carousel) {
    if (!carousel) return null

    return new Carousel(
      carousel.id,
      carousel.title,
      carousel.image,
      carousel.dateTime
    )
  }
}
