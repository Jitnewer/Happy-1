export class Research {
  constructor (id, title, dateTime, image, firstParagraph, paragraphs) {
    this.id = id
    this.title = title
    this.dateTime = dateTime
    this.image = image
    this.firstParagraph = firstParagraph
    this.paragraphs = paragraphs
  }

  static copyConstructor (research) {
    if (!research) return null
    return new Research(
      research.id,
      research.title,
      research.dateTime,
      research.image,
      research.firstParagraph,
      research.paragraphs
    )
  }
}
