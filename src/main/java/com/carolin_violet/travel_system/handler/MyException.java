package com.carolin_violet.travel_system.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName MyException
 * @Description TODO
 * @Author zj
 * @Date 2022/5/22 9:18
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException{
    private Integer code;

    private String msg;
}
