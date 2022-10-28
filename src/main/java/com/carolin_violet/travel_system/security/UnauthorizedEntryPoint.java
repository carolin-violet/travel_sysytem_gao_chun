package com.carolin_violet.travel_system.security;

import com.carolin_violet.travel_system.utils.R;
import com.carolin_violet.travel_system.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 未授权的统一处理方式
 * </p>
 *
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        System.out.println("======================UnAuth==============================");
        ResponseUtil.out(response, R.error());
    }
}
