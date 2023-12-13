export class FileUploadAdapter {
  resourceUrl;
  copyConstructor;
  constructor (resourceUrl) {
    this.resourceUrl = resourceUrl
    console.log('Created UserEventAdapter for ' + resourceUrl)
  }

  async asyncUploadProfilePic (file, user) {
    try {
      const url = this.resourceUrl + '/profilePic' // Replace with the actual URL of your endpoint
      const formData = new FormData()
      formData.append('file', file)
      formData.append('userName', user.firstname)
      formData.append('userId', user.id)

      const options = {
        method: 'POST',
        body: formData
      }

      const response = await fetch(url, options)

      console.log(response.body)

      return response.json()
    } catch (error) {
      console.error('Error uploading file:', error)
      // Handle the error as needed
    }
  }
}
