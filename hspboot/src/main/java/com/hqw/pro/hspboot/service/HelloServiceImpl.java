package com.hqw.pro.hspboot.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public String sayHello() {
        System.out.println("HelloServiceImpl:sayHello:" + applicationContext.getClass().getSimpleName() + ":" + applicationContext);
        return "ok, hello";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("HelloServiceImpl:setApplicationContext:" + applicationContext.getClass().getSimpleName() + ":" + applicationContext);
    }


}
