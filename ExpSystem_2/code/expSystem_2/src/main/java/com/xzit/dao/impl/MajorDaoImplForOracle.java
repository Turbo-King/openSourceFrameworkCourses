package com.xzit.dao.impl;

import com.xzit.dao.MajorDao;

public class MajorDaoImplForOracle implements MajorDao {
    @Override
    public int add(String majorName) {
        System.out.println("使用Oracle实现专业信息的添加");
        return 1;
    }
}
