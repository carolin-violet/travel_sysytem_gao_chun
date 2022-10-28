package com.carolin_violet.travel_system.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName SystemControllerLog
 * @Description TODO
 * @Author zj
 * @Date 2022/9/12 19:48
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemControllerLog {
    // 操作描述
    String operate();
    // 模块
    String module();
}
