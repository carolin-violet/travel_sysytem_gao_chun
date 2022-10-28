package com.carolin_violet.travel_system.controller;

import com.carolin_violet.travel_system.service.OssService;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName OssController
 * @Description TODO
 * @Author zj
 * @Date 2022/5/23 10:38
 * @Version 1.0
 */

@RestController
@RequestMapping("/travel_system/oss/picture")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    // 新增相关物件时添加相关展示图片
    @PostMapping
    public R uploadOssPicture(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file, "travel_GaoChun/pictures");
        return R.ok().data("url", url);
    }

}
