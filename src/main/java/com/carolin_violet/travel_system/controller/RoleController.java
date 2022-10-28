package com.carolin_violet.travel_system.controller;


import com.carolin_violet.travel_system.bean.Role;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.RoleService;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/travel_system/role")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    // 查询所有角色
    @SystemControllerLog(operate = "查询所有角色",module = "角色管理")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    @RequestMapping("findAll")
    public R findAllRole() {
        List<Role> list = roleService.list(null);
        return R.ok().data("items", list);
    }
}

