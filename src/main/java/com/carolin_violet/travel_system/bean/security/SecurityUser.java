package com.carolin_violet.travel_system.bean.security;

import com.carolin_violet.travel_system.bean.Manager;
import com.carolin_violet.travel_system.bean.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName SecurityUser
 * @Description TODO
 * @Author zj
 * @Date 2022/5/24 10:13
 * @Version 1.0
 */
@Data
public class SecurityUser implements UserDetails {


    // 登录用户的基本信息
    private Manager user;

    //当前权限
    private List<String> permissionValueList;

    public SecurityUser() {
    }

    public SecurityUser(Manager user) {
        if (user != null) {
            this.user = user;
        }
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        permissionValueList.forEach(permission ->{
            if(!StringUtils.isEmpty(permission)){
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
                authorities.add(authority);
            }
        });
        return authorities;
    }


    @JsonIgnore
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return user.getTelephone();
    }

    //下面这四个方法是根据某些条件来判断用户是否可用，方便起见，可以直接设为true
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() { //指示用户的帐户是否已过期
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {   //指示用户是否被锁定或解锁
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {  //指示用户的凭据（密码）是否已过期
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {    //指示用户是否被启用或禁用
        return true;
    }
}
