package com.carolin_violet.travel_system.service;

import com.carolin_violet.travel_system.bean.Delicacy;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-22
 */
public interface DelicacyService extends IService<Delicacy> {

    void removePhoto(String picture);
}
