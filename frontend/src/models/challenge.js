export class Challenge {
  constructor (id, title, dateTime, image, firstParagraph, paragraphs) {
    this.id = id
    this.title = title
    this.dateTime = dateTime
    this.image = image
    this.firstParagraph = firstParagraph
    this.paragraphs = paragraphs
  }

  static copyConstructor (challenge) {
    if (!challenge) return null
    return new Challenge(
      challenge.id,
      challenge.title,
      challenge.dateTime,
      challenge.image,
      challenge.firstParagraph,
      challenge.paragraphs
    )
  }
}
