package com.faq.javacustomerserver.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class BeforePointAspect{
    @Before("@annotation(com.faq.javacustomerserver.annotation.BeforeAspect)")
    public void beforeAspect(JoinPoint joinPoint){
        System.out.println("this is before output message");
        var args = joinPoint.getArgs();
        for(Object arg:args)
            System.out.println("args:"+arg);
    }
}

