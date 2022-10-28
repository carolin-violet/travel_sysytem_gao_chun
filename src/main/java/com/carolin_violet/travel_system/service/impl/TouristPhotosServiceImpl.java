package com.carolin_violet.travel_system.service.impl;

import com.carolin_violet.travel_system.bean.TouristPhotos;
import com.carolin_violet.travel_system.mapper.TouristPhotosMapper;
import com.carolin_violet.travel_system.service.TouristPhotosService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-08-18
 */
@Service
public class TouristPhotosServiceImpl extends ServiceImpl<TouristPhotosMapper, TouristPhotos> implements TouristPhotosService {

    @Override
    public void removePhotos(String id) {

    }
}
