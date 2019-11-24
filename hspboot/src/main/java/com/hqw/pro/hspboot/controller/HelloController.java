package com.hqw.pro.hspboot.controller;

import com.hqw.pro.hspboot.service.HelloService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class HelloController implements ApplicationContextAware {

    @Autowired
    private HelloService helloService;

    private ApplicationContext applicationContext;

    @RequestMapping("hello")
    public String hello() {
        System.out.println("HelloController:hello:" + this.getClass().getSimpleName());
        String str = helloService.sayHello();
        System.out.println(str);
        System.out.println("HelloController:hello:" + this.getClass().getSimpleName());
        System.out.println("HelloController:hello:" + applicationContext.getClass().getSimpleName() + ":" + applicationContext);
        return "hello, this is a springboot demo. " + str;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("HelloController:setApplicationContext:" + applicationContext.getClass().getSimpleName() + ":" + applicationContext);
    }
}
