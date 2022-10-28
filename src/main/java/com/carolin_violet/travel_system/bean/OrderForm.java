package com.carolin_violet.travel_system.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author carolin-violet
 * @since 2022-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 游客id
     */
    private String touristId;

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
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    /**
     * 订单创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 订单修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
