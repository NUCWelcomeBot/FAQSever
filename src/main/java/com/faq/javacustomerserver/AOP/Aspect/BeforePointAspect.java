package com.faq.javacustomerserver.AOP.Aspect;

import com.faq.javacustomerserver.AOP.PointCut.BeforeCountPointCut;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.apache.bcel.classfile.Method;

public class BeforePointAspect implements BeforeCountPointCut {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return null;
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {

    }
}
