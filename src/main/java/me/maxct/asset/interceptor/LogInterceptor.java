package me.maxct.asset.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author imaxct
 * 2019-03-24 16:34
 */
@Aspect
@Component
@Order(8)
public class LogInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);
    private final ObjectMapper  objectMapper;

    @Autowired
    public LogInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Around("execution(public * me.maxct.asset.controller.*.*(..)) && args(..,request)")
    public Object aroundLog(ProceedingJoinPoint joinPoint,
                            HttpServletRequest request) throws Throwable {

        Object result = joinPoint.proceed();
        LOGGER.info("[METHOD:{} URI:{} PARAMS:{} RESULT:{}]", request.getMethod(),
            request.getRequestURI(), objectMapper.writeValueAsString(request.getParameterMap()),
            objectMapper.writeValueAsString(result));
        return result;
    }
}
