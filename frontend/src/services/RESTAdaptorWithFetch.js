import CustomError from '@/CustomError'

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
        const responseBody = !response.bodyUsed ? await response.text() : ''

        if (response.status === 401) {
          throw new CustomError('Unauthorized: Please log in', response.status, responseBody)
        } else if (response.status === 403) {
          throw new CustomError('Forbidden: You don\'t have permission to access this resource', response.status, responseBody)
        } else {
          throw new CustomError('Error with response', response.status, responseBody)
        }
      }
    } catch (error) {
      if (error.status === 401) {
        throw new CustomError('Unauthorized: Please log in', error.status, error.message)
      } else if (error.status === 403) {
        throw new CustomError('Forbidden: You don\'t have permission to access this resource', error.status, error.message)
      } else {
        console.log(error)
        throw new CustomError('Error fetching data', error.status || 500, error.message)
      }
    }
  }

  async asyncFindByMail (mail) {
    try {
      const data = await this.fetchJson(`${this.resourceUrl}/mail/${mail}`)

      return data
    } catch (e) {
      console.log(e)
    }
  }

  async asyncFindAll () {
    try {
      const data = await this.fetchJson(this.resourceUrl, {
      })
      return data?.map(d => this.copyConstructor(d))
    } catch (error) {
      throw new CustomError('Error in asyncFindAll', error.status || 500, error.message)
    }
  }

  async asyncFindById (id) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${id}`)
      return this.copyConstructor(response)
    } catch (error) {
      throw new CustomError('Error in asyncFindById', error.status || 500, error.message)
    }
  }

  async asyncAddEntityToEntity (id1, id2, url) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${url}/${id1}/${id2}`, {
        method: 'POST'
      })
      return this.copyConstructor(response)
    } catch (error) {
      throw new CustomError('Error in asyncAddEntityToEntity', error.status || 500, error.message)
    }
  }

  async asyncRemoveEntityFromEntity (id1, id2, url) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${url}/${id1}/${id2}`, {
        method: 'DELETE'
      })
      return true
    } catch (error) {
      throw new CustomError('Error in asyncRemoveEntityFromEntity', error.status || 500, error.message)
    }
  }

  async asyncFindByColumn (column, url) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${url}/${column}`)
      return this.copyConstructor(response)
    } catch (error) {
      throw new CustomError('Error in asyncFindByColumn', error.status || 500, error.message)
    }
  }

  async asyncGetCount (id1, url) {
    try {
      return await this.fetchJson(`${this.resourceUrl}/${url}/${id1}`)
    } catch (error) {
      console.error('Error in asyncAddEntityToEntity:', error.message)
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
      if (object.id === 0 || !object.id) {
        response = await this.fetchJson(this.resourceUrl, {
          method: 'POST',
          body: JSON.stringify(object),
          headers: {
            'Content-Type': 'application/json'
          }
        })
      } else {
        response = await this.fetchJson(`${this.resourceUrl}/${object.id}`, {
          method: 'PUT',
          body: JSON.stringify(object),
          headers: {
            'Content-Type': 'application/json'
          }
        })
      }

      return this.copyConstructor(response.entity)
    } catch (error) {
      console.log(error)
      throw new CustomError('Error in asyncSave', error.status || 500, error.message)
    }
  }

  async asyncDeleteById (id) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${id}`, {
        method: 'DELETE'
      })
    } catch (error) {
      throw new CustomError('Error in asyncDeleteById', error.status || 500, error.message)
    }
  }

  async asyncFindByProperty (propertyId, url) {
    try {
      return await this.fetchJson(`${this.resourceUrl}/${url}/${propertyId}`)
    } catch (error) {
      throw new CustomError('Error in asyncFindByProperty', error.status || 500, error.message)
    }
  }
}
