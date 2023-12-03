export class Research {
  static TAGS = {
    FOOD_WASTE: 'Food Waste',
    ENVIRONMENT: 'Environment'
  }

  constructor (id, image, title, link, context, author, publishDate, tags, summary, postType) {
    this.id = id
    this.image = image
    this.title = title
    this.link = link
    this.context = context
    this.author = author
    this.publishDate = publishDate
    this.tags = tags
    this.summary = summary
    this.postType = postType
  }

  static createSampleNews (index) {
    const id = index
    const nameSuffix = ['Food waste', 'Plastic Waste'][index]
    const link = ['www.Hva.nl', null][index]
    const context = [
      'Food waste is a pervasive and pressing global issue that encompasses the unnecessary discarding of edible food at various stages of the supply chain, from production to consumption. It not only poses environmental challenges, contributing to greenhouse gas emissions and resource depletion, but also represents a moral concern in a world where millions suffer from hunger. The reasons for food waste are diverse, ranging from inefficient farming practices and cosmetic standards that reject imperfect produce to consumer behavior and expiration date confusion. Addressing food waste requires a collective effort involving producers, retailers, and consumers to promote sustainable practices, foster awareness, and rethink our approach to consumption, ensuring that food is valued, preserved, and shared responsibly.',
      'For the past century plastic waste has risen by 60%.'
    ][index]
    const author = ['Kim Hofman', 'Connor Bakker'][index]
    const publishDate = ['2023-08-01', '2023-10-14'][index]
    const tags = [[this.TAGS.FOOD_WASTE], [this.TAGS.ENVIRONMENT]][index]
    const summary = ['It\'s about food waste', 'It\'s about platic'][index]
    const postType = ['post', 'link'][index]

    return new Research(
      id,
      index === 1
        ? require('../assets/images/plastic-waste.jpg')
        : require('../assets/images/food-wast.webp'),
      nameSuffix,
      link,
      context,
      author,
      publishDate,
      tags,
      summary,
      postType
    )
  }

  static copyConstructor (news) {
    if (!news) return null

    // You can add more logic here to deep clone attributes if needed.
    return new Research(
      news.id,
      news.image,
      news.title,
      news.link,
      news.context,
      news.author,
      news.publishDate,
      news.tags,
      news.summary,
      news.postType
    )
  }
}
