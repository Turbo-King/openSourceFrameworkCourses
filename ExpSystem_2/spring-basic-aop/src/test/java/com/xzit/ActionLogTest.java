package com.xzit;

import com.xzit.annotation.ActionLog;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/10/12
 * \* TODO
 * \* Description: 注解测试
 * \
 */


public class ActionLogTest {
    @Test
    public void actionLogAnno() throws Exception {
        Class clazz = Class.forName("com.xzit.services.UserService");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ActionLog.class)) {
                ActionLog annotation = method.getAnnotation(ActionLog.class);
                String operationType = annotation.operationType();
                String operationName = annotation.operationName();
                System.out.println(operationType + "," + operationName);
            }
        }
    }
}


