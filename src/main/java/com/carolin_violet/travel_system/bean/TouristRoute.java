package com.carolin_violet.travel_system.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author carolin-violet
 * @since 2022-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TouristRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 线路id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 线路标题
     */
    private String title;

    /**
     * 线路描述
     */
    private String description;

    /**
     * 推荐原因
     */
    private String reason;

    private Float price;

    private Float discountPrice;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    /**
     * 添加时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 删除时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

}
