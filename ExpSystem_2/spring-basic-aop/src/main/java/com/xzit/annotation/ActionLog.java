package com.xzit.annotation;

import java.lang.annotation.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/10/12
 * \* TODO
 * \* Description:
 * \
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionLog {
    /**
     * 要执行的操作类型比如：add操作
     **/
    public String operationType() default "";

    /**
     * 要执行的具体操作比如：添加用户
     **/
    public String operationName() default "";
}
