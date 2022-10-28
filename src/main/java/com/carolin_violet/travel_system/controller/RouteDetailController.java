package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.bean.RouteDetail;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.RouteDetailService;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-22
 */
@RestController
@RequestMapping("/travel_system/route-detail")
@CrossOrigin
public class RouteDetailController {

    @Autowired
    private RouteDetailService routeDetailService;


    // 根据线路id查询线路所有站点信息
    @SystemControllerLog(operate = "根据线路id查询线路所有站点信息",module = "站点管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ROUTE')")
    @GetMapping("{id}/findAll")
    public R findAllSites(@PathVariable String id) {
        QueryWrapper<RouteDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("route_id", id).orderByAsc("sort");
        List<RouteDetail> list = routeDetailService.list(wrapper);
        return R.ok().data("items", list);
    }

    // 添加站点信息
//    @SystemControllerLog(operate = "添加站点信息",module = "站点管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ROUTE')")
    @PostMapping("addSite")
    public R addSite(@RequestBody RouteDetail routeDetail) {
        boolean save = routeDetailService.save(routeDetail);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 修改站点信息
    @SystemControllerLog(operate = "修改站点信息",module = "站点管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ROUTE')")
    @PutMapping("updateSite")
    public R updateSite(@RequestBody RouteDetail routeDetail) {
        boolean flag = routeDetailService.updateById(routeDetail);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 删除站点信息
    @SystemControllerLog(operate = "删除站点信息",module = "站点管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ROUTE')")
    @DeleteMapping("{id}")
    public R removeSite(@PathVariable String id) {
        boolean flag = routeDetailService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 获取所有分类及分类的数据名称
    @SystemControllerLog(operate = "获取所有分类及分类的数据名称",module = "站点管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ROUTE')")
    @GetMapping("getAllItems")
    public R getAllItems() {
       List<Map> allItems = routeDetailService.getAllItems();
        return R.ok().data("items", allItems);
    }
}

