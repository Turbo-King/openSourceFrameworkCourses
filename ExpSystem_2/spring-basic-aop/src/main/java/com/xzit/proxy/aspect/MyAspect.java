package com.xzit.proxy.aspect;

public class MyAspect {
    public void before() {
        System.out.println("方法执行【前】所要完成的事情");
    }
    public void after() {
        System.out.println("方法执行【后】所要完成的事情");
    }
}
