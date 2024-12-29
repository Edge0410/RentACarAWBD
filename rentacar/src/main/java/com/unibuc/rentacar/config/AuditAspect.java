package com.unibuc.rentacar.config;

import com.unibuc.rentacar.services.AuditService;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditService auditService;

    @AfterReturning("execution(* com.unibuc.rentacar.services.*.create*(..))")
    public void logCreate(JoinPoint joinPoint) {
        String entityService = joinPoint.getSignature().getDeclaringTypeName().substring(joinPoint.getSignature().getDeclaringTypeName().lastIndexOf('.') + 1);
        String entity = entityService.substring(0, entityService.length() - "Service".length());
        String message = "Created entity in " + entity;
        System.out.println(auditService.logAudit(message));
    }

    @AfterReturning("execution(* com.unibuc.rentacar.services.*.update*(..))")
    public void logUpdate(JoinPoint joinPoint) {
        String entityService = joinPoint.getSignature().getDeclaringTypeName().substring(joinPoint.getSignature().getDeclaringTypeName().lastIndexOf('.') + 1);
        String entity = entityService.substring(0, entityService.length() - "Service".length());
        String message = "Updated entity in " + entity;
        System.out.println(auditService.logAudit(message));
    }

    @AfterReturning("execution(* com.unibuc.rentacar.services.*.delete*(..))")
    public void logDelete(JoinPoint joinPoint) {
        String entityService = joinPoint.getSignature().getDeclaringTypeName().substring(joinPoint.getSignature().getDeclaringTypeName().lastIndexOf('.') + 1);
        String entity = entityService.substring(0, entityService.length() - "Service".length());
        String message = "Deleted entity in " + entity;
        System.out.println(auditService.logAudit(message));
    }
}
