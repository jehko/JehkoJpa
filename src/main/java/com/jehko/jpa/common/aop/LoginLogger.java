package com.jehko.jpa.common.aop;


import com.jehko.jpa.logs.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoginLogger {
    private final LogService logService;

    @Around("execution(* com.jehko.jpa..*.*Service*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        StringBuilder sb = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        if(args != null && args.length > 0) {
            for(Object arg : args) {
                sb.append(arg.toString());
            }
        }
        log.info(sb.toString());
        log.info("{} {} {} {}", sb.toString(), joinPoint.getSignature().getName(), request.getMethod(), request.getRequestURI());

        return result;
    }
}
