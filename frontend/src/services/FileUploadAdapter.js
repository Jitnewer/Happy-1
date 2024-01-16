/**
 * Class for handling file uploads using fetch API.
 */
export class FileUploadAdapter {
  resourceUrl;
  copyConstructor;

  /**
   * Constructor for FileUploadAdapter.
   * @param {string} resourceUrl - The base URL for file upload operations.
   */
  constructor (resourceUrl) {
    this.resourceUrl = resourceUrl
    console.log('Created FileUploadAdaptor for ' + resourceUrl)
  }

  /**
   * Asynchronously handles the file upload request.
   * @param {FormData} formData - The FormData containing the file and additional parameters.
   * @param {string} map - The endpoint map for the specific file upload operation.
   * @returns {Promise<Response>} - A promise that resolves to the fetch response.
   */
  async request (formData, map) {
    try {
      const url = this.resourceUrl + map

      let options
      if (map === '/delete') {
        options = {
          method: 'DELETE',
          body: formData
        }
      } else {
        options = {
          method: 'POST',
          body: formData
        }
      }

      const response = await fetch(url, options)

      console.log('test: ' + response)
      return response
    } catch (error) {
      console.error('Error uploading file: ' + error)
    }
  }

  /**
   * Asynchronously uploads a profile picture.
   * @param {File} file - The file to upload.
   * @param {Object} user - The user object associated with the profile picture.
   * @returns {Promise<Object>} - A promise that resolves to the JSON response.
   */
  async asyncUploadProfilePic (file, user) {
    try {
      const formData = new FormData()
      formData.append('file', file)
      formData.append('userName', user.firstname)
      formData.append('userId', user.id)

      const response = await this.request(formData, '/profilePic')

      return response.json()
    } catch (error) {
      console.error('Error uploading image: ', error)
    }
  }

  // Similar methods for other file upload operations...

  /**
   * Asynchronously deletes an image.
   * @param {string} imagePath - The path of the image to delete.
   * @returns {Promise<Object>} - A promise that resolves to the JSON response.
   */
  async asyncDeleteImage (imagePath) {
    try {
      const formdata = new FormData()
      formdata.append('imagePath', imagePath)

      const response = await this.request(formdata, '/delete')

      return response.json()
    } catch (error) {
      console.log('Error deleting image: ' + error)
    }
  }
}
