import 'whatwg-fetch'
import fetchIntercept from 'fetch-intercept'
import CustomError from '@/CustomError'
export class FetchInterceptor {
  static theInstance;
  session;
  router;
  unregister;

  constructor (session, router) {
    this.session = session
    this.router = router

    FetchInterceptor.theInstance = this

    this.unregister = FetchInterceptor.register(this)
    console.log('FetchInterceptor has been registered, current token = ', FetchInterceptor.theInstance.session.getTokenFromBrowserStorage())
  }

  static register (instance) {
    return fetchIntercept.register({
      async response (response) {
        if (response.status === 401) {
          // Redirect to '/sign-out' in case of a 401 Unauthorized error
          FetchInterceptor.theInstance.session.saveTokenIntoBrowserStorage(null, null)
          FetchInterceptor.theInstance.router.push({ name: 'login' })
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
