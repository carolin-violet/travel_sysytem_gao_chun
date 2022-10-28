package com.carolin_violet.travel_system.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carolin_violet.travel_system.bean.Manager;
import com.carolin_violet.travel_system.bean.OperateLog;
import com.carolin_violet.travel_system.security.TokenManager;
import com.carolin_violet.travel_system.service.ManagerService;

import com.carolin_violet.travel_system.service.OperateLogService;
import com.carolin_violet.travel_system.utils.IpdbUtil;
import com.carolin_violet.travel_system.utils.R;
import org.apache.ibatis.annotations.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;


/**
 * @ClassName SystemLogAspect
 * @Description TODO
 * @Author zj
 * @Date 2022/9/12 19:51
 * @Version 1.0
 */
@Aspect
@Component
public class SystemLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    @Autowired
    private ManagerService  managerService;

    @Autowired
    OperateLogService operateLogService;

    @Autowired
    private TokenManager tokenManager;
    /**
     * Controller层切点
     */
    @Pointcut("@annotation(com.carolin_violet.travel_system.config.SystemControllerLog)")
    public void SystemControllerLog(){

    }


    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("SystemControllerLog()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        logger.info("进入日志切面前置通知!");

    }

    @After("SystemControllerLog()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("进入日志切面后置通知!");

    }

    /**value切入点位置
     * returning 自定义的变量，标识目标方法的返回值,自定义变量名必须和通知方法的形参一样
     * 特点：在目标方法之后执行的,能够获取到目标方法的返回值，可以根据这个返回值做不同的处理
     */
    @AfterReturning(value = "SystemControllerLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    /***
     * 异常通知 记录操作报错日志
     * * @param joinPoint
     * * @param e
     * */
    @AfterThrowing(pointcut = "SystemControllerLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.info("进入日志切面异常通知!!");
        logger.info("异常信息:" + e.getMessage());
    }


    //使用这个方法先注释前面三个方法，留before方法就行
    /**
     * 通知包裹了目标方法，在目标方法调用之前和之后执行自定义的行为
     * ProceedingJoinPoint切入点可以获取切入点方法上的名字、参数、注解和对象
     * @param joinPoint
     */
    @Around("SystemControllerLog() && @annotation(systemControllerLog)")
    public R doAfterReturning(ProceedingJoinPoint joinPoint, SystemControllerLog systemControllerLog) throws Throwable {
        logger.info("设置日志信息存储到表中!");
        //joinPoint.proceed() 结果集
        //参数数组
        Object[] args = joinPoint.getArgs();
        //请求参数数据
//        String requestJson = JSONUtil.toJsonStr(args);
        //方法名
//        String methodName = joinPoint.getSignature().getName();
        //得到request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //得到token
        String token = request.getHeader("token");
        String telephone = tokenManager.getUserFromToken(token);
        Manager manager = managerService.getOne(new QueryWrapper<Manager>().eq("telephone", telephone));
        logger.info("得到用户信息："+manager.toString());
        //写入数据库操作日志
        OperateLog operatelog = new OperateLog();
        operatelog.setUserId(manager.getId());
        operatelog.setName(manager.getName());
        operatelog.setOperate(systemControllerLog.operate());
        operatelog.setModule(systemControllerLog.module());
//        operatelog.setCreateTime(new Date());
        operatelog.setIp(request.getRemoteAddr());

        String[] cns = IpdbUtil.find(request.getRemoteAddr(), "CN");
        System.out.println(cns);
        operatelog.setAddress(Arrays.toString(cns));

        //存入返回的结果集 joinPoint.proceed()
        R proceed = (R)joinPoint.proceed();
        System.out.println(joinPoint.proceed());
        operatelog.setResult(joinPoint.proceed().toString());
//        operatelog.setResult(JSONUtil.toJsonStr(joinPoint.proceed()));
        //保存
        operateLogService.save(operatelog);

        return proceed;

    }

}

