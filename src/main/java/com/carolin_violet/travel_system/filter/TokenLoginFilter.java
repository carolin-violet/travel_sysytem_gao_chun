package com.carolin_violet.travel_system.filter;


import com.carolin_violet.travel_system.bean.security.LoginUser;
import com.carolin_violet.travel_system.bean.security.SecurityUser;
import com.carolin_violet.travel_system.security.TokenManager;
import com.carolin_violet.travel_system.utils.R;
import com.carolin_violet.travel_system.utils.ResponseUtil;
import com.carolin_violet.travel_system.utils.RsaUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @ClassName JwtLoginFilter
 * @Description 验证登录账户并返回token
 * @Author zj
 * @Date 2022/5/24 10:03
 * @Version 1.0
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false); // 关闭登录只允许 post
        // 设置登陆路径，并且post请求
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/travel_system/login","POST"));
    }

    //这个方法是用来去尝试验证用户的
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            String privateKey = (String) redisTemplate.opsForValue().get(request.getRemoteAddr() + "-privateKey:");
            String decryptPassword = RsaUtils.decrypt(loginUser.getPassword(), RsaUtils.getPrivateKey(privateKey));
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), decryptPassword, new ArrayList<>()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录成功后调用的方法
     * @param req
     * @param res
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        logger.info("=================登录成功=============");
        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        // 根据用户名生产token
        String token = tokenManager.createToken(securityUser.getUsername());
        // 将权限存入redis缓存
        redisTemplate.opsForValue().set("securityUser:"+ securityUser.getUsername(), securityUser);
        redisTemplate.opsForValue().set("token:"+ securityUser.getUsername(), token);
        ResponseUtil.out(res, R.ok().data("token", token));
    }

    /**
     * 登录失败
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(response, R.error().message("账号或密码错误"));
    }
}
