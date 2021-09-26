package com.faq.javacustomerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.faq.javacustomerserver.DAO"})
@EnableCaching
@EnableSwagger2
public class JavaCustomerServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaCustomerServerApplication.class, args);
    }
}