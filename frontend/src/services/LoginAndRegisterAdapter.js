export class LoginAndRegisterAdapter {
  resourceUrl;
  copyConstructor;
  constructor (resourceUrl, copyConstructor) {
    this.resourceUrl = resourceUrl

    this.copyConstructor = copyConstructor
    console.log('Created LoginAndRegisterAdapter')
  }

  async fetchJson (url, options = null) {
    try {
      const response = await fetch(url, options)
      if (response.ok) {
        return await response.json()
      } else {
        let errorMessage
        try {
          errorMessage = JSON.parse(await response.text()).message
        } catch (parseError) {
          errorMessage = 'Failed to parse error message'
        }

        // Return an object with error information
        return {
          error: true,
          status: response.status,
          message: errorMessage
        }
      }
    } catch (error) {
      console.error('Error fetching JSON:', error)
      return null
    }
  }

  async asyncRegister (user) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      })

      return this.copyConstructor(response)
    } catch (error) {
      console.error('Error in asyncCreate:', error)
      return null
    }
  }

  async asyncLogin (email, password) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/login/${email}/${password}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }
      })
      return this.copyConstructor(response)
    } catch (error) {
      console.error('Error in asyncLogin:', error)
      return null
    }
  }

  async asyncFindByEmail (email) {
    try {
      const response = await this.fetchJson(`${this.resourceUrl}/users/mail/${email}`)
      return this.copyConstructor(response)
    } catch (error) {
      console.error('Error in asyncFindByEmail:', error)
      return null
    }
  }
}
