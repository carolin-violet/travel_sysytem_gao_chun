package com.carolin_violet.travel_system.service;

import com.carolin_violet.travel_system.bean.Tourist;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-30
 */
public interface TouristService extends IService<Tourist> {

    void removeAll(String id);
}
