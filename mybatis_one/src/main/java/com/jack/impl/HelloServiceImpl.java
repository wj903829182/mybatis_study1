package com.jack.impl;

import com.jack.service.HelloService;

/**
 * create by jack 2017/12/18
 */
public class HelloServiceImpl implements HelloService{
    public void sayHello(String name) {
        System.out.println("hello , "+name);
    }
}
