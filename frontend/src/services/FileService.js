import axios from "axios"

const BASE_URL = "file"

class FileService {
  getAllImages() {
    return axios.get(BASE_URL);
  }

  uploadImage(fileFormData){
    return axios.post(BASE_URL+'/uploadFile', fileFormData);
  }
}

export default new FileService();