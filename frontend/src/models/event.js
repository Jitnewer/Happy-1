/**
 * Represents an event.
 * @class
 */
export class Event {
  /**
   * Constructor for creating an Event object.
   * @constructor
   * @param {number} id - The unique identifier of the event.
   * @param {{}} image - The image URL or path of the event.
   * @param {string} name - The name of the event.
   * @param {string} city - The city where the event takes place.
   * @param {string} date - The date of the event.
   * @param {string} timeBegin - The starting time of the event.
   * @param {string} timeEnd - The ending time of the event.
   * @param {number|null} price - The price of the event (null if free).
   * @param {string} location - The location or venue of the event.
   * @param {string} info - Additional information about the event.
   * @param {number} size - The size or capacity of the event.
   */
  constructor (id, image, name, city, date, timeBegin, timeEnd, price, location, info, size) {
    this.id = id
    this.image = image
    this.name = name
    this.city = city
    this.date = date
    this.timeBegin = timeBegin
    this.timeEnd = timeEnd
    this.price = price
    this.location = location
    this.info = info
    this.size = size
  }

  /**
   * Gets the date of the event.
   * @returns {string} The date of the event.
   */
  getDate () {
    return this.date
  }

  /**
   * Creates a sample event based on the given index.
   * @static
   * @param {number} index - The index to select sample event details.
   * @returns {Event} A new Event object with sample data.
   */
  static createSampleEvents (index) {
    const id = Math.floor(Math.random() * 100) + 1
    const imageIndex = Math.floor(Math.random() * 2)
    const nameSuffix = ['Amsterdam', 'Rotterdam', 'Arnhem', 'Zaandam', 'Utrecht', 'Edam', 'Purmerend', 'Brabant', 'Assen', 'Den Haag', 'Haarlem', 'Hilversum'][index]
    const date = ['2023-10-09', '2023-10-09', '2023-12-09', '2024-01-09', '2024-01-25', '2024-01-25', '2024-01-25', '2024-08-20', '2024-04-06', '2024-03-29', '2024-10-7'][index]
    const timeBegin = '12:00'
    const timeEnd = '15:00'
    const price = [null, null, null, 50, null, null, null, 50, null, null, null, 50, null, null, null, 50][index]
    const location = ['Amsterdam Dam', 'Rotterdam Ahoy', 'Villa Sansbeek', 'Zaandam', 'Utrecht', 'Edam', 'Purmerend', 'Brabant', 'Assen', 'Den Haag', 'Haarlem', 'Hilversum'][index]
    const info = [
      'Are you a supporter of sustainability and a good time? Then we warmly invite you to the Green Gathering in Amsterdam! Alongside experts like Sarah Groenewoud from EcoEats, the Municipality of Utrecht, and various other businesses in the food sector and events industry, we will engage in discussions about how sustainability plays a pivotal role in their endeavors. Together, we will explore innovative solutions to comply with the latest environmental regulations and combat plastic pollution.',
      "Do you share a passion for both sustainability and a good time? If so, we invite you to the Eco-Friendly Hangout in Rotterdam! Join us alongside environmental enthusiasts, including local eco-leaders, the City of Amsterdam, and fellow businesses in the hospitality and events sectors, as we delve into the importance of sustainability in our endeavors. Together, we'll embark on a quest to find innovative solutions to meet the latest Single Use Plastics regulations that came into force this year.",
      'Do you consider sustainability as important as having a good time? Then join us at the Happy Hospitality Pub Talk in Arnhem! Together with Kees Bouwhof, owner of Villa Sonsbeek and the Palatijn, the Municipality of Arnhem, and several other entrepreneurs from the hospitality and events industry, we will discuss how sustainability is at the forefront of their concerns. We will also search for the holy grail of solutions to address the new Single Use Plastics legislation that came into effect earlier this year.',
      "Do you believe in the importance of sustainability while enjoying a great atmosphere? Then don't miss out on the Sustainable Social Gathering in Arnhem! Together with industry leaders like Emily Greenfield from Green Eats, the Rotterdam City Council, and several other hospitality and event professionals, we will discuss the impact of sustainability on our businesses. Additionally, we'll be on the hunt for the ultimate solution to comply with the new Single Use Plastics legislation that has been in effect since the beginning of the year.",
      'Are you a supporter of sustainability and a good time? Then we warmly invite you to the Green Gathering in Amsterdam! Alongside experts like Sarah Groenewoud from EcoEats, the Municipality of Utrecht, and various other businesses in the food sector and events industry, we will engage in discussions about how sustainability plays a pivotal role in their endeavors. Together, we will explore innovative solutions to comply with the latest environmental regulations and combat plastic pollution.',
      "Do you share a passion for both sustainability and a good time? If so, we invite you to the Eco-Friendly Hangout in Rotterdam! Join us alongside environmental enthusiasts, including local eco-leaders, the City of Amsterdam, and fellow businesses in the hospitality and events sectors, as we delve into the importance of sustainability in our endeavors. Together, we'll embark on a quest to find innovative solutions to meet the latest Single Use Plastics regulations that came into force this year.",
      'Do you consider sustainability as important as having a good time? Then join us at the Happy Hospitality Pub Talk in Arnhem! Together with Kees Bouwhof, owner of Villa Sonsbeek and the Palatijn, the Municipality of Arnhem, and several other entrepreneurs from the hospitality and events industry, we will discuss how sustainability is at the forefront of their concerns. We will also search for the holy grail of solutions to address the new Single Use Plastics legislation that came into effect earlier this year.',
      "Do you believe in the importance of sustainability while enjoying a great atmosphere? Then don't miss out on the Sustainable Social Gathering in Arnhem! Together with industry leaders like Emily Greenfield from Green Eats, the Rotterdam City Council, and several other hospitality and event professionals, we will discuss the impact of sustainability on our businesses. Additionally, we'll be on the hunt for the ultimate solution to comply with the new Single Use Plastics legislation that has been in effect since the beginning of the year.",
      'Are you a supporter of sustainability and a good time? Then we warmly invite you to the Green Gathering in Amsterdam! Alongside experts like Sarah Groenewoud from EcoEats, the Municipality of Utrecht, and various other businesses in the food sector and events industry, we will engage in discussions about how sustainability plays a pivotal role in their endeavors. Together, we will explore innovative solutions to comply with the latest environmental regulations and combat plastic pollution.',
      "Do you share a passion for both sustainability and a good time? If so, we invite you to the Eco-Friendly Hangout in Rotterdam! Join us alongside environmental enthusiasts, including local eco-leaders, the City of Amsterdam, and fellow businesses in the hospitality and events sectors, as we delve into the importance of sustainability in our endeavors. Together, we'll embark on a quest to find innovative solutions to meet the latest Single Use Plastics regulations that came into force this year.",
      'Do you consider sustainability as important as having a good time? Then join us at the Happy Hospitality Pub Talk in Arnhem! Together with Kees Bouwhof, owner of Villa Sonsbeek and the Palatijn, the Municipality of Arnhem, and several other entrepreneurs from the hospitality and events industry, we will discuss how sustainability is at the forefront of their concerns. We will also search for the holy grail of solutions to address the new Single Use Plastics legislation that came into effect earlier this year.',
      "Do you believe in the importance of sustainability while enjoying a great atmosphere? Then don't miss out on the Sustainable Social Gathering in Arnhem! Together with industry leaders like Emily Greenfield from Green Eats, the Rotterdam City Council, and several other hospitality and event professionals, we will discuss the impact of sustainability on our businesses. Additionally, we'll be on the hunt for the ultimate solution to comply with the new Single Use Plastics legislation that has been in effect since the beginning of the year.",
      'Are you a supporter of sustainability and a good time? Then we warmly invite you to the Green Gathering in Amsterdam! Alongside experts like Sarah Groenewoud from EcoEats, the Municipality of Utrecht, and various other businesses in the food sector and events industry, we will engage in discussions about how sustainability plays a pivotal role in their endeavors. Together, we will explore innovative solutions to comply with the latest environmental regulations and combat plastic pollution.',
      "Do you share a passion for both sustainability and a good time? If so, we invite you to the Eco-Friendly Hangout in Rotterdam! Join us alongside environmental enthusiasts, including local eco-leaders, the City of Amsterdam, and fellow businesses in the hospitality and events sectors, as we delve into the importance of sustainability in our endeavors. Together, we'll embark on a quest to find innovative solutions to meet the latest Single Use Plastics regulations that came into force this year.",
      'Do you consider sustainability as important as having a good time? Then join us at the Happy Hospitality Pub Talk in Arnhem! Together with Kees Bouwhof, owner of Villa Sonsbeek and the Palatijn, the Municipality of Arnhem, and several other entrepreneurs from the hospitality and events industry, we will discuss how sustainability is at the forefront of their concerns. We will also search for the holy grail of solutions to address the new Single Use Plastics legislation that came into effect earlier this year.',
      "Do you believe in the importance of sustainability while enjoying a great atmosphere? Then don't miss out on the Sustainable Social Gathering in Arnhem! Together with industry leaders like Emily Greenfield from Green Eats, the Rotterdam City Council, and several other hospitality and event professionals, we will discuss the impact of sustainability on our businesses. Additionally, we'll be on the hunt for the ultimate solution to comply with the new Single Use Plastics legislation that has been in effect since the beginning of the year."
    ][index]
    const size = 20
    return new Event(
      id,
      imageIndex === 0
        ? require('../assets/eventPic/rotterdam-event.webp')
        : require('../assets/eventPic/rotterdam-event.webp'),
      `Kroegpraat @ ${nameSuffix}`,
      nameSuffix,
      date,
      timeBegin,
      timeEnd,
      price,
      location,
      info,
      size
    )
  }

