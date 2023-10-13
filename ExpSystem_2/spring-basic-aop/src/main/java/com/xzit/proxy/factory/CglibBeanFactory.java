package com.xzit.proxy.factory;

import com.xzit.proxy.aspect.MyAspect;
import com.xzit.service.cglib.UserServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibBeanFactory {
    public static UserServiceImpl createService() {
        // 1、先有目标类对象
        final UserServiceImpl userServiceImpl = new UserServiceImpl();
        // 2、再有切面类对象
        final MyAspect myAspect = new MyAspect();
        // 3、最后有代理类，采用cglib，底层创建目标类的子类
        // 3.1、核心类
        Enhancer enhancer = new Enhancer();
        // 3.2 、先确定父类
        enhancer.setSuperclass(userServiceImpl.getClass());
        /* 3.3、 设置回调函数 ，
         * MethodInterceptor接口 等效 jdk中的 InvocationHandler接口
         * intercept() 等效 jdk中的 invoke()
         * 参数1、参数2、参数3：和以invoke()方法的参数一样
         * 参数4：methodProxy 方法的代理 */
        enhancer.setCallback(new MethodInterceptor() {

            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                // 前执行
                myAspect.before();
                // 执行目标类的方法
                Object obj = method.invoke(userServiceImpl, args);
                // 执行代理类的父类，就是执行目标类，相当于inwoke调用了2次
                methodProxy.invokeSuper(proxy, args);
                // 后执行
                myAspect.after();
                return obj;
            }});
        // 4、创建代理
        UserServiceImpl proxyService = (UserServiceImpl) enhancer.create();
        return proxyService;
    }
}
