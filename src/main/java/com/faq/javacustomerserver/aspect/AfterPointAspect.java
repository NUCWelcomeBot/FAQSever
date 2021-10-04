package com.faq.javacustomerserver.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AfterPointAspect{
    @After("@annotation(com.faq.javacustomerserver.annotation.AfterAspect)")
    public void afterAspect(JoinPoint joinPoint){
        System.out.println("this is before output message");
        var args = joinPoint.getArgs();
        for(Object arg:args)
            System.out.println("args:"+arg);
    }

}
