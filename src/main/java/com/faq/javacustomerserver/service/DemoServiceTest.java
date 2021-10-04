package com.faq.javacustomerserver.service;

import com.faq.javacustomerserver.annotation.AfterAspect;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceTest {
    @AfterAspect
    public void test(int id){
        System.out.println("lalaltest:"+id);
    }
}
