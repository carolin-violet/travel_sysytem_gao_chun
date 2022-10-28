const NodeRSA = require('node-rsa');

function generateKeys() {
  const key = new NodeRSA({b: 1024});
  //指定加密格式  不改格式得话可能会报错
  key.setOptions({ encryptionScheme: 'pkcs1' });
  const publicKey = key.exportKey('pkcs8-public-pem');
  const privateKey = key.exportKey('pkcs8-private-pem');

  return { publicKey, privateKey }
}


function encrypt(plainText, publicKey) {
  const encryptObj = new NodeRSA(publicKey,'pkcs8-public-pem')
  encryptObj.setOptions({ encryptionScheme: 'pkcs1' })
  return encryptObj.encrypt(plainText)
}

function decrypt(cipherText, privateKey) {
  const decryptObj = new NodeRSA(privateKey,'pkcs8-private-pem')
  decryptObj.setOptions({ encryptionScheme: 'pkcs1' })
  return decryptObj.decrypt(cipherText, 'utf-8')
}


module.exports = { generateKeys, encrypt, decrypt }
