package com.carolin_violet.travel_system.security.handler;

import com.carolin_violet.travel_system.security.TokenManager;
import com.carolin_violet.travel_system.utils.R;
import com.carolin_violet.travel_system.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * 登出
 */
@Component
@Slf4j
public class MyLogoutHandler implements LogoutHandler {

    @Autowired
    private TokenManager tokenJwtManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        // 1、从header中获取token
        String token = httpServletRequest.getHeader("token");
        String userName = tokenJwtManager.getUserFromToken(token);

        // 从redis删除权限列表缓存
        redisTemplate.delete("securityUser:" + userName);
        redisTemplate.delete("token:" + userName);
        ResponseUtil.out(httpServletResponse, R.ok().data("msg", "退出成功"));

    }
}
