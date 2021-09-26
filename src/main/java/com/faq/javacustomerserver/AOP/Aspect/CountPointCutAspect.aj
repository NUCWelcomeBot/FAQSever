package com.faq.javacustomerserver.AOP.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.apache.bcel.classfile.Method;

@Slf4j
public aspect CountPointCutAspect {

   pointcut recordLog():call();
}
