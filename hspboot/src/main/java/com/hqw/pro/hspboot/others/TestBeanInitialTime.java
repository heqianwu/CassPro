package com.hqw.pro.hspboot.others;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestBeanInitialTime implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, ApplicationListener{

    @Autowired
    TestK testk;

    //先运行构造方法实例化或构造方法注入，再通过@AutoWired属性注入
    public TestBeanInitialTime(){
//        System.out.println("TestBeanInitialTime:Construct"+testk+":"+testk.strTest);  //先运行构造方法实例化，再通过@AutoWired属性注入
        System.out.println("TestBeanInitialTime:Construct");
    }

//    @Autowired   //加不加都可以，自动判断是否 AbstractAutowireCapableBeanFactory/createBeanInstance/autowireConstructor
//    public TestBeanInitialTime(TestK testk){
//        this.testk=testk;
//        System.out.println("TestBeanInitialTime:Construct"+testk+":"+testk.strTest);
//        System.out.println("TestBeanInitialTime:Construct");
//    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("BeanNameAware:setBeanName");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware:setBeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware:setApplicationContext"+testk+":"+testk.strTest);
        System.out.println("ApplicationContextAware:setApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean:afterPropertiesSet"+testk+":"+testk.strTest);
        System.out.println("InitializingBean:afterPropertiesSet");
    }

    @PostConstruct
    public void initPostConstruct(){
        System.out.println("PostConstruct:initPostConstruct"+testk+":"+testk.strTest);
        System.out.println("PostConstruct:initPostConstruct");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.print("ApplicationListener:");
        if (event instanceof ApplicationStartedEvent){
            System.out.println("onApplicationEvent:ApplicationStartedEvent");
        }
        else if (event instanceof ContextStartedEvent){
            System.out.println("onApplicationEvent:ContextStartedEvent");
        }
        else if (event instanceof ContextRefreshedEvent){
            System.out.println("onApplicationEvent:ContextRefreshedEvent");
        }
        else if (event instanceof ContextClosedEvent){
            System.out.println("onApplicationEvent:ContextClosedEvent");
        }
        else if (event instanceof ContextStoppedEvent){
            System.out.println("onApplicationEvent:ContextStartedEvent");
        }
        else if (event instanceof ApplicationReadyEvent){
            System.out.println("onApplicationEvent:ApplicationReadyEvent");
        }
        else
            System.out.println("onApplicationEvent:"+event.getClass().getSimpleName());
    }



}
