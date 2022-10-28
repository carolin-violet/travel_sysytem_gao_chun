package com.carolin_violet.travel_system.service;

import com.carolin_violet.travel_system.bean.RouteDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-22
 */
public interface RouteDetailService extends IService<RouteDetail> {

    List<Map> getAllItems();
}
