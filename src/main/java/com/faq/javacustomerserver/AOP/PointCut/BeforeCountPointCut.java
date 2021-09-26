package com.faq.javacustomerserver.AOP.PointCut;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.apache.bcel.classfile.Method;
import org.springframework.aop.BeforeAdvice;
import org.springframework.stereotype.Component;


public interface BeforeCountPointCut extends BeforeAdvice {
    Object invoke(MethodInvocation invocation) throws Throwable;
    void before(Method method,Object[] args,Object target) throws Throwable;
}