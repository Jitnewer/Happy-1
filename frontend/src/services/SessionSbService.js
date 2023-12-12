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
      console.log(response)
      if (response.ok) {
        const body = await response.json()
        return { authorization, body }
      } else {
        console.log(response, !response.bodyUsed ? await response.text() : '')
        return null
      }
    } catch (error) {
      console.error('Error fetching JSON:', error)
      return null
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
      console.log('Response from asyncSignIn:', response)
      this.saveTokenIntoBrowserStorage(response.authorization, user)
      return response
    } catch (e) {
      console.error('Error in asyncFindById:', e)
      return null
    }
  }

  async asyncFindByEmail (email) {
    try {
      // this.saveTokenIntoBrowserStrorage()
      const response = await this.fetchJson(`${this.RESOURCES_URL}/${email}`)
      console.log('Response from asyncFindByEmail:', response)
      return response
    } catch (e) {
      console.error('Error in asyncFindById:', e)
      return null
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
