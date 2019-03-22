package me.maxct.asset.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author imaxct
 * 2019-03-22 21:48
 */
@Aspect
@Component
public class AuthCheckInterceptor {

    @Around("execution(public me.maxct.asset.dto.Msg me.maxct.asset.controller.*.*()) && @annotation(AuthCheck)")
    public Object beforeAuthCheck(ProceedingJoinPoint joinPoint) throws Throwable {

        Object o = joinPoint.proceed();
        System.out.println(o.getClass());
        if (o instanceof java.lang.String) {
            System.out.println("String");
        }
        return o;
    }
}
