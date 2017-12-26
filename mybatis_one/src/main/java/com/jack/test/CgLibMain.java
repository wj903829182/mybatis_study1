package com.jack.test;

import com.jack.impl.HelloServiceCgLib;
import com.jack.impl.HelloServiceImpl;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;

/**
 * create by jack 2017/12/18
 */
public class CgLibMain {
    public static void main(String[] args) {
        HelloServiceCgLib cgLib = new HelloServiceCgLib();
        HelloServiceImpl proxyImpl = (HelloServiceImpl) cgLib.getInstance(new HelloServiceImpl());
        proxyImpl.sayHello("jack");
        StatementHandler a;
    }
}
