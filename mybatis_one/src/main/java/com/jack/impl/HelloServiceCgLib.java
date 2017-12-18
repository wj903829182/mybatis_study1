package com.jack.impl;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * create by jack 2017/12/18
 */
public class HelloServiceCgLib implements MethodInterceptor {

    private Object target;


    /**
     * 创建代理对象
     * @param target
     * @return
     */
    public Object getInstance(Object target){
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        //回调方法
        enhancer.setCallback(this);
        //创建代理对象
        return  enhancer.create();
    }


    /**
     * 回调方法
     * @param object
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("------------我是CGLIB的动态代理--------------");
        //反射方法前调用
        System.out.println("我准备说hello");
        Object returnObj = methodProxy.invokeSuper(object, objects);
        //反射方法后调用
        System.out.println("我说过hello了");
        return returnObj;
    }
}
