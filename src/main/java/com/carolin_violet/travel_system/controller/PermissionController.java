package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.bean.Permission;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.handler.MyException;
import com.carolin_violet.travel_system.service.PermissionService;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/travel_system/permission")
@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // 根据用户id添加权限
//    @SystemControllerLog(operate = "根据用户id添加权限",module = "权限管理")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    @PostMapping("addPermission")
    public R addPermission(@RequestBody Permission permission) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", permission.getUserId()).eq("role_id", permission.getRoleId());
        // 用列表判断防空指针异常
        List<Permission> list = permissionService.list(wrapper);
        if (list.size() == 0) {
            permissionService.save(permission);
            return R.ok();
        } else {
            throw new MyException(20001, "数据已存在");
        }
    }



    // 根据权限id删除权限
    @SystemControllerLog(operate = "根据权限id删除权限",module = "权限管理")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    @DeleteMapping("{id}")
    public R removePermission(@PathVariable Integer id) {
        boolean flag = permissionService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    // 根据用户id查询该用户所有权限记录
    @SystemControllerLog(operate = "根据用户id查询该用户所有权限记录",module = "权限管理")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    @GetMapping("findAll/{id}")
    public R findAllPermissionById(@PathVariable String id) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List<Permission> list = permissionService.list(wrapper);
        System.out.println(list.toString());
        return R.ok().data("items", list);
    }
}

