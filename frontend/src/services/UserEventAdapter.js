export class UserEventAdapter {
  resourceUrl;
  copyConstructor;
  constructor (resourceUrl, copyConstructor) {
    this.resourceUrl = resourceUrl
    this.copyConstructor = copyConstructor
    console.log('Created UserEventAdapter for ' + resourceUrl)
  }

  async fetchJson (url, options = null) {
    try {
      const response = await fetch(url, options)
      if (response.ok) {
        return await response.json()
      } else {
        console.log(response, !response.bodyUsed ? await response.text() : '')
        return null
      }
    } catch (error) {
      console.error('Error fetching JSON:', error)
      return null
    }
  }

  async asyncFindEventByUser (userId) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/userevents/eventsByUser/${userId}`)
      return response.map(event => this.copyConstructor(event))
    } catch (error) {
      console.error('Error in asyncFindEventByUser:', error)
      return null
    }
  }
}
