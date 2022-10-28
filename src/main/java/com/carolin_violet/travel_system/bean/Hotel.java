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
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 旅馆id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 旅馆名称
     */
    private String name;

    /**
     * 旅馆介绍
     */
    private String description;

    /**
     * 旅馆封面展示图片
     */
    private String cover;

    /**
     * 推荐指数(1,2,3,4,5)
     */
    private Integer recommendRank;

    private String telephone;

    private String address;

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
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
