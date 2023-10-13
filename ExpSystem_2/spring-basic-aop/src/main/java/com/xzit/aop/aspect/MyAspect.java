package com.xzit.aop.aspect;

// 切面类

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/** * 切面类中需要删除之前自己写的通知，添加上AOP联盟提供的通知(即要增强的东西)，
 * 需要实现不同接口，接口就是规范，从而就确定方法名称。
 * 采用“环绕通知” MethodInterceptor
 *
 * */
@Component
@Aspect
public class MyAspect implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {

        System.out.println("目标方法执行前执行的代码");
        // 使用AOP联盟的环绕通知：必须手动执行目标方法
        Object obj = mi.proceed();
        System.out.println("目标方法执行后执行的代码");
        return obj;
    }
}
