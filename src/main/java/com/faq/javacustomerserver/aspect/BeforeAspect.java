package com.faq.javacustomerserver.aspect;

import com.faq.javacustomerserver.dao.Mapper.KeyMapper;
import com.faq.javacustomerserver.dao.Mapper.QAMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class BeforeAspect {
    @Autowired
    private KeyMapper keyMapper;
    @Autowired
    private QAMapper qaMapper;
    @Before("@annotation(com.faq.javacustomerserver.annotation.BeforeKeyCutPoint)")
    public void beforeKeyCutPoint(JoinPoint joinPoint){
        var args = joinPoint.getArgs();
        var id = ((Integer) args[0]);
        var key = keyMapper.findById(id).get();
        if(key == null){
            throw new NullPointerException("key不存在！");
        }
        key.addCount();
        keyMapper.save(key);
    }
    @Before("@annotation(com.faq.javacustomerserver.annotation.BeforeQACutPoint)")
    public void beforeQACutPoint(JoinPoint joinPoint){
        var args = joinPoint.getArgs();
        var id = ((Integer) args[0]);
        var qa = qaMapper.findById(id).get();
        if(qa == null){
            throw new NullPointerException("key不存在！");
        }
        qa.addCount();
        qaMapper.save(qa);
    }
}

