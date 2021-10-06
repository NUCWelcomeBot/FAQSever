package com.faq.javacustomerserver.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AfterAspect {
    @After("@annotation(com.faq.javacustomerserver.annotation.AfterKeyCutPoint)")
    public void afterKeyCutPoint(JoinPoint joinPoint){
        var args = joinPoint.getArgs();
        for(Object arg:args)
            System.out.println("args:"+arg);
    }
    @After("@annotation(com.faq.javacustomerserver.annotation.AfterQACutPoint)")
    public void afterQACutPoint(JoinPoint joinPoint){
        var args = joinPoint.getArgs();
        for(Object arg:args)
            System.out.println("args:"+arg);
    }
}
