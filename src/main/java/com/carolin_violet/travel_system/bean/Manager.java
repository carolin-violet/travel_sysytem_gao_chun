package com.carolin_violet.travel_system.bean;


import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @ClassName ManagerInfo
 * @Description TODO
 * @Author zj
 * @Date 2022/5/22 8:50
 * @Version 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Manager {
    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 管理员姓名
     */
    private String name;

    /**
     * 管理员手机号
     */
    private String telephone;

    /**
     * 管理员密码
     */
    private String password;

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
