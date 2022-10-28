package com.carolin_violet.travel_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.bean.Coordinate;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.CoordinateService;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author carolin-violet
 * @since 2022-09-11
 */
@RestController
@RequestMapping("/travel_system/coordinate")
public class CoordinateController {

    @Autowired
    private CoordinateService coordinateService;

    // 获取坐标
    @SystemControllerLog(operate = "获取坐标",module = "坐标管理")
    @PreAuthorize("hasAnyAuthority('ROLE_COORDINATE')")
    @GetMapping("{mark_id}")
    public R getCoordinate(@PathVariable String mark_id) {
        Coordinate coordinate = coordinateService.getOne(new QueryWrapper<Coordinate>().eq("mark_id", mark_id));
        return R.ok().data("coordinate", coordinate);
    }


    // 添加坐标
//    @SystemControllerLog(operate = "添加坐标",module = "坐标管理")
    @PreAuthorize("hasAnyAuthority('ROLE_COORDINATE')")
    @PostMapping
    public R addCoordinate(@RequestBody Coordinate coordinate) {
        coordinateService.save(coordinate);
        return R.ok();
    }

    // 修改坐标
    @SystemControllerLog(operate = "修改坐标",module = "坐标管理")
    @PreAuthorize("hasAnyAuthority('ROLE_COORDINATE')")
    @PutMapping
    public R updateCoordinate(@RequestBody Coordinate coordinate) {
        coordinateService.updateById(coordinate);
        return R.ok();
    }

}

