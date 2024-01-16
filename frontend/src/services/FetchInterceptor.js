import 'whatwg-fetch'
import fetchIntercept from 'fetch-intercept'
import CustomError from '@/CustomError'

/**
 * Class for intercepting and handling fetch requests and responses.
 */
export class FetchInterceptor {
  static theInstance;
  session;
  router;
  unregister;

  /**
   * Constructor for FetchInterceptor.
   * @param {Object} session - An object representing the user session.
   * @param {Object} router - An object representing the router for navigation.
   */
  constructor (session, router) {
    this.session = session
    this.router = router

    FetchInterceptor.theInstance = this

    // Register the instance for interception
    this.unregister = FetchInterceptor.register(this)
    console.log('FetchInterceptor has been registered, current token = ', FetchInterceptor.theInstance.session.getTokenFromBrowserStorage())
  }

  /**
   * Static method to register the interceptor.
   * @param {FetchInterceptor} instance - An instance of the FetchInterceptor class.
   * @returns {Function} - The unregister function for the interceptor.
   */
  static register (instance) {
    return fetchIntercept.register({
      async response (response) {
        if (response.status === 401) {
          // Redirect to '/sign-out' in case of a 401 Unauthorized error
          FetchInterceptor.theInstance.session.saveTokenIntoBrowserStorage(null, null)
          FetchInterceptor.theInstance.router.push({ name: 'login', query: { signOut: true } })
        } else if (!response.ok) {
          // Handle other error responses
          const errorMessage = await response.text() // You may need to adjust this based on your API response format
          const error = new CustomError(errorMessage)

          // Push to '/error' route with error message as a prop
          FetchInterceptor.theInstance.router.push({ name: 'error', params: { message: errorMessage } })
        }

        return response
      },
      request (url, options) {
        return instance.request(url, options)
      }
    })
  }

  /**
   * Method to handle the request by adding authorization headers.
   * @param {string} url - The URL of the request.
   * @param {Object} options - The options for the request.
   * @returns {Array} - An array containing the modified URL and options.
   */
  request (url, options) {
    const token = FetchInterceptor.theInstance.session.getTokenFromBrowserStorage()

    if (token == null) {
      return [url, options]
    } else if (options == null) {
      return [url, { headers: { Authorization: token } }]
    } else {
      const newOptions = { ...options }
      newOptions.headers = {
        ...newOptions.headers,
        Authorization: token
      }
      return [url, newOptions]
    }
  }
}
