package com.carolin_violet.travel_system.service;

import com.carolin_violet.travel_system.bean.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-24
 */
public interface PermissionService extends IService<Permission> {

    List<String> getPermissionListById(String id);

}
