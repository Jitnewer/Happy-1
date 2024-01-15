// Import the Event class
import { Event } from './event.js'

const EVENT_ID = 1
const EVENT_IMAGE = 'sample-image.jpg'
const EVENT_NAME = 'Sample Event'
const EVENT_CITY = 'Sample City'
const EVENT_DATE = '2024-01-15'
const EVENT_TIME_BEGIN = '12:00'
const EVENT_TIME_END = '15:00'
const EVENT_PRICE = 50
const EVENT_LOCATION = 'Sample Location'
const EVENT_INFO = 'Sample Event Info'
const EVENT_SIZE = 20
let event
describe('Event class', () => {
  beforeEach(() => {
    event = new Event(
      EVENT_ID,
      EVENT_IMAGE,
      EVENT_NAME,
      EVENT_CITY,
      EVENT_DATE,
      EVENT_TIME_BEGIN,
      EVENT_TIME_END,
      EVENT_PRICE,
      EVENT_LOCATION,
      EVENT_INFO,
      EVENT_SIZE
    )
  })
  test('creates an instance of Event and checks its methods', () => {
    expect(event, 'event is not an instance of event').toBeInstanceOf(Event)

    expect(event.getDate(), 'event date is not the same as date').toEqual(EVENT_DATE)
  })

  test('creates a sample event using createSampleEvents method', () => {
    const sampleEvent = Event.createSampleEvents(0)

    expect(sampleEvent, 'sampleEvent is not an instance of Event').toBeInstanceOf(Event)
    expect(sampleEvent.getDate(), 'sampleEvent date is not defined ').toBeDefined()
  })

  test('checks getRandomTimeBegin method', () => {
    const randomTimeBegin = Event.getRandomTimeBegin()

    expect(randomTimeBegin, 'timeBegin does not match regex').toMatch(/^\d{2}:\d{2}$/)
  })

  test('checks getRandomTimeEnd method', () => {
    const randomTimeBegin = '12:00'
    const randomTimeEnd = Event.getRandomTimeEnd(randomTimeBegin)

    expect(randomTimeEnd, 'timeEnd does not match regex').toMatch(/^\d{2}:\d{2}$/)
  })

  test('checks getRandomDate method', () => {
    const randomDate = Event.getRandomDate()

    expect(randomDate, 'date does not match regex').toMatch(/^\d{1,2}-\d{1,2}-\d{4}$/)
  })

  test('checks getRandomPrice method', () => {
    const randomPrice = Event.getRandomPrice()

    if (randomPrice !== null) {
      expect(randomPrice, 'price is greater or equal then 40').toBeGreaterThanOrEqual(40)
      expect(randomPrice, 'price is less or equal then 80').toBeLessThanOrEqual(80)
    } else {
      expect(randomPrice, 'price is null').toBeNull()
    }
  })

  test('checks equals method', () => {
    const eventCopy = Event.copyConstructor(event)

    expect(Event.equals(event, eventCopy), 'Event is the same as eventCopy').toBeTruthy()
  })

  test('checks copyConstructor method', () => {
    const copiedEvent = Event.copyConstructor(event)

    expect(copiedEvent, 'copyEvent is the same as event').toEqual(event)
  })
})
