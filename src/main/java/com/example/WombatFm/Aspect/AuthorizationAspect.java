package com.example.WombatFm.Aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.WombatFm.RequiredRole;

import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class AuthorizationAspect {
    @Autowired
    private HttpSession httpSession;

    @Before("@annotation(requiredRole)")
    public void checkAuthorization(JoinPoint joinPoint, RequiredRole requiredRole)
            throws Throwable {
        String[] roles = requiredRole.value();

        String usernameSession = (String) httpSession.getAttribute("username");
        String roleSession = (String) httpSession.getAttribute("role");

        if (usernameSession == null) {
            throw new Error("You need to login");
        }

        if (Arrays.asList(roles).contains("*") || Arrays.asList(roles).contains(roleSession)) {
            return;
        }

        throw new Error("You do not have required role");
    }
}
