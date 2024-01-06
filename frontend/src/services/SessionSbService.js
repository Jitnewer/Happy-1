import CustomError from '@/CustomError'

export class SessionSbService {
  BROWSER_STORAGE_ITEM_NAME;
  RESOURCES_URL;

  constructor (resourceUrl, browserStorageItemName) {
    this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName
    this.RESOURCES_URL = resourceUrl
    this.getTokenFromBrowserStorage()
  }

  async fetchJson (url, options = null) {
    try {
      const response = await fetch(url, options)
      const authorization = response.headers.get('Authorization') // Access the headers

      if (response.ok) {
        const body = await response.json()
        return { authorization, body }
      } else {
        const responseBody = !response.bodyUsed ? await response.text() : ''

        console.log(response)

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
        throw new CustomError('Forbidden: You don\'t have permission to access this resource', error.status, error)
      } else {
        throw new CustomError('Error fetching data', error.status || 500, error.message)
      }
    }
  }

  async asyncSignIn (user) {
    try {
      // this.saveTokenIntoBrowserStrorage()
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
      throw new CustomError('Error in asyncSignIn', error.status || 500, error.message)
    }
  }

  async asyncRegister (user) {
    try {
      // this.saveTokenIntoBrowserStrorage()
      return await this.fetchJson(`${this.RESOURCES_URL}` + '/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      })
    } catch (error) {
      throw new CustomError('Error in asyncRegister', error.status || 500, error.message)
    }
  }

  async asyncFindByEmail (email) {
    try {
      // this.saveTokenIntoBrowserStrorage()
      return await this.fetchJson(`${this.RESOURCES_URL}/${email}`)
    } catch (error) {
      throw new CustomError('Error in asyncFindByEmail', error.status || 500, error.message)
    }
  }

  signOut () {
    this.saveTokenIntoBrowserStorage(null, null)
  }

  saveTokenIntoBrowserStorage (token, user) {
    if (token !== null && user !== null) {
      // Save token to session storage
      window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token)

      // Check if the token is already in local storage
      if (this.getTokenFromBrowserStorage() !== this.BROWSER_STORAGE_ITEM_NAME) {
        // Save token to local storage
        window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token)

        // Save user details to local storage (you can adjust this based on your needs)
        window.localStorage.setItem('userDetails', JSON.stringify(user))
      }
    } else {
      // If either token or user is null, remove from both storages
      window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME)
      window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME)
      window.localStorage.removeItem('userDetails')
    }
  }

  isAuthenticated () {
    // Check if the token exists in session storage or local storage
    return !!this.getTokenFromBrowserStorage()
  }

  getTokenFromBrowserStorage () {
    return window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME) || window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME)
  }
}