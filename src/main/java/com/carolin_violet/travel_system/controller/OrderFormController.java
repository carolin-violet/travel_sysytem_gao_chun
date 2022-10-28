package com.carolin_violet.travel_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carolin_violet.travel_system.bean.OrderForm;
import com.carolin_violet.travel_system.bean.ScenicSpot;
import com.carolin_violet.travel_system.bean.TouristRoute;
import com.carolin_violet.travel_system.bean.vo.OrderVo;
import com.carolin_violet.travel_system.config.SystemControllerLog;
import com.carolin_violet.travel_system.service.OrderFormService;
import com.carolin_violet.travel_system.service.ScenicSpotService;
import com.carolin_violet.travel_system.service.TouristRouteService;
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
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/travel_system/order")
@CrossOrigin
public class OrderFormController {

    @Autowired
    private OrderFormService orderFormService;

    @Autowired
    private ScenicSpotService scenicSpotService;

    @Autowired
    private TouristRouteService touristRouteService;

    // 根据时间、mark进行分页条件查询
    @SystemControllerLog(operate = "订单查询",module = "订单管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ORDER')")
    @GetMapping("pageOrder/{current}/{limit}")
    public R getPageOrder(@PathVariable long current, @PathVariable long limit, @RequestParam String orderKey, @RequestParam String orderType, @RequestParam String isPaid, @RequestParam String date, @RequestParam String searchKey) {

        Page<OrderForm> orderPage = new Page<>(current, limit);
        QueryWrapper<OrderForm> wrapper = new QueryWrapper<>();

        if (orderType.equals("ascending")) {
            wrapper.orderByAsc(orderKey);
        } else {
            wrapper.orderByDesc(orderKey);
        }

        if (!isPaid.equals("")) wrapper.like("is_paid", isPaid);
        if (!date.equals("")) wrapper.like("appointment_time", date);
        if (!searchKey.equals("")) wrapper.like("name", searchKey).or().like("id", searchKey).eq("is_deleted", 0);


        orderFormService.page(orderPage, wrapper);

        long total = orderPage.getTotal();

        List<OrderForm> records = orderPage.getRecords();

        // 每一个订单再查询对应的名称(景点、线路拼团)
        List<OrderVo> orderList = new ArrayList<>();
        for (OrderForm order: records) {
            System.out.println(order.toString());
            OrderVo orderItem = new OrderVo();
            BeanUtils.copyProperties(order, orderItem);

            if ("scenic".equals(order.getMark())) {
                ScenicSpot scenicSpot = scenicSpotService.getById(order.getCommodityId());
                orderItem.setTitle(scenicSpot.getName());
            } else if ("route".equals(order.getMark())) {
                TouristRoute touristRoute = touristRouteService.getById(order.getCommodityId());
                orderItem.setTitle(touristRoute.getTitle());
            }
            orderList.add(orderItem);
        }

        return R.ok().data("rows", orderList).data("total", total);
    }

    // 修改订单信息
    @SystemControllerLog(operate = "修改订单信息",module = "订单管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ORDER')")
    @PostMapping("updateOrder")
    public R updateOrder(@RequestBody OrderForm orderForm) {
        boolean flag = orderFormService.updateById(orderForm);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 删除订单
    @SystemControllerLog(operate = "删除订单",module = "订单管理")
    @PreAuthorize("hasAnyAuthority('ROLE_ORDER')")
    @DeleteMapping("{id}")
    public R deleteOrder(@PathVariable String id) {
        boolean flag = orderFormService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

