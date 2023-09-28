package com.xzit.wzh.dao.impl;

import com.xzit.wzh.dao.IMajorDao;
import com.xzit.wzh.dao.ITeacherDao;
import com.xzit.wzh.domain.MajorInfo;
import com.xzit.wzh.domain.TeacherInfo;
import com.xzit.wzh.utils.DBConnectionUtil;

import java.sql.*;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: MajorDao实现类
 * \
 */
public class TeacherDaoImpl implements ITeacherDao {

    @Override
    public TeacherInfo selectByTeacherId(Long teacherId) {
        return null;
    }

    @Override
    public List<TeacherInfo> selectByTeacherName(String teacherName) {
        return null;
    }

    @Override
    public int insert(TeacherInfo teacherInfo) throws Exception {
        return 0;
    }

    @Override
    public int update(TeacherInfo teacherInfo) throws Exception {
        return 0;
    }

    @Override
    public int deleteByTeacherId(Long teacherId) {
        return 0;
    }

    @Override
    public int deleteByTeacherIds(Long[] teacherIds) {
        return 0;
    }
}
