package com.carolin_violet.travel_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.bean.Manager;
import com.carolin_violet.travel_system.bean.security.SecurityUser;
import com.carolin_violet.travel_system.service.ManagerService;
import com.carolin_violet.travel_system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description TODO
 * @Author zj
 * @Date 2022/5/23 21:04
 * @Version 1.0
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private PermissionService permissionService;

    /***
     * 根据账号获取用户信息
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        Manager user = managerService.getOne(new QueryWrapper<Manager>().eq("telephone", username));

        // 判断用户是否存在
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        List<String> permissionList = permissionService.getPermissionListById(user.getId());


        SecurityUser securityUser = new SecurityUser();

        securityUser.setUser(user);
        securityUser.setPermissionValueList(permissionList);
        return securityUser;
    }

}
