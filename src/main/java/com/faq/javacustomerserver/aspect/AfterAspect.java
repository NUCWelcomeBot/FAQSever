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
        // 参考before中的代码，可随意定义，JoinPoint为所AOP所修饰的函数参数
    }
    @After("@annotation(com.faq.javacustomerserver.annotation.AfterQACutPoint)")
    public void afterQACutPoint(JoinPoint joinPoint){
        // 参考before中的代码，可随意定义，JoinPoint为所AOP所修饰的函数参数
    }
}
