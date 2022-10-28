package com.carolin_violet.travel_system.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.carolin_violet.travel_system.bean.Delicacy;
import com.carolin_violet.travel_system.mapper.DelicacyMapper;
import com.carolin_violet.travel_system.service.DelicacyService;
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
public class DelicacyServiceImpl extends ServiceImpl<DelicacyMapper, Delicacy> implements DelicacyService {

    // 在删除整条记录前先删除oss相应照片
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
