
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

  static createSampleEvents () {
    const id = Math.floor(Math.random() * 100) + 1
    const imageIndex = Math.floor(Math.random() * 2)
    const nameSuffix = ['Amsterdam', 'Rotterdam', 'Arnhem', 'Zaandam'][
      Math.floor(Math.random() * 4)
    ]
    const date = '2023-10-09'
    const timeBegin = '12:00'
    const timeEnd = '15:00'
    const price = 200
    const locationNames = ['Amsterdam Dam', 'Rotterdam Ahoy', 'Villa Sansbeek', 'Zaandam']
    const locationIndex = Math.floor(Math.random() * 4)
    const info = 'Vind jij duurzaamheid net zo belangrijk als gezelligheid? Kom dan naar de Happy Hospitality Kroegpraat in Arnhem! \nSamen met Kees Bouwhof - eigenaar van Villa Sonsbeek en de Palatijn, de Gemeente Arnhem en een aantal andere ondernemers uit de horeca en evenementenindustrie bespreken we hoe duurzaamheid hen bezighoudt. En gaan we op zoek naar heilige graal als oplossing voor de nieuwe Single Use Plastics wetgeving die sinds dit jaar van kracht is. '

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
      locationNames[locationIndex],
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
