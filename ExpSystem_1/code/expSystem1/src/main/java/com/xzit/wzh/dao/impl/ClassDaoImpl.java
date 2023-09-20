package com.xzit.wzh.dao.impl;

import com.xzit.wzh.dao.IClassDao;
import com.xzit.wzh.dao.IMajorDao;
import com.xzit.wzh.domain.ClassInfo;
import com.xzit.wzh.domain.MajorInfo;
import com.xzit.wzh.utils.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: ClassDao实现类
 * \
 */
public class ClassDaoImpl implements IClassDao {
    @Override
    public ClassInfo selectByClassId(Long classId) {
        String sql = "select * from sys_classinfo where class_id=?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ClassInfo classInfo;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, classId);
            rs = ps.executeQuery();

            // 将结果转为对象
            classInfo = DBConnectionUtil.toClassObject(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBConnectionUtil.release(rs, ps, connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return classInfo;
    }

    @Override
    public List<ClassInfo> selectByMajorId(Long majorId) {
        String sql = "select * from sys_classinfo where major_id=?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<ClassInfo> classInfoList = new ArrayList<>();
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, majorId);
            rs = ps.executeQuery();

            // 将结果转为对象
            while (rs.next()) {
                classInfoList.add(DBConnectionUtil.toClassObject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBConnectionUtil.release(rs, ps, connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return classInfoList;
    }

    @Override
    public List<ClassInfo> selectByClassName(String className) {
        return null;
    }

    @Override
    public int insert(ClassInfo classInfo) throws Exception {
        return 0;
    }

    @Override
    public int update(ClassInfo classInfo) throws Exception {
        return 0;
    }

    @Override
    public int deleteByClassId(Long classId) {
        return 0;
    }

    @Override
    public int deleteByClassIds(Long[] classIds) {
        return 0;
    }


}
