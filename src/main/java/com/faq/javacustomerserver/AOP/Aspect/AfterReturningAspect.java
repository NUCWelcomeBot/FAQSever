package com.faq.javacustomerserver.AOP.Aspect;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class AfterReturningAspect implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

    }
}