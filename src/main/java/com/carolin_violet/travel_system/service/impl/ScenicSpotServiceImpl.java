package com.carolin_violet.travel_system.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.carolin_violet.travel_system.bean.ScenicSpot;
import com.carolin_violet.travel_system.mapper.ScenicSpotMapper;
import com.carolin_violet.travel_system.service.ScenicSpotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carolin_violet.travel_system.utils.OssPropertiesUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-22
 */
@Service
public class ScenicSpotServiceImpl extends ServiceImpl<ScenicSpotMapper, ScenicSpot> implements ScenicSpotService {

    @Override
    public void removePhoto(String picture) {
        String endpoint = OssPropertiesUtils.END_POINT;
        String accessKeyId = OssPropertiesUtils.KEY_ID;
        String accessKeySecret = OssPropertiesUtils.KEY_SECRET;
        String bucketName = OssPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.deleteObject(bucketName, picture.replace("https://edu-19527.oss-cn-nanjing.aliyuncs.com/", ""));

        ossClient.shutdown();
    }
}
