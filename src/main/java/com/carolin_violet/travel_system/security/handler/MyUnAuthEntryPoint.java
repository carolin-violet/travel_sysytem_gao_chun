package com.carolin_violet.travel_system.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 未登录
 */
@Component
@Slf4j
public class MyUnAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("======================UnAuth==============================");
        // 返回请求端
        Map<String,Object> resultMap = new HashMap<>();
        // 保存数据
        resultMap.put("code","10000");
        resultMap.put("msg","当前账户未登录");
        resultMap.put("data",new HashMap<>());

        // 设置返回消息类型
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 返回给请求端
        PrintWriter writer  = httpServletResponse.getWriter();
        writer.write(resultMap.toString());
//        writer.close();
    }
}
