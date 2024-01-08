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

        console.error(responseBody)
      }
    } catch (error) {
      const errorString = error.toString()
      const innerJsonStartIndex = errorString.indexOf('{"')
      const innerJsonEndIndex = errorString.lastIndexOf('"}}') + 2
      const innerJsonSubstring = errorString.substring(innerJsonStartIndex, innerJsonEndIndex)
      const decodedInnerJson = decodeURIComponent(innerJsonSubstring)

      const correctedInnerJsonString = decodedInnerJson.replace('{"message":"{', '{"message":{').replace('}"}', '}}')

      const errorObject = JSON.parse(correctedInnerJsonString + '}')
      const e = errorObject.params.message
      if (e.status === 401) {
        throw new CustomError('Unauthorized: Please log in', e.status, e.message)
      } else if (e.status === 403) {
        throw new CustomError('Forbidden: You don\'t have permission to access this resource', e.status, e.message)
      } else {
        throw new CustomError('Error fetching data', e.status || 500, e.message)
      }
    }
  }

  async asyncFindAll () {
    try {
      const data = await this.fetchJson(this.resourceUrl, {
      })
      return data?.map(d => this.copyConstructor(d))
    } catch (error) {
      throw new CustomError('Error in asyncFindAll', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  async asyncFindById (id) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${id}`)
      return this.copyConstructor(response)
    } catch (error) {
      throw new CustomError('Error in asyncFindById', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  async asyncAddEntityToEntity (id1, id2, url) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${url}/${id1}/${id2}`, {
        method: 'POST'
      })
      return this.copyConstructor(response)
    } catch (error) {
      throw new CustomError('Error in asyncAddEntityToEntity', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  async asyncRemoveEntityFromEntity (id1, id2, url) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${url}/${id1}/${id2}`, {
        method: 'DELETE'
      })
      return true
    } catch (error) {
      throw new CustomError('Error in asyncRemoveEntityFromEntity', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  async asyncFindByColumn (column, url) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/${url}/${column}`)
      return this.copyConstructor(response)
    } catch (error) {
      throw new CustomError('Error in asyncFindByColumn', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  async asyncGetCount (id1, url) {
    try {
      return await this.fetchJson(`${this.resourceUrl}/${url}/${id1}`)
    } catch (error) {
      throw new CustomError('Error in asyncAddEntityToEntity', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  async asyncHasEntityEntity (id1, id2, url) {
    try {
      return await this.fetchJson(`${this.resourceUrl}/${url}/${id1}/${id2}`)
    } catch (error) {
      throw new CustomError('Error in asyncAddEntityToEntity', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  async asyncSave (object) {
    let response
    try {
      if (object.id == null) {
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
      return response
    } catch (error) {
      throw new CustomError('Error in asyncSave', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  async asyncDeleteById (id) {
    // eslint-disable-next-line no-useless-catch
    try {
      return await this.fetchJson(`${this.resourceUrl}/${id}`, {
        method: 'DELETE'
      })
    } catch (error) {
      throw new CustomError('Error in asyncDeleteById', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  async asyncFindByProperty (propertyId, url) {
    try {
      return await this.fetchJson(`${this.resourceUrl}/${url}/${propertyId}`)
    } catch (error) {
      throw new CustomError('Error in asyncFindByProperty', error.toJSON().status || 500, error.toJSON().error)
    }
  }
}
