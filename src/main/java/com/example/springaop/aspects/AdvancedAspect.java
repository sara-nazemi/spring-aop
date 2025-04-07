package com.example.springaop.aspects;

import com.example.springaop.annotations.SecureAccess;
import com.example.springaop.userContext.UserContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdvancedAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdvancedAspect.class);


    public Object secureAndLog(ProceedingJoinPoint joinPoint, SecureAccess secureAccess) throws Throwable {
        String requiredRole = secureAccess.role();
        String description = secureAccess.description();
        String currentRole = UserContext.getRole();
        LOGGER.info("Requesting method: {} | Required Role: {} | Description: {}",
                joinPoint.getSignature().getName(), requiredRole, description);
        if (!requiredRole.equals(currentRole)) {
            LOGGER.warn("Access denied for role: {} on method {}", currentRole, joinPoint.getSignature().getName());
            throw new SecurityException("Access denied !");
        }

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            LOGGER.info("Executed method: {} | Duration: {} ms", joinPoint.getSignature().getName(), duration);
            return result;
        } catch (Throwable ex) {
            LOGGER.error("Exception is method: {} | Duration: {} ms",
                    joinPoint.getSignature().getName(), ex.getMessage());
            throw ex;
        }

    }
}
