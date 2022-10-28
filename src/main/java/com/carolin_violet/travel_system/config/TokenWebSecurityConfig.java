package com.carolin_violet.travel_system.config;

import com.carolin_violet.travel_system.filter.TokenAuthenticationFilter;
import com.carolin_violet.travel_system.filter.TokenLoginFilter;
import com.carolin_violet.travel_system.security.DefaultPasswordEncoder;
import com.carolin_violet.travel_system.security.TokenLogoutHandler;
import com.carolin_violet.travel_system.security.TokenManager;
import com.carolin_violet.travel_system.security.UnauthorizedEntryPoint;
import com.carolin_violet.travel_system.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * <p>
 * Security配置类
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 未登录handler
    @Autowired
    private MyUnAuthEntryPoint myUnAuthEntryPoint;

    // 无权限
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    //  登出handler处理
    @Autowired
    private MyLogoutHandler myLogoutHandler;

    @Autowired
    private TokenLogoutHandler tokenLogoutHandler;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;


    private UserDetailsService userDetailsService;
    private TokenManager tokenManager;
    private DefaultPasswordEncoder defaultPasswordEncoder;
    private RedisTemplate redisTemplate;

    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 配置设置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint) // 未登录 handler
                .accessDeniedHandler(myAccessDeniedHandler) // 无权限


                .and().cors().configurationSource(corsConfigurationSource())   // 解决跨域问题
                .and().csrf().disable() // 关闭 csrf 跨域请求
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()  // 通过所有OPTION请求

                // 以下为登录后无需权限就能访问的
                .antMatchers(HttpMethod.POST, "/travel_system/oss/picture").permitAll()  // 新增相关物件时添加相关展示图片
                .antMatchers(HttpMethod.POST, "/travel_system/oss/picture/addPhoto/**").permitAll() // 根据物件mark_id添加更多的详情图片
                .anyRequest().authenticated()

                .and()
                .logout() // logout设定
                .logoutUrl("/travel_system/logout")  //退出请求  /logouts 未定义，交给自定义handler实现功能
                .addLogoutHandler(myLogoutHandler) // 登出 myLogoutHandler 处理

                .and()

                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate)) // 认证交给 自定义 TokenLoginFilter 实现
                .addFilter(new TokenAuthenticationFilter(authenticationManager(),tokenManager, redisTemplate))
                // basic 方式
                .httpBasic();
    }

    CorsConfigurationSource corsConfigurationSource() {
        // 提供CorsConfiguration实例，并配置跨域信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));   // *或者http://localhost:9528(vuecli的端口)
        corsConfiguration.setMaxAge(360000L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * 密码处理
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 配置哪些请求不拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 此处配置不需要通过过滤链的访问
        web.ignoring().antMatchers(
                "/travel_system/front/**",  //  所有前台数据不过滤
                "/travel_system/security/*",
                "/travel_system/msm/send/**",
                "/swagger-resources/**",
                "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        );
    }
}