package com.carolin_violet.travel_system.bean.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

/**
 * @ClassName LoginUser
 * @Description 由于过多使用LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);会导致Stream异常，所以缓存下登录数据
 * @Author zj
 * @Date 2022/5/24 17:04
 * @Version 1.0
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    // 登录手机号

    private String username;
    //登录密码
    private String password;

    // 登录手机验证码
    private String code;

}
