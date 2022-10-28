package com.carolin_violet.travel_system.bean.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TravelNoteVo
 * @Description 游客上传的游记信息
 * @Author zj
 * @Date 2022/5/22 19:27
 * @Version 1.0
 */
@Data
public class TravelNoteVo {

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
}
