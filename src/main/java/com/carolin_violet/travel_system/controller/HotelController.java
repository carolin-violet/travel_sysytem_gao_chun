package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.Coordinate;
import com.carolin_violet.travel_system.bean.Hotel;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.CoordinateService;
import com.carolin_violet.travel_system.service.HotelService;
import com.carolin_violet.travel_system.service.PropagandistPhotosService;
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
 * @since 2022-05-22
 */
@RestController
@RequestMapping("/travel_system/hotel")
@CrossOrigin
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private CoordinateService coordinateService;

    // 分页查询旅馆
    @SystemControllerLog(operate = "分页查询旅馆",module = "旅馆管理")
    @PreAuthorize("hasAnyAuthority('ROLE_HOTEL')")
    @GetMapping("findAll/{cur}/{limit}")
    public R findAllHotel(@PathVariable long cur, @PathVariable long limit, @RequestParam String orderKey, @RequestParam String orderType, @RequestParam String searchKey) {
        Page<Hotel> hotelPage = new Page<>(cur, limit);
        QueryWrapper<Hotel> wrapper = new QueryWrapper<>();

        if (orderType.equals("ascending")) {
            wrapper.orderByAsc(orderKey);
        } else {
            wrapper.orderByDesc(orderKey);
        }

        wrapper.like("name", searchKey).or().like("id", searchKey).eq("is_deleted", 0);

        hotelService.page(hotelPage, wrapper);

        long total = hotelPage.getTotal();
        List<Hotel> records = hotelPage.getRecords();
        return R.ok().data("items", records).data("total", total);
    }

    // 添加旅馆
//    @SystemControllerLog(operate = "添加旅馆",module = "旅馆管理")
    @PreAuthorize("hasAnyAuthority('ROLE_HOTEL')")
    @PostMapping("addHotel")
    public R addHotel(@RequestBody Hotel hotel) {
        boolean save = hotelService.save(hotel);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 修改旅馆信息
    @SystemControllerLog(operate = "修改旅馆信息",module = "旅馆管理")
    @PreAuthorize("hasAnyAuthority('ROLE_HOTEL')")
    @PutMapping("updateHotel")
    public R updateHotel(@RequestBody Hotel hotel) {
        boolean flag = hotelService.updateById(hotel);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 逻辑删除旅馆
    @SystemControllerLog(operate = "删除旅馆",module = "旅馆管理")
    @PreAuthorize("hasAnyAuthority('ROLE_HOTEL')")
    @DeleteMapping("{id}")
    public R removeHotel(@PathVariable String id) {
        coordinateService.remove(new QueryWrapper<Coordinate>().eq("mark_id", id));  // 删除地点坐标
        boolean flag = hotelService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

