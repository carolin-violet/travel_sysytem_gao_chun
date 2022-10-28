package com.carolin_violet.travel_system.filter;

import com.carolin_violet.travel_system.bean.security.SecurityUser;
import com.carolin_violet.travel_system.security.TokenManager;
import com.carolin_violet.travel_system.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @ClassName JwtVerifyFilter
 * @Description 权限分配
 * @Author zj
 * @Date 2022/5/24 12:38
 * @Version 1.0
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取当前认证成功用户权限信息
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request, response);
        if(authRequest != null){
            // 有权限，则放入权限上下文中
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }
        // 执行下一个 filter 过滤器链
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        //从header获取token
        String token = request.getHeader("token");
        if(token != null) {
            //从token获取手机号
            String username = tokenManager.getUserFromToken(token);

            SecurityUser user = (SecurityUser) redisTemplate.opsForValue().get("securityUser:" + username);
            return new UsernamePasswordAuthenticationToken(username,token, user.getAuthorities());
        }
        return null;
    }

}
