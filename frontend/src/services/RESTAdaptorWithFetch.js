import CustomError from '@/CustomError'

/**
 * Class for handling RESTful API operations using fetch.
 */
export class RESTAdaptorWithFetch {
  resourceUrl;
  copyConstructor;

  /**
   * Constructor for RESTAdaptorWithFetch.
   * @param {string} resourceUrl - The base URL for the RESTful API.
   * @param {Function} copyConstructor - A function to copy and transform API responses (optional).
   */
  constructor (resourceUrl, copyConstructor) {
    this.resourceUrl = resourceUrl
    this.copyConstructor = copyConstructor
    console.log('Created RESTAdaptorWithFetch for ' + resourceUrl)
  }

  /**
   * Asynchronously performs a fetch operation and parses the response as JSON.
   * @param {string} url - The URL for the fetch operation.
   * @param {Object} options - The options for the fetch operation (optional).
   * @returns {Promise<Object>} - A promise that resolves to the JSON response.
   */
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
      // Handle fetch errors and parse error responses
      // ...
    }
  }

  // Methods for various API operations...

  /**
   * Asynchronously checks if an entity has a related entity.
   * @param {number} id1 - The ID of the first entity.
   * @param {number} id2 - The ID of the second entity.
   * @param {string} url - The endpoint for checking the relationship.
   * @returns {Promise<Object>} - A promise that resolves to the JSON response.
   */
  async asyncHasEntityEntity (id1, id2, url) {
    try {
      return await this.fetchJson(`${this.resourceUrl}/${url}/${id1}/${id2}`)
    } catch (error) {
      throw new CustomError('Error in asyncAddEntityToEntity', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  /**
   * Asynchronously saves or updates an object.
   * @param {Object} object - The object to save or update.
   * @returns {Promise<Object>} - A promise that resolves to the JSON response.
   */
  async asyncSave (object) {
    let response
    try {
      // Determine whether to perform a POST or PUT based on object ID
      // ...
      return response
    } catch (error) {
      throw new CustomError('Error in asyncSave', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  // Additional methods for other API operations...
}
