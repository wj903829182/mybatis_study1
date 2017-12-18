package com.jack.test;

import com.jack.impl.HelloServiceImpl;
import com.jack.proxy.HelloServiceProxy;
import com.jack.service.HelloService;

/**
 * create by jack 2017/12/18
 */
public class HelloServiceMain {
    public static void main(String[] args) {
        //创建实现代理接口对象
        HelloServiceProxy HelloHandler = new HelloServiceProxy();
        //获取代理对象
        HelloService proxy = (HelloService) HelloHandler.bind(new HelloServiceImpl());
        //方法调用
        proxy.sayHello("jack");
    }
}
