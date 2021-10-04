package com.faq.javacustomerserver.aop.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AfterPointAspect{
    @After("@annotation(com.faq.javacustomerserver.aop.annotation.DemoAspect)")
    public void beforeAspect(){

    }
}
