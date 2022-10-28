package com.carolin_violet.travel_system.handler;

import com.carolin_violet.travel_system.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author zj
 * @Date 2022/5/6 9:54
 * @Version 1.0
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)  // 指定出现什么异常执行这个方法
    @ResponseBody  // 返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理..");
    }

    @ExceptionHandler(ArithmeticException.class)  // 指定出现什么异常执行这个方法
    @ResponseBody  // 返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理..");
    }

    // 自定义异常
    @ExceptionHandler(MyException.class)  // 指定出现什么异常执行这个方法
    @ResponseBody  // 返回数据
    public R error(MyException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
