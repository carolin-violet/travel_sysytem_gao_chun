package com.carolin_violet.travel_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.bean.Permission;
import com.carolin_violet.travel_system.bean.Role;
import com.carolin_violet.travel_system.mapper.PermissionMapper;
import com.carolin_violet.travel_system.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carolin_violet.travel_system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-24
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RoleService roleService;

    // 根据用户id获取相关权限的名称列表
    @Override
    public List<String> getPermissionListById(String id) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List<Permission> permissions = baseMapper.selectList(wrapper);
        List<String> permissionValueList = new ArrayList<>();
        for (Permission permission : permissions) {
            Role role = roleService.getById(permission.getRoleId());
            permissionValueList.add(role.getRoleName());
        }
        return permissionValueList;
    }
}
