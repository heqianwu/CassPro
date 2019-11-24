package com.hqw.pro.dynamicproxy;


import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.lang.reflect.Method;

//对于以Cglib方式增强的AOP目标类，会创建两个对象，
//   一个事Bean实例本身，一个是Cglib增强代理对象，而不仅仅是只有后者。
//被Spring的AOP增强的类，在同一个类的内部方法调用时，其被调用方法上的增强通知将不起作用。


//Spring的AOP实现方式有两种：1、Java代理方式；2、Cglib动态增强方式，这两种方式在Spring中是可以无缝自由切换的。

//Cglib动态代理，实现MethodInterceptor接口
public class CglibProxy implements MethodInterceptor {
    private Object target;//需要代理的目标对象

    //重写拦截方法
    @Override
    public Object intercept(Object obj, Method method, Object[] arr, MethodProxy proxy) throws Throwable {
        System.out.println("Cglib动态代理，监听开始！");
        Object invoke = method.invoke(target, arr);//方法执行，参数：target 目标对象 arr参数数组
        System.out.println("Cglib动态代理，监听结束！");
        return invoke;
    }

    //定义获取代理对象方法
    public Object getCglibProxy(Object objectTarget) {
        //为目标对象target赋值
        this.target = objectTarget;
        Enhancer enhancer = new Enhancer();
        //设置父类,因为Cglib是针对指定的类生成一个子类，所以需要指定父类
        enhancer.setSuperclass(objectTarget.getClass());
        enhancer.setCallback(this);// 设置回调
        Object result = enhancer.create();//创建并返回代理对象
        return result;
    }

    public static void main(String[] args) {
        CglibProxy cglib = new CglibProxy();//实例化CglibProxy对象
        UserManager user = (UserManager) cglib.getCglibProxy(new UserManagerImpl());//获取代理对象
        user.delUser("admin");//执行删除方法
    }

}


class PersonService {
    private String value;

    public PersonService() {
        System.out.println("PersonService构造");
    }

    //该方法不能被子类覆盖
    final public Person getPerson(String code) {
        System.out.println("PersonService:getPerson>>" + code);
        return null;
    }

    public void setPerson() {
        System.out.println("PersonService:setPerson");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}


class Person {

}

class CglibProxyIntercepter implements MethodInterceptor {
    @Override
    public Object intercept(Object sub, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("执行前...");
        Object object = methodProxy.invokeSuper(sub, objects);
        System.out.println("执行后...");
        return object;
    }
}


class Test {
    public static void main(String[] args) {
        //代理类class文件存入本地磁盘
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PersonService.class);
        enhancer.setCallback(new CglibProxyIntercepter());
        PersonService proxy = (PersonService) enhancer.create();
        proxy.setPerson();
        proxy.getPerson("1");
    }


    public void testFastClass() throws Exception {
        FastClass fastClass = FastClass.create(PersonService.class);
        FastMethod fastMethod = fastClass.getMethod("getValue", new Class[0]);
        PersonService bean = new PersonService();
        bean.setValue("Hello world");
        System.out.println("Hello world".equals(fastMethod.invoke(bean, new Object[0])));
    }
}




















