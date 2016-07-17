package com.newcoder.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by WXY on 2016/7/17.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.newcoder.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StringBuilder sb = new StringBuilder();
        for(java.lang.Object arg : joinPoint.getArgs()){
            sb.append(arg.toString() + "|");
        }
        logger.info("before method : "  + sb.toString());
    }

    @After("execution(* com.newcoder.controller.IndexController.*(..))")
    public void aftermethod(JoinPoint joinPoint){
        logger.info("after method");
    }
}
