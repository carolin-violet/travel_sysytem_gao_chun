package com.carolin_violet.travel_system.service;

import com.carolin_violet.travel_system.bean.TouristPhotos;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-08-18
 */
public interface TouristPhotosService extends IService<TouristPhotos> {

    void removePhotos(String id);
}
