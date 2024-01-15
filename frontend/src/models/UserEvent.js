import { User } from '@/models/user'
import { Event } from '@/models/event'

export class UserEvent {
  constructor (id, user, event) {
    this.id = id
    this.user = user
    this.event = event
  }

  static copyConstructor (userEvent) {
    if (!userEvent) {
      return new UserEvent(
        userEvent.id,
        userEvent.user,
        userEvent.event
      )
    }
  }

  /**
   * Creates a sample UserEvent with random user and event details.
   * @static
   * @param {number} index - The index to select sample user and event details.
   * @returns {UserEvent} A new UserEvent object with sample data.
   */
  static createSampleUserEvent (index) {
    const id = Math.floor(Math.random() * 100) + 1
    const user = User.createSampleUser()
    const event = Event.createSampleEvents(index)

    return new UserEvent(id, user, event)
  }
}
