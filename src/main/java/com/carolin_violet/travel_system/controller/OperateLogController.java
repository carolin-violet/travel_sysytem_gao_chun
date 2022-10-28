package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.OperateLog;
import com.carolin_violet.travel_system.bean.vo.OperateLogVo;
import com.carolin_violet.travel_system.service.OperateLogService;
import com.carolin_violet.travel_system.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author carolin-violet
 * @since 2022-09-12
 */
@RestController
@RequestMapping("/travel_system/operate-log")
@CrossOrigin
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    // 分页查询接口操作日志记录
    @GetMapping("{current}/{limit}")
    @PreAuthorize("hasAnyAuthority('ROLE_LOG')")
    public R getPageLog(@PathVariable long current, @PathVariable long limit, @RequestParam String orderKey, @RequestParam String orderType) {
        Page<OperateLog> operateLogPage =  new Page<>(current, limit);
        QueryWrapper<OperateLog> wrapper = new QueryWrapper<>();

        if (orderType.equals("ascending")) {
            wrapper.orderByAsc(orderKey);
        } else {
            wrapper.orderByDesc(orderKey);
        }
        operateLogService.page(operateLogPage, wrapper);

        long total = operateLogPage.getTotal();
        List<OperateLog> records = operateLogPage.getRecords();
        ArrayList<OperateLogVo> list = new ArrayList<>();
        for (OperateLog operateLog : records) {
            OperateLogVo operateLogVo = new OperateLogVo();
            BeanUtils.copyProperties(operateLog, operateLogVo);
            list.add(operateLogVo);
        }
        return R.ok().data("total", total).data("items", list);
    }

    // 查看操作结果详情
    @GetMapping("detail/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_LOG')")
    public R getLogDetail(@PathVariable String id) {
        String result = operateLogService.getById(id).getResult();
        return R.ok().data("result", result);
    }
}

