package com.carolin_violet.travel_system.security.handler;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录验证失败处理器  (没用)
 */
@Component
public class LoginFailedHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Map<String,Object> resultMap = new HashMap<>();
        // 保存数据
        resultMap.put("code","1000");
        resultMap.put("msg","当前账户或密码错误");
        resultMap.put("data",null);

        // 设置返回消息类型
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 返回给请求端
        httpServletResponse.getWriter().write(resultMap.toString());
    }
}
