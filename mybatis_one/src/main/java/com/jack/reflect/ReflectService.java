package com.jack.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * create by jack 2017/12/18
 */
public class ReflectService {
    /**
     * 服务方法
     * @param name
     */
    public void sayHello(String name){
        System.out.println("hello , "+name);
    }

    public static void main(String[] args) {
        try {
            //通过反射创建ReflectService对象
            Object service = Class.forName(ReflectService.class.getName()).newInstance();
            //获取服务方法-sayHello
            Method method = service.getClass().getMethod("sayHello",String.class);
            //反射调用方法
            method.invoke(service, "jack");
            System.out.println(ReflectService.class.getName());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
