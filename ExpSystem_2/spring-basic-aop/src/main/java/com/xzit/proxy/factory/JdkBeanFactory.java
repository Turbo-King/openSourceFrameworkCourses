package com.xzit.proxy.factory;

import com.xzit.proxy.aspect.MyAspect;
import com.xzit.service.UserService;
import com.xzit.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkBeanFactory {
    public static UserService createService() {
        // 1、先有目标类对象
        final UserService userService = new UserServiceImpl();
        // 2、再有切面类
        final MyAspect myAspect = new MyAspect();
        /* 3、最后有代理类，将目标类(切入点)和切面类(通知)进行结合 =》 切面 * Proxy.newProxyInstance * 参数1：ClassLoader loader 类加载器，我们知道，动态代理类在运行时创建的，任何类都需要类加载器将其加载到内存。 * 类加载器该如何写呢？ * 答：一般情况下：当前类.class.getClassLoader() * 或者 目标类的实例.getClass().getClassLoader() * * 参数2：Class[] interfaces 代理类需要实现的所有接口
        * 方式1：目标类的实例.getClass().getInterfaces()
             注意：该方式只能获得自己接口，不能获得父元素接口
        * 方式2：new Class[]{UserService.class}
        * 参数3：InvocationHandler h
             处理类，是一个接口，必须进行实现类，一般情况下采用：匿名内部类,
             该接口提供了一个 invoke 方法，代理类的每一个方法执行时，都将调用一次invoke 方法
        * 参数31：Object proxy 代理对象
        * 参数32：Method method 代理对象当前执行的方法的描述对象（反射）
           * 执行的方法名：method.getName()
           * 执行的方法：method.invoke(对象, 实际参数)
        * 参数33：Object[] args 方法的实际参数 */
        UserService proxyService = (UserService) Proxy.newProxyInstance(
                MyAspect.class.getClassLoader(),
                userService.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 前执行
                        myAspect.before();
                        // 执行目标类的方法
                        Object obj = method.invoke(userService, args);
                        // 后执行
                        myAspect.after();
                        return obj;
                    }
                });
        return proxyService;
    }
}
