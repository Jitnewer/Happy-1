import { FileUploadAdapter } from '@/services/FileUploadAdapter'
import CONFIG from '../../app-config'

const fileUploadService = new FileUploadAdapter(CONFIG.BACKEND_URL + '/image')

beforeEach(() => {
  console.log('before each')
})

it('should upload profile picture', async () => {
  // const file = new File([''], 'filename', { type: 'image/png' })
  // const user = {
  //   firstname: 'firstname',
  //   id: 1
  // }
  // const response = await fileUploadService.asyncUploadProfilePic(file, user)
  // console.log('response: ' + response)
  // expect(response).toBeDefined()
  console.log('test')
})
