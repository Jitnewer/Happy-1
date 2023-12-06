export class RESTAdaptorWithFetch {
  resourceUrl;
  copyConstructor;
  constructor (resourceUrl, copyConstructor) {
    this.resourceUrl = resourceUrl
    this.copyConstructor = copyConstructor
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
      const response = await this.fetchJson(`${this.resourceUrl}/${id}`)
      return this.copyConstructor(response)
    } catch (error) {
      console.error('Error in asyncFindById:', error)
      return null
    }
  }

  async asyncAddEntityToEntity (id1, id2, url) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${url}/${id1}/${id2}`, {
        method: 'POST'
      })
      return this.copyConstructor(response)
    } catch (error) {
      console.error('Error in asyncAddEntityToEntity:', error)
      return null
    }
  }

  async asyncRemoveEntityFromEntity (id1, id2, url) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${url}/${id1}/${id2}`, {
        method: 'DELETE'
      })
      return true
    } catch (error) {
      console.error('Error in asyncRemoveEntityToEntity:', error)
      return null
    }
  }

  async asyncFindByColumn (column, url) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${url}/${column}`)
      return this.copyConstructor(response)
    } catch (error) {
      console.error('Error in asyncAddEntityToEntity:', error)
      return null
    }
  }

  async asyncGetCount (id1, url) {
    try {
      return await this.fetchJson(`${this.resourceUrl}/${url}/${id1}`)
    } catch (error) {
      console.error('Error in asyncAddEntityToEntity:', error)
      return null
    }
  }

  async asyncHasEntityEntity (id1, id2, url) {
    try {
      console.log(`${this.resourceUrl}/${url}/${id1}/${id2}`)
      return await this.fetchJson(`${this.resourceUrl}/${url}/${id1}/${id2}`)
    } catch (error) {
      console.error('Error in asyncAddEntityToEntity:', error)
    }
  }

  async asyncSave (object) {
    let response
    try {
      if (object.id === 0) {
        response = await this.fetchJson(this.resourceUrl, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(object)
        })
      } else {
        response = await this.fetchJson(`${this.resourceUrl}/${object.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(object)
        })
      }

      return this.copyConstructor(response)
    } catch (error) {
      console.error('Error in asyncUpdate:', error)
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
