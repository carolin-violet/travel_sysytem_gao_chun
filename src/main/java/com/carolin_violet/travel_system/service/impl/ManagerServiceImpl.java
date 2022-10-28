package com.carolin_violet.travel_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carolin_violet.travel_system.bean.Manager;
import com.carolin_violet.travel_system.mapper.ManagerMapper;
import com.carolin_violet.travel_system.service.ManagerService;
import org.springframework.stereotype.Service;

/**
 * @ClassName ManagerInfoServiceImpl
 * @Description TODO
 * @Author zj
 * @Date 2022/5/22 9:01
 * @Version 1.0
 */

@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

}
