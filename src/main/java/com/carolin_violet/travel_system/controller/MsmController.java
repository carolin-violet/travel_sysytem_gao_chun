package com.carolin_violet.travel_system.controller;

import com.carolin_violet.travel_system.service.MsmService;
import com.carolin_violet.travel_system.utils.R;
import com.carolin_violet.travel_system.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName MsmController
 * @Description TODO
 * @Author zj
 * @Date 2022/5/24 21:41
 * @Version 1.0
 */
@RestController
@RequestMapping("/travel_system/front")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    // 发送短信的方法
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        // 从redis获取验证码，如果获取到就直接返回
        System.out.println(phone);
        String code = redisTemplate.opsForValue().get("msm:" + phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        // 如果redis获取不到，进行阿里云发送
        // 生成随机值，给阿里云发送
        code = RandomUtil.getSixBitRandom();
        // 通过service发送短信
        boolean isSend =msmService.send(code, phone);
        if (isSend) {
            // 发送成功，把发送成功验证码放到redis中并设置有效时间
            redisTemplate.opsForValue().set("msm:" + phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
