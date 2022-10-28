//package com.carolin_violet.travel_system.filter;
//
//import com.carolin_violet.travel_system.bean.security.LoginUser;
//import com.carolin_violet.travel_system.config.RedisUtil;
//import com.carolin_violet.travel_system.utils.R;
//import com.carolin_violet.travel_system.utils.ResponseUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @ClassName CodeValidateFilter
// * @Description 短信验证码过滤
// * @Author zj
// * @Date 2022/5/24 22:46
// * @Version 1.0
// */
//public class CodeValidateFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
//        try {
//            if ("/travel_system/login".equals(request.getRequestURI()) &&
//                    request.getMethod().equalsIgnoreCase("post")) {
//
//                // 获取用户输入的短信验证码
//                String inputCode = loginUser.getCode();
//                // 获取redis中的短信验证码
//                String phone = loginUser.getUsername();
//                String code = RedisUtil.redisTemplate.opsForValue().get("msm:" + phone);
//                System.out.println("------------------------------+++++++++++++++++++++++++++++++++++");
//                System.out.println(code);
//                System.out.println(inputCode);
//                System.out.println(phone);
//                System.out.println(loginUser.toString());
//                // 判断是否正确
//                if(code == null||!code.equals(inputCode)){
//                    System.out.println("验证码错误");
//                    ResponseUtil.out(response, R.error().message("验证码错误"));
//                }
//            }
//        }catch (AuthenticationException e){
//            ResponseUtil.out(response, R.error().message("认证出错"));
//            e.printStackTrace();
//        }
//        filterChain.doFilter(request, response);
//    }
//}
