package com.carolin_violet.travel_system.bean.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName FeedbackVo
 * @Description TODO
 * @Author zj
 * @Date 2022/5/22 20:15
 * @Version 1.0
 */
@Data
public class FeedbackVo {


    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 游客id
     */
    private String touristId;

    private String content;

    // 图片url列表
    private List<String> photoUrlList = new ArrayList<>();


    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

    /**
     * 官方回复
     */
    private String replay;

    /**
     * 官方回复时间
     */
    private Date replayTime;
}
