package com.carolin_violet.travel_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.bean.Hotel;
import com.carolin_violet.travel_system.bean.RouteDetail;
import com.carolin_violet.travel_system.bean.ScenicSpot;
import com.carolin_violet.travel_system.mapper.RouteDetailMapper;
import com.carolin_violet.travel_system.service.HotelService;
import com.carolin_violet.travel_system.service.RouteDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carolin_violet.travel_system.service.ScenicSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-22
 */
@Service
public class RouteDetailServiceImpl extends ServiceImpl<RouteDetailMapper, RouteDetail> implements RouteDetailService {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ScenicSpotService scenicSpotService;

    @Override
    public List<Map> getAllItems() {
        List<Map> mapList = new ArrayList<>();

        // 添加旅馆分类数据
        List<Hotel> hotelList = hotelService.list(null);
        List<Map> hotels = new ArrayList<>();
        for (int i = 0; i < hotelList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("label", hotelList.get(i).getName());
            map.put("value", hotelList.get(i).getName());
            hotels.add(map);
        }
        Map m1 = new HashMap();
        m1.put("label", "旅馆");
        m1.put("value", "旅馆");
        m1.put("children", hotels);


        // 添加景点分类数据
        List<ScenicSpot> scenicSpotList = scenicSpotService.list(null);
        List<Map> scenicSpots = new ArrayList<>();
        for (int i = 0; i < scenicSpotList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("label", scenicSpotList.get(i).getName());
            map.put("value", scenicSpotList.get(i).getName());
            scenicSpots.add(map);
        }
        Map m2 = new HashMap();
        m2.put("label", "景点");
        m2.put("value", "景点");
        m2.put("children", scenicSpots);

        mapList.add(m1);
        mapList.add(m2);

        return mapList;
    }
}
