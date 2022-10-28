package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.Delicacy;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.DelicacyService;
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
@RequestMapping("/travel_system/delicacy")
@CrossOrigin
public class DelicacyController {
    @Autowired
    private DelicacyService delicacyService;

    // 分页查询所有美食
    @SystemControllerLog(operate = "分页查询美食",module = "美食管理")
    @PreAuthorize("hasAnyAuthority('ROLE_DELICACY')")
    @GetMapping("findAll/{cur}/{limit}")
    public R findAllDelicacy(@PathVariable long cur, @PathVariable long limit, @RequestParam String orderKey, @RequestParam String orderType, @RequestParam String searchKey) {
        Page<Delicacy> delicacyPage = new Page<>(cur, limit);
        QueryWrapper<Delicacy> wrapper = new QueryWrapper<>();

        if (orderType.equals("ascending")) {
            wrapper.orderByAsc(orderKey);
        } else {
            wrapper.orderByDesc(orderKey);
        }

        wrapper.like("name", searchKey).or().like("id", searchKey).eq("is_deleted", 0);

        delicacyService.page(delicacyPage, wrapper);

        long total = delicacyPage.getTotal();
        List<Delicacy> records = delicacyPage.getRecords();
        return R.ok().data("items", records).data("total", total);
    }

    // 添加美食信息
//    @SystemControllerLog(operate = "添加美食信息",module = "美食管理")
    @PreAuthorize("hasAnyAuthority('ROLE_DELICACY')")
    @PostMapping("addDelicacy")
    public R addDelicacy(@RequestBody Delicacy delicacy) {
        boolean save = delicacyService.save(delicacy);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 修改美食信息
    @SystemControllerLog(operate = "修改美食信息",module = "美食管理")
    @PreAuthorize("hasAnyAuthority('ROLE_DELICACY')")
    @PutMapping("updateDelicacy")
    public R updateDelicacy(@RequestBody Delicacy delicacy) {
        boolean flag = delicacyService.updateById(delicacy);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 删除美食信息
    @SystemControllerLog(operate = "删除美食信息",module = "美食管理")
    @PreAuthorize("hasAnyAuthority('ROLE_DELICACY')")
    @DeleteMapping("{id}")
    public R removeDelicacy(@PathVariable String id) {
        delicacyService.removePhoto(delicacyService.getById(id).getCover());
        boolean flag = delicacyService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

