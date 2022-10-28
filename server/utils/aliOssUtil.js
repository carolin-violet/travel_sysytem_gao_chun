let OSS = require('ali-oss');
const fs = require('fs')
const { v4: uuidv4 } = require('uuid');

let client = new OSS({
  // yourRegion填写Bucket所在地域。以华东1（杭州）为例，Region填写为oss-cn-hangzhou。
  region: 'oss-cn-nanjing',
  // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
  accessKeyId: '',
  accessKeySecret: '',
  bucket: 'edu-19527'
});

// 上传一张图片
async function uploadPhoto (file, folderName) {
  try {
    // 使用'chunked encoding'。使用putStream接口时，SDK默认会发起一个'chunked encoding'的HTTP PUT请求。
    // 填写本地文件的完整路径，从本地文件中读取数据流。
    // 如果本地文件的完整路径中未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
    let stream = fs.createReadStream(file.filepath);

    const uuid = uuidv4().split('-').join("")
    const path = `${folderName}/${uuid}-${file.originalFilename}`

    // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
    let result = await client.putStream(path, stream);
    console.log(result);
    return result.url
  } catch (e) {
    console.log(e)
  }
}

// 删除一张图片
async function deletePhoto (filePath) {
  try {
    // 填写Object完整路径。Object完整路径中不能包含Bucket名称。
    let result = await client.delete(filePath);
    console.log(result);
    return result
  } catch (e) {
    console.log(e);
  }
}

// 删除多张图片
async function deletePhotos (pathList) {
  try {
    let result = await client.deleteMulti(pathList, {quiet: true});
    console.log(result);
    return result
  } catch (e) {
    console.log(e);
  }
}

module.exports = { uploadPhoto, deletePhoto, deletePhotos }
