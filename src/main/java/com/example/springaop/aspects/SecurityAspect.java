package com.example.springaop.aspects;


import com.example.springaop.annotations.RequiresRole;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    @Before("@annotation(requiresRole)")
    public void checkAccess(RequiresRole requiresRole) {
        String requiredRole = requiresRole.value();
        String currentUserRole = getCurrentUserRole();
        if (!requiredRole.equals(currentUserRole)) {
            throw new SecurityException("Access Denied. Required role : " + requiredRole);
        }

    }

    private String getCurrentUserRole() {
        //شبیه سازی نقش کاربر جاری
        return "user"; // or ADMIN
    }

}
