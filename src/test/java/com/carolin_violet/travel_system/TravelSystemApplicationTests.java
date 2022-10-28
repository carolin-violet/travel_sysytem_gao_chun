package com.carolin_violet.travel_system;

import com.carolin_violet.travel_system.utils.BaiDuSentiment;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class TravelSystemApplicationTests {

    @Test
    void contextLoads() {
        String str = "123456";
        String encodedPassword = BCrypt.hashpw(str,BCrypt.gensalt());
        System.out.println(encodedPassword);

        // 使用正确密码验证密码是否正确
        boolean flag = BCrypt.checkpw(str, encodedPassword);
        System.out.println(flag);

        // 使用错误密码验证密码是否正确
        boolean flag2 = BCrypt.checkpw("566", encodedPassword);
        System.out.println(flag2);

    }

    @Test
    void sentimentTest() {
        JSONObject res = new BaiDuSentiment().getSentimentRes("bilibili太香啦");
        System.out.println(res);
    }


}
