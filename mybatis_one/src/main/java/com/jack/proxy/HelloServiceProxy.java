package com.jack.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * create by jack 2017/12/18
 */
public class HelloServiceProxy implements InvocationHandler{

    /**
     * 真实服务对象
     */
    private Object target;

    /**
     *通过代理对象调用方法首先进入这个方法
     * @param proxy  代理对象
     * @param method  被调用方法
     * @param args    方法的参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("-----------这是JDK动态代理---------------");
        Object result = null;
        //反射方法前调用
        System.out.println("我准备说hello......");
        //执行方法，相当于调用HelloServiceImpl类的sayHello方法
        result = method.invoke(target, args);
        //反射方法后调用
        System.out.println("我说过hello了");
        return result;
    }


    /**
     *绑定一个委托对象并返回一个代理类
     * @param target
     * @return
     */
    public Object bind(Object target) {
        this.target = target;
        //取得代理对象，jdk代理需要提供接口
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }



}
