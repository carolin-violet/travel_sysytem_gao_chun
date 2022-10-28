import { JSEncrypt } from 'jsencrypt'

export default ({app: {$axios}}, inject) => {
  inject('RsaEncrypt', (plainText) => {
    return new Promise((resolve, reject) => {
      $axios.get('/tourist/publicKey').then(res => {
        const jsencrypt = new JSEncrypt()
        jsencrypt.setPublicKey(res.data.publicKey)
        const cipher = jsencrypt.encrypt(plainText)
        // console.log('cipher:', cipher);
        resolve(cipher)
      }).catch(err => {
        reject(err)
      })
    })
  })
}
