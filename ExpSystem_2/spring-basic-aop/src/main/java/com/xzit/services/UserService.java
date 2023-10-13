package com.xzit.services;

import com.xzit.annotation.ActionLog;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/10/12
 * \* TODO
 * \* Description:
 * \
 */

public class UserService {
    @ActionLog(operationType = "insert操作", operationName = "添加用户")
    public void insert(String username) {
        System.out.println("添加用户...");
    }

    @ActionLog(operationType = "update操作", operationName = "更新用户")
    public void update(String username) {
        System.out.println("更新用户...");
    }

    @ActionLog(operationType = "delete操作", operationName = "删除用户")
    public void delete(String username) {
        System.out.println("删除用户...");
    }

    @ActionLog(operationType = "query操作", operationName = "查询用户")
    public String query(String username) {
        System.out.println("查询用户...");
        return "用户信息";
    }
}
