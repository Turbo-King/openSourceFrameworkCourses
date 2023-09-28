package com.xzit.wzh.dao.impl;

import com.xzit.wzh.dao.ICourseDao;
import com.xzit.wzh.dao.IMajorDao;
import com.xzit.wzh.domain.CourseInfo;
import com.xzit.wzh.domain.MajorInfo;
import com.xzit.wzh.utils.DBConnectionUtil;

import java.sql.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: MajorDao实现类
 * \
 */
public class CourseDaoImpl implements ICourseDao {

    @Override
    public CourseInfo selectByCourseId(Long courseId) {
        return null;
    }

    @Override
    public CourseInfo selectByCourseCode(String courseCode) {
        return null;
    }

    @Override
    public CourseInfo selectByCourseName(String courseName) {
        return null;
    }

    @Override
    public int insert(CourseInfo courseInfo) throws Exception {
        return 0;
    }

    @Override
    public int update(CourseInfo courseInfo) throws Exception {
        return 0;
    }

    @Override
    public int deleteByCourseId(Long CourseId) {
        return 0;
    }

    @Override
    public int deleteByCourseIds(Long[] CourseIds) {
        return 0;
    }
}
