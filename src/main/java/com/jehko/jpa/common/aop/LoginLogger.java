package com.jehko.jpa.common.aop;


import com.jehko.jpa.logs.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoginLogger {
    private final LogService logService;

    @Around("execution(* com.jehko.jpa..*.*Service*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("before logging");

        Object result = joinPoint.proceed();

        log.info("after logging");

        return result;
    }
}
