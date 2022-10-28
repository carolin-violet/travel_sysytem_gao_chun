package com.carolin_violet.travel_system.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 无权访问配置(前后分离)
 */
@Component  // 交给spring管理
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Map<String,Object> resultMap = new HashMap<>();
        // 保存数据
        resultMap.put("code","403");
        resultMap.put("msg","无权访问");
        resultMap.put("data",null);

        // 设置返回消息类型
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 返回给请求端
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(resultMap.toString());
        writer.flush();
//        writer.close();
    }
}
