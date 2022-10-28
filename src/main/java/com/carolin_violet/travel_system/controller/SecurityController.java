package com.carolin_violet.travel_system.controller;

import com.carolin_violet.travel_system.utils.R;
import com.carolin_violet.travel_system.utils.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SecurityController
 * @Description 生成RSA公私钥对
 * @Author zj
 * @Date 2022/9/2 17:09
 * @Version 1.0
 */
@RestController
@RequestMapping("/travel_system/security")
@CrossOrigin
public class SecurityController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 获取RSA公钥
    @GetMapping("publicKey")
    public R getPublicKey(HttpServletRequest httpServletRequest) {
        try {
            String publicKey = (String) redisTemplate.opsForValue().get(httpServletRequest.getRemoteAddr() + "-publicKey:");
            if (publicKey == null) {
                // 生成base64编码后的公私密钥对
                HashMap<String, String> keyPairMap = RsaUtils.getKeyPairMap();
                String privateKey = keyPairMap.get("privateKey");
                publicKey =  keyPairMap.get("publicKey");
                redisTemplate.opsForValue().set(httpServletRequest.getRemoteAddr() + "-publicKey:", privateKey, 1, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(httpServletRequest.getRemoteAddr() + "-privateKey:", privateKey, 1, TimeUnit.MINUTES);
            }
            return R.ok().data("publicKey", publicKey);
        }catch (Exception e) {
            e.printStackTrace();
            return R.error().message("秘钥生成失败");
        }
    }
}
