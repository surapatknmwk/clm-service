package com.ktb.clmapiauthen.models.annotation;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@Aspect
public class LoggingAspect {

    @Pointcut("@annotation(com.ktb.clmapiauthen.models.annotation.Loggable)")
    public void loggableMethods() {
    }

    @Before("loggableMethods()")
    public void logMethod(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Object[] signatureArgs = jp.getArgs();
        log.info("=================== Executing method: {} ARG : {}", methodName, signatureArgs);
    }

}
