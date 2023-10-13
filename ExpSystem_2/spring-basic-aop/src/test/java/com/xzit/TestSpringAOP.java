package com.xzit;
import com.xzit.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/10/10
 * \* TODO
 * \* Description:
 * \
 */
// 测试类
public class TestSpringAOP {

    @Test
    public void demo01() {

        String xmlPath = "spring-beans.xml";
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(xmlPath);
        // 获取目标类对象
        UserService userService =
                (UserService) applicationContext.getBean("userServiceId");
        userService.addUser();
        userService.updateUser();
        userService.deleteUser();
    }
}