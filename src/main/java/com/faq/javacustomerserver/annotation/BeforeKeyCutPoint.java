package com.faq.javacustomerserver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// 在请求执行前统计KeyEntity的访问量，After同理，只是变为了请求执行后，切面代码见aspect
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeKeyCutPoint {

}
