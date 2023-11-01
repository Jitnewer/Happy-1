import { Event } from '@/models/event'

export class RESTAdaptorWithFetch {
  resourceUrl;
  copyConstructor;
  constructor (resourceUrl, copyConstructor) {
    this.resourceUrl = resourceUrl
    this.copyConstructor = copyConstructor
    console.log(copyConstructor)
    console.log('Created RESTAdaptorWithFetch for ' + resourceUrl)
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

  async asyncFindAll () {
    try {
      const data = await this.fetchJson(this.resourceUrl)
      return data?.map(d => this.copyConstructor(d))
    } catch (error) {
      console.error('Error in asyncFindAll:', error)
      return null
    }
  }

  async asyncFindById (id) {
    try {
      console.log(`${this.resourceUrl}/${id}`)
      const response = await this.fetchJson(`${this.resourceUrl}/${id}`)
      return this.copyConstructor(response)
    } catch (error) {
      console.error('Error in asyncFindById:', error)
      return null
    }
  }

  async asyncSave (event) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${event.id}`, {
        method: 'PUT'
      })
      return this.copyConstructor(response)
    } catch (error) {
      console.error('Error in asyncSave:', error)
      return null
    }
  }

  async asyncDeleteById (id) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${id}`, {
        method: 'DELETE'
      })
      return true
    } catch (error) {
      console.error('Error in asyncDeleteById:', error)
      return false
    }
  }
}