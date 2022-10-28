var AipNlpClient = require("baidu-aip-sdk").nlp;

// 设置APPID/AK/SK
var APP_ID = "26374241";
var API_KEY = "";
var SECRET_KEY = "";

// 新建一个对象，建议只保存一个对象调用服务接口
var client = new AipNlpClient(APP_ID, API_KEY, SECRET_KEY);



  // 调用情感倾向分析
function getSentiment(text) {
  return client.sentimentClassify(text).then(function(result) {
      console.log('baidu-res:', JSON.stringify(result));
      return result
  }).catch(function(err) {
      // 如果发生网络错误
      console.log('baidu-err:', err);
      return err
  });
}

module.exports = { getSentiment }