  /**
   * Generates a random beginning time for an event.
   * @static
   * @returns {string} The random starting time in HH:mm format.
   */
  static getRandomTimeBegin () {
    const minHour = 11
    const maxHour = 20

    const randomHour = Math.floor(Math.random() * (maxHour - minHour + 1)) + minHour
    const randomMinute = Math.floor(Math.random() * 6) * 10 // Generate random multiple of 10

    return `${String(randomHour).padStart(2, '0')}:${String(randomMinute).padStart(2, '0')}`
  }

  /**
   * Generates a random ending time for an event based on the starting time.
   * @static
   * @param {string} timeBegin - The starting time of the event.
   * @returns {string} The random ending time in HH:mm format.
   */
  static getRandomTimeEnd (timeBegin) {
    const timeBeginParts = timeBegin.split(':')
    const beginHour = parseInt(timeBeginParts[0])
    const beginMinute = parseInt(timeBeginParts[1])

    const maxHour = Math.min(beginHour + 2, 22)
    const maxMinute = beginMinute

    const randomHour = Math.floor(Math.random() * (maxHour - beginHour + 1)) + beginHour
    const randomMinute = Math.floor(Math.random() * (maxMinute - beginMinute + 1)) + beginMinute

    // Round randomMinute to nearest multiple of 10
    const roundedMinute = Math.round(randomMinute / 10) * 10

    return `${String(randomHour).padStart(2, '0')}:${String(roundedMinute).padStart(2, '0')}`
  }

