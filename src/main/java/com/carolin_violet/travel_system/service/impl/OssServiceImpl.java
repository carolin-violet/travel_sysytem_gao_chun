package com.carolin_violet.travel_system.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.service.OssService;
import com.carolin_violet.travel_system.utils.OssPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName OssServiceImpl
 * @Description TODO
 * @Author zj
 * @Date 2022/5/23 10:40
 * @Version 1.0
 */
@Service
public class OssServiceImpl implements OssService {

    // 上传图片至阿里云
    @Override
    public String uploadFileAvatar(MultipartFile file, String FolderName) {

        String endpoint = OssPropertiesUtils.END_POINT;
        String accessKeyId = OssPropertiesUtils.KEY_ID;
        String accessKeySecret = OssPropertiesUtils.KEY_SECRET;
        String bucketName = OssPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 获取文件名字
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            fileName = FolderName + "/" + fileName;

            // 创建PutObject请求。
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();

            // 把上传之后文件路径返回
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据url删除图片
    @Override
    public void removeRemotePhoto(String url) {
        String endpoint = OssPropertiesUtils.END_POINT;
        String accessKeyId = OssPropertiesUtils.KEY_ID;
        String accessKeySecret = OssPropertiesUtils.KEY_SECRET;
        String bucketName = OssPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.deleteObject(bucketName, url.replace("https://edu-19527.oss-cn-nanjing.aliyuncs.com/", ""));

        ossClient.shutdown();
    }

}
