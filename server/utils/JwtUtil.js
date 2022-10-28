const jwt = require('jsonwebtoken')

const secret = 'zj-yyds'

// content为内容，必须传一个对象,expiresTime可以直接传数值，单位默认为s，也可以传例如"2 days", "10h", "7d"
const generateToken = (content, expiresTime) => {
  return jwt.sign(content, secret, {
    expiresIn: expiresTime
  })
}


const checkToken = (token) => {
  return jwt.verify(token, secret, (error, decode) => {
    if (error) {
      console.log("token-error:", error);
      return undefined
    } else {
      return decode
    }
  })
}

module.exports = { generateToken, checkToken }