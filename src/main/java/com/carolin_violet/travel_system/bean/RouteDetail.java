package com.carolin_violet.travel_system.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.sql.Time;
import java.util.Date;

import java.io.Serializable;
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
 * @since 2022-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RouteDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 站点id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 线路id
     */
    private String routeId;

    /**
     * 站点名称
     */
    private String site;

    /**
     * 站点排序
     */
    private Integer sort;

    /**
     * 站点活动开始时间
     */
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Time startTime;

    /**
     * 站点活动结束时间
     * @DateTimeFormat 将前端传过来的时间字符串转换成datetime
     */
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Time endTime;

    /**
     * 站点活动详情描述
     */
    private String description;

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
