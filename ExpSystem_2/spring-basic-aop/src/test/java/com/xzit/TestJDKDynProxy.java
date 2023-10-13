package com.xzit;

import com.xzit.proxy.factory.JdkBeanFactory;
import com.xzit.service.UserService;
import org.junit.Test;

public class TestJDKDynProxy {
    @Test
    public void testJDKDynProxy() {
        UserService userService = JdkBeanFactory.createService();
        userService.addUser();
        userService.updateUser();
        userService.deleteUser();
    }
}
