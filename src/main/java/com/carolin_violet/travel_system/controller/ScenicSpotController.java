package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.Coordinate;
import com.carolin_violet.travel_system.bean.ScenicSpot;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.CoordinateService;
import com.carolin_violet.travel_system.service.ScenicSpotService;
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
@RequestMapping("/travel_system/scenic-spot")
@CrossOrigin
public class ScenicSpotController {

    @Autowired
    private ScenicSpotService scenicSpotService;

    @Autowired
    private CoordinateService coordinateService;

    // 分页查询景点
    @SystemControllerLog(operate = "分页查询景点",module = "景点管理")
    @PreAuthorize("hasAnyAuthority('ROLE_SCENIC')")
    @GetMapping("findAll/{cur}/{limit}")
    public R findAllScenicSpot(@PathVariable long cur, @PathVariable long limit, @RequestParam String orderKey, @RequestParam String orderType, @RequestParam String searchKey) {
        Page<ScenicSpot> scenicSpotPage = new Page<>(cur, limit);
        QueryWrapper<ScenicSpot> wrapper = new QueryWrapper<>();

        if (orderType.equals("ascending")) {
            wrapper.orderByAsc(orderKey);
        } else {
            wrapper.orderByDesc(orderKey);
        }

        wrapper.like("name", searchKey).or().like("id", searchKey).eq("is_deleted", 0);

        scenicSpotService.page(scenicSpotPage, wrapper);
        List<ScenicSpot> records = scenicSpotPage.getRecords();
        long total = scenicSpotPage.getTotal();
        return R.ok().data("items", records).data("total", total);
    }

    // 添加景点
//    @SystemControllerLog(operate = "添加景点",module = "景点管理")
    @PreAuthorize("hasAnyAuthority('ROLE_SCENIC')")
    @PostMapping("addScenicSpot")
    public R AddScenicSpot(@RequestBody ScenicSpot scenicSpot) {
        boolean save = scenicSpotService.save(scenicSpot);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 修改景点信息
    @SystemControllerLog(operate = "修改景点信息",module = "景点管理")
    @PreAuthorize("hasAnyAuthority('ROLE_SCENIC')")
    @PutMapping("updateScenicSpot")
    public R updateScenicSpot(@RequestBody ScenicSpot scenicSpot) {
        boolean flag = scenicSpotService.updateById(scenicSpot);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 删除景点
    @SystemControllerLog(operate = "删除景点",module = "景点管理")
    @PreAuthorize("hasAnyAuthority('ROLE_SCENIC')")
    @DeleteMapping("{id}")
    public R removeScenicSpot(@PathVariable String id) {
        coordinateService.remove(new QueryWrapper<Coordinate>().eq("mark_id", id));  // 删除地点坐标
        scenicSpotService.removePhoto(scenicSpotService.getById(id).getCover());
        boolean flag = scenicSpotService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

