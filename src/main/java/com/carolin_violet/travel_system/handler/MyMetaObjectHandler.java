package com.carolin_violet.travel_system.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName MyMetaObjectHandler
 * @Description TODO
 * @Author zj
 * @Date 2022/5/6 8:55
 * @Version 1.0
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 添加记录时自动填充的字段
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("modifyTime", new Date(), metaObject);
        this.setFieldValByName("isDeleted", 0, metaObject);
    }

    // 修改记录时自动填充的字段
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("modifyTime", new Date(), metaObject);
    }
}
