import { Paragraph } from '@/models/paragraph'

export class Challenge {
  constructor (id, title, dateTime, image, firstParagraph, paragraphs, theme) {
    this.id = id
    this.title = title
    this.dateTime = dateTime
    this.image = image
    this.firstParagraph = firstParagraph
    this.paragraphs = paragraphs
    this.theme = theme
  }

  static copyConstructor (challenge) {
    if (!challenge) return null
    return new Challenge(
      challenge.id,
      challenge.title,
      challenge.dateTime,
      challenge.image,
      challenge.firstParagraph,
      challenge.paragraphs,
      challenge.theme
    )
  }

  static createChallengeSample () {
    // Generate a unique ID (you can use a library like UUID for a more robust solution)
    const id = Math.floor(Math.random() * 1000)

    // Sample values for other properties
    const title = 'Sample Challenge'
    const dateTime = new Date().toISOString()
    const image = 'assets/img/profilepic.png'
    const firstParagraph = 'This is the first paragraph of the sample challenge.'
    const paragraphs = [
      new Paragraph(2, 'Task Details', 'This is the second paragraph of the sample challenge, providing more details about the task.'),
      new Paragraph(3, 'Instructions', 'In the third paragraph, you can elaborate on specific instructions or goals of the challenge.'),
      new Paragraph(4, 'Additional Information', 'The fourth paragraph might include additional information or hints for participants.')
    ]; const theme = 'WATER'

    // Create and return a new Challenge instance
    return new Challenge(id, title, dateTime, image, firstParagraph, paragraphs, theme)
  }
}
