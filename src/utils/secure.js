import JSEncrypt from 'jsencrypt'
import { getPublickey } from '@/api/user';

export function encryptPassword(content) {
  return new Promise((resolve, reject) => {
    getPublickey().then(res => {
      const publicKey = res.data.publicKey
      const encrypt = new JSEncrypt()      // 创建加密对象实例
      encrypt.setPublicKey(publicKey)       // 设置公钥
      const cipher = encrypt.encrypt(content)      // 加密数据
      resolve(cipher)
    }).catch(err => {
      reject(err)
    })
  })
}

