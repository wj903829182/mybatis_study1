package com.jack.test;

import com.jack.impl.HelloServiceCgLib;
import com.jack.impl.HelloServiceImpl;

/**
 * create by jack 2017/12/18
 */
public class CgLibMain {
    public static void main(String[] args) {
        HelloServiceCgLib cgLib = new HelloServiceCgLib();
        HelloServiceImpl proxyImpl = (HelloServiceImpl) cgLib.getInstance(new HelloServiceImpl());
        proxyImpl.sayHello("jack");
    }
}
