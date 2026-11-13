package com.example.practice5.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServiceLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    private final long slowThresholdMs;

    public ServiceLoggingAspect(@Value("${app.aspect.slow-threshold-ms:200}") long slowThresholdMs) {
        this.slowThresholdMs = slowThresholdMs;
    }

    @Pointcut("execution(public * com.example.practice4.service..*(..)) "
        + "|| execution(public * com.example.practice6.service..*(..)) "
        + "|| execution(public * com.example.practice6.client..*(..))")
void serviceMethods() { }

    @Around("serviceMethods()")
    public Object logAndTime(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().toShortString();
        log.info("--> {} args={}", method, Arrays.toString(pjp.getArgs()));

        long start = System.nanoTime();
        try {
            Object result = pjp.proceed();
            long ms = (System.nanoTime() - start) / 1_000_000;
            if (ms >= slowThresholdMs) {
                log.warn("<-- {} SLOW took {} ms", method, ms);
            } else {
                log.info("<-- {} took {} ms", method, ms);
            }
            return result;
        } catch (Throwable ex) {
            long ms = (System.nanoTime() - start) / 1_000_000;
            log.error("<-- {} failed after {} ms: {}", method, ms, ex.toString());
            throw ex;
        }
    }
}