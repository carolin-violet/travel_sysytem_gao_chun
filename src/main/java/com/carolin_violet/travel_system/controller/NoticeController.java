package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.Notice;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.NoticeService;
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
@RequestMapping("/travel_system/notice")
@CrossOrigin
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    // 分页查询公告
    @SystemControllerLog(operate = "分页查询公告",module = "公告管理")
    @PreAuthorize("hasAnyAuthority('ROLE_NOTICE')")
    @GetMapping("pageNotice/{current}/{limit}")
    public R findAllNotice(@PathVariable long current, @PathVariable long limit, @RequestParam String orderKey, @RequestParam String orderType, @RequestParam String searchKey) {
        // 创建Page对象用于分页
        Page<Notice> pageNotice = new Page<>(current, limit);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();

        if (orderType.equals("ascending")) {
            wrapper.orderByAsc(orderKey);
        } else {
            wrapper.orderByDesc(orderKey);
        }

        wrapper.like("name", searchKey).or().like("id", searchKey).eq("is_deleted", 0);

        // 调用方法实现分页查询
        noticeService.page(pageNotice, wrapper);

        // 查询总条数
        long total = pageNotice.getTotal();
        // 查询一页的数据
        List<Notice> records = pageNotice.getRecords();
        System.out.println("records666666666666666" + records);
        return R.ok().data("total", total).data("rows", records);
    }

    // 发布公告
//    @SystemControllerLog(operate = "发布公告",module = "公告管理")
    @PreAuthorize("hasAnyAuthority('ROLE_NOTICE')")
    @PostMapping("addNotice")
    public R addNotice(@RequestBody Notice notice) {
        boolean save = noticeService.save(notice);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 更新公告信息
    @SystemControllerLog(operate = "更新公告信息",module = "公告管理")
    @PreAuthorize("hasAnyAuthority('ROLE_NOTICE')")
    @PutMapping("updateNotice")
    public R updateNotice(@RequestBody Notice notice) {
        boolean flag = noticeService.updateById(notice);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 删除公告
    @SystemControllerLog(operate = "删除公告",module = "公告管理")
    @PreAuthorize("hasAnyAuthority('ROLE_NOTICE')")
    @DeleteMapping("{id}")
    public R removeNotice(@PathVariable String id) {
        boolean flag = noticeService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

