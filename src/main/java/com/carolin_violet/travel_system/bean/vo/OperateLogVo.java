package com.carolin_violet.travel_system.bean.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName OperateLogVo
 * @Description 日志记录返回结果
 * @Author zj
 * @Date 2022/9/13 15:02
 * @Version 1.0
 */
@Data
public class OperateLogVo {

    private String id;

    /**
     * 操作人姓名
     */
    private String name;

    /**
     * 操作人id
     */
    private String userId;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作描述
     */
    private String operate;

    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 操作ip
     */
    private String ip;

    /**
     * 操作地点
     */
    private String address;
}
