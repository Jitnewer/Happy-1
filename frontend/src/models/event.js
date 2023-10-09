
export class Event {
  constructor (id, image, name, date, timeBegin, timeEnd, price, location, info) {
    this.id = id
    this.image = image
    this.name = name
    this.date = date
    this.timeBegin = timeBegin
    this.timeEnd = timeEnd
    this.price = price
    this.location = location
    this.info = info
  }

  static createSampleEvents (index) {
    const id = Math.floor(Math.random() * 100) + 1
    const imageIndex = Math.floor(Math.random() * 2)
    const nameSuffix = ['Amsterdam', 'Rotterdam', 'Arnhem', 'Zaandam'][index]
    const date = ['2023-10-09', '2023-10-09', '2023-12-09', '2024-01-09'][index]
    const timeBegin = '12:00'
    const timeEnd = '15:00'
    const price = [null, null, null, 50][index]
    const location = ['Amsterdam Dam', 'Rotterdam Ahoy', 'Villa Sansbeek', 'Zaandam'][index]
    const info = [
      'Are you a supporter of sustainability and a good time? Then we warmly invite you to the Green Gathering in Amsterdam! Alongside experts like Sarah Groenewoud from EcoEats, the Municipality of Utrecht, and various other businesses in the food sector and events industry, we will engage in discussions about how sustainability plays a pivotal role in their endeavors. Together, we will explore innovative solutions to comply with the latest environmental regulations and combat plastic pollution.',
      "Do you share a passion for both sustainability and a good time? If so, we invite you to the Eco-Friendly Hangout in Rotterdam! Join us alongside environmental enthusiasts, including local eco-leaders, the City of Amsterdam, and fellow businesses in the hospitality and events sectors, as we delve into the importance of sustainability in our endeavors. Together, we'll embark on a quest to find innovative solutions to meet the latest Single Use Plastics regulations that came into force this year.",
      'Do you consider sustainability as important as having a good time? Then join us at the Happy Hospitality Pub Talk in Arnhem! Together with Kees Bouwhof, owner of Villa Sonsbeek and the Palatijn, the Municipality of Arnhem, and several other entrepreneurs from the hospitality and events industry, we will discuss how sustainability is at the forefront of their concerns. We will also search for the holy grail of solutions to address the new Single Use Plastics legislation that came into effect earlier this year.',
      "Do you believe in the importance of sustainability while enjoying a great atmosphere? Then don't miss out on the Sustainable Social Gathering in Arnhem! Together with industry leaders like Emily Greenfield from Green Eats, the Rotterdam City Council, and several other hospitality and event professionals, we will discuss the impact of sustainability on our businesses. Additionally, we'll be on the hunt for the ultimate solution to comply with the new Single Use Plastics legislation that has been in effect since the beginning of the year."
    ][index]
    return new Event(
      id,
      imageIndex === 0
        ? require('../assets/images/arnhem-event.jpg')
        : require('../assets/images/rotterdam-event.webp'),
      `Kroegpraat @ ${nameSuffix}`,
      date,
      timeBegin,
      timeEnd,
      price,
      location,
      info
    )
  }

  static copyConstructor (event) {
    if (!event) return null

    const copy = new Event(
      event.id,
      event.image,
      event.name,
      event.date,
      event.timeBegin,
      event.timeEnd,
      event.price,
      event.location,
      event.info
    )
    // You can add more logic here to deep clone attributes if needed.
    return copy
  }
}
