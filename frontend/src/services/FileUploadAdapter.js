export class FileUploadAdapter {
  resourceUrl;
  copyConstructor;
  constructor (resourceUrl) {
    this.resourceUrl = resourceUrl
    console.log('Created UserEventAdapter for ' + resourceUrl)
  }

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

      return response
    } catch (error) {
      console.error('Error uploading file: ' + error)
    }
  }

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

  async asyncUploadEventPic (file, eventId) {
    try {
      const formData = new FormData()
      formData.append('file', file)
      formData.append('eventId', eventId)

      const response = await this.request(formData, '/eventPic')

      return response.json()
    } catch (error) {
      console.error('Error uploading image: ', error)
    }
  }

  async asyncUploadResearchPic (file, researchId) {
    try {
      const formdata = new FormData()
      formdata.append('file', file)
      formdata.append('researchId', researchId)

      const response = await this.request(formdata, '/researchPic')

      return response.json()
    } catch (error) {
      console.error('Error uploading image: ', error)
    }
  }

  async asyncUploadCarouselPic (file, carouselId) {
    try {
      const formdata = new FormData()
      formdata.append('file', file)
      formdata.append('carouselId', carouselId)
      console.log('ik ben carouselId in fileupload ' + carouselId)
      console.log(formdata)
      console.log(this.request(formdata, '/carouselPic'))
      const response = await this.request(formdata, '/carouselPic')
      return response.json()
    } catch (error) {
      console.error('Error uploading image: ', error)
    }
  }

  async asyncUploadChallengePic (file, challengeId) {
    try {
      const formdata = new FormData()
      formdata.append('file', file)
      formdata.append('challengeId', challengeId)

      const response = await this.request(formdata, '/challengePic')
      return response.json()
    } catch (error) {
      console.error('Error uploading image: ', error)
    }
  }

  async asyncUploadNetworkPic (file, networkId) {
    try {
      const formdata = new FormData()
      formdata.append('file', file)
      formdata.append('networkId', networkId)

      const response = await this.request(formdata, '/networkPic')

      return response.json()
    } catch (error) {
      console.error('Error uploading image: ', error)
    }
  }

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
