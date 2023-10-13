package com.xzit;

import com.xzit.proxy.factory.CglibBeanFactory;
import com.xzit.service.cglib.UserServiceImpl;
import org.junit.Test;

public class TestCglibDynProxy {
    @Test
    public void testCglibDynProxy() {
        UserServiceImpl userService = CglibBeanFactory.createService();
        userService.addUser();
        userService.updateUser();
        userService.deleteUser();
    }
}
