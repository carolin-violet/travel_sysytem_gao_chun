package com.carolin_violet.travel_system.bean.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName OrderVo2
 * @Description TODO
 * @Author zj
 * @Date 2022/6/29 15:39
 * @Version 1.0
 */

@Data
public class OrderVo {

    /**
     * 订单id
     */
    private String id;

    // 商品信息(名称，主题)
    private String title;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 遊客id
     */
    private String TouristId;

    /**
     * 预约时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date appointmentTime;

    /**
     * 预约手机号
     */
    private String phone;

    /**
     * 预约人姓名
     */
    private String name;

    /**
     * 成年人的人数
     */
    private Integer adult;

    /**
     * 未成年人的人数
     */
    private Integer child;

    /**
     * route为线路拼单,scenic为景点门票
     */
    private String mark;

    /**
     * 总金额
     */
    private Float amount;

    /**
     * 是否已支付，支付为1，未支付为0
     */
    private Integer isPaid;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单修改时间
     */
    private Date modifyTime;

}
