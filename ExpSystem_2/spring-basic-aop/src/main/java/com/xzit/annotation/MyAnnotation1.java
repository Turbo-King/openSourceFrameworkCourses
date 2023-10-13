package com.xzit.annotation;

import java.lang.annotation.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/10/12
 * \* TODO
 * \* Description: 自定义注解
 * \
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation1 {
    String value();

    int sex() default 1;
}
