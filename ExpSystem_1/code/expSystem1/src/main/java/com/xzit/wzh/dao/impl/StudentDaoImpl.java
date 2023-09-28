package com.xzit.wzh.dao.impl;

import com.xzit.wzh.dao.IMajorDao;
import com.xzit.wzh.dao.IStudentDao;
import com.xzit.wzh.domain.MajorInfo;
import com.xzit.wzh.domain.StudentInfo;
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
public class StudentDaoImpl implements IStudentDao {
    @Override
    public StudentInfo selectByStudentId(Long studentId) {
        return null;
    }

    @Override
    public List<StudentInfo> selectByClassId(String classId) {
        return null;
    }

    @Override
    public List<StudentInfo> selectByStudentName(String studentName) {
        return null;
    }

    @Override
    public int insert(StudentInfo studentInfo) throws Exception {
        return 0;
    }

    @Override
    public int update(StudentInfo studentInfo) throws Exception {
        return 0;
    }

    @Override
    public int deleteByStudentId(Long studentId) {
        return 0;
    }

    @Override
    public int deleteByStudentIds(Long[] studentIds) {
        return 0;
    }
}
