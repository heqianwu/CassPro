package com.hqw.pro.hspboot.others;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

//@Component
public class BeanSimplePostProcessor implements BeanPostProcessor {

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor:postProcessAfterInitialization:" + bean.getClass().getSimpleName());
        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor:postProcessBeforeInitialization:" + bean.getClass().getSimpleName());
        return bean;
    }

}
