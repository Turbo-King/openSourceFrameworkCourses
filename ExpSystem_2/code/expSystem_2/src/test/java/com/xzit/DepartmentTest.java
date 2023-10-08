package com.xzit;

import com.xzit.domain.Department;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/10/8
 * \* TODO
 * \* Description:
 * \
 */
public class DepartmentTest {
    @Test
    public void getDepartment() {
        //1.加载 spring.xml 配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring-beans.xml");
        //2.通过 id 值获取对象
//        Department department1 = (Department) applicationContext.getBean("department1");
//        System.out.println(department1);
        Department department2 = (Department) applicationContext.getBean("department2");
        System.out.println(department2);
    }
}
