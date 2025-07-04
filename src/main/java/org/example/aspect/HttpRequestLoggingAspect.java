package org.example.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class HttpRequestLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestLoggingAspect.class);

    //@Before("execution(* org.example.controller.ProductController.*(..))")
    @Before("@annotation(org.example.aspect.annotation.LogHttpRequest)")
    public void logBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            log.info("Получен запрос: {} {}", request.getMethod(), request.getRequestURI());
        }
    }
}