  /**
   * Generates a random date for an event within the next year.
   * @static
   * @returns {string} The random date in "dd-mm-yyyy" format.
   */
  static getRandomDate () {
    const today = new Date()
    const nextYear = new Date(today)
    nextYear.setFullYear(nextYear.getFullYear() + 1)

    const randomTimestamp = today.getTime() + Math.random() * (nextYear.getTime() - today.getTime())
    const randomDate = new Date(randomTimestamp)

    const day = randomDate.getDate()
    const month = randomDate.getMonth() + 1 // Add 1 to the month value
    const year = randomDate.getFullYear()

    return `${day}-${month}-${year}`
  }

  /**
   * Generates a random price for an event.
   * @static
   * @returns {number|null} The random price or null for free events.
   */
  static getRandomPrice () {
    const minPrice = 40
    const maxPrice = 80

    // Generate a random number between 0 and 1
    const randomValue = Math.random()

    // If the random value is below 0.1 (adjust this threshold as needed), return null
    if (randomValue < 0.5) {
      return null
    }

    // Otherwise, proceed with generating a random price
    let randomPrice = Math.floor(Math.random() * (maxPrice - minPrice + 1)) + minPrice

    const lastDigit = randomPrice % 10

    if (lastDigit !== 5 && lastDigit !== 0 && lastDigit !== 9) {
      if (lastDigit < 5) {
        randomPrice += 5 - lastDigit
      } else {
        randomPrice += 10 - lastDigit
      }
    }

    return randomPrice
  }

  /**
   * Checks if two events are equal by comparing their properties.
   * @static
   * @param {Event} event - The first event for comparison.
   * @param {Event} eventCopy - The second event for comparison.
   * @returns {boolean} True if the events are equal, false otherwise.
   */
  static equals (event, eventCopy) {
    return (
      eventCopy.name === event.name &&
      eventCopy.city === event.city &&
      eventCopy.date === event.date &&
      eventCopy.timeBegin === event.timeBegin &&
      eventCopy.timeEnd === event.timeEnd &&
      eventCopy.price === event.price &&
      eventCopy.location === event.location &&
      eventCopy.info === event.info
    )
  }

  /**
   * Creates a copy of an existing event using a copy constructor.
   * @static
   * @param {Event} event - The event to be copied.
   * @returns {Event|null} A new Event object that is a copy of the input event.
   */
  static copyConstructor (event) {
    if (!event) return null

    return new Event(
      event.id,
      event.image,
      event.name,
      event.city,
      event.date,
      event.timeBegin,
      event.timeEnd,
      event.price,
      event.location,
      event.info,
      event.size
    )
  }
}
