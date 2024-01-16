import CustomError from '@/CustomError'

export class SessionSbService {
  BROWSER_STORAGE_ITEM_NAME;
  RESOURCES_URL;

  constructor (resourceUrl, browserStorageItemName) {
    this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName
    this.RESOURCES_URL = resourceUrl
    this.getTokenFromBrowserStorage()
  }

  /**
   * Asynchronously performs a fetch operation and parses the response as JSON.
   * Handles authentication errors and converts them to CustomError.
   * @param {string} url - The URL for the fetch operation.
   * @param {Object} options - The options for the fetch operation (optional).
   * @returns {Promise<Object>} - A promise that resolves to the JSON response.
   */
  async fetchJson (url, options = null) {
    try {
      const response = await fetch(url, options)
      const authorization = response.headers.get('Authorization') // Access the headers

      if (response.ok) {
        const body = await response.json()
        return { authorization, body }
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

  /**
   * Asynchronously signs in a user.
   * @param {Object} user - The user object containing login credentials.
   * @returns {Promise<Object>} - A promise that resolves to the JSON response.
   */
  async asyncSignIn (user) {
    try {
      const response = await this.fetchJson(`${this.RESOURCES_URL}` + '/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      })

      this.saveTokenIntoBrowserStorage(response.authorization, user)
      return response
    } catch (error) {
      throw new CustomError('Error in asyncSignIn', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  /**
   * Asynchronously registers a new user.
   * @param {Object} user - The user object containing registration details.
   * @returns {Promise<Object>} - A promise that resolves to the JSON response.
   */
  async asyncRegister (user) {
    try {
      return await this.fetchJson(`${this.RESOURCES_URL}` + '/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      })
    } catch (error) {
      throw new CustomError('Error in asyncRegister', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  /**
   * Asynchronously retrieves user details by email.
   * @param {string} email - The email address of the user to retrieve.
   * @returns {Promise<Object>} - A promise that resolves to the JSON response.
   */
  async asyncFindByEmail (email) {
    try {
      return await this.fetchJson(`${this.RESOURCES_URL}/${email}`)
    } catch (error) {
      throw new CustomError('Error in asyncFindByEmail', error.status || 500, error.message)
    }
  }

  /**
   * Signs out the current user by removing the token from browser storage.
   */
  signOut () {
    this.saveTokenIntoBrowserStorage(null, null)
  }

  /**
   * Saves the authentication token and user details into browser storage.
   * @param {string|null} token - The authentication token to save.
   * @param {Object|null} user - The user details to save.
   */
  saveTokenIntoBrowserStorage (token, user) {
    // Logic for saving tokens and user details into browser storage...
  }

  /**
   * Checks if the user is authenticated by checking the existence of a token.
   * @returns {boolean} - True if the user is authenticated, false otherwise.
   */
  isAuthenticated () {
    return !!this.getTokenFromBrowserStorage()
  }

  /**
   * Retrieves the authentication token from browser storage.
   * @returns {string|null} - The authentication token, or null if not found.
   */
  getTokenFromBrowserStorage () {
    return window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME) || window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME)
  }
}
