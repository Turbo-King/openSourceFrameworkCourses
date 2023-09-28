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
    public List<ClassInfo> selectAll() {
        List<ClassInfo> classInfos = new ArrayList<>();
        String sql = "select * from sys_majorinfo ";
        try {
            //2.创建连接对象
            Connection conn = DBConnectionUtil.getConnection();
            //3.创建语句对象
            PreparedStatement psmt = conn.prepareStatement(sql);
            //4.执行SQL命令，获得ResultSet对象
            ResultSet rs = psmt.executeQuery();
            //5.解析结果
            while (rs != null && rs.next()) {
                // 将结果转为对象
                classInfos.add(DBConnectionUtil.toClassObject(rs));
            }
            //6.释放资源
            DBConnectionUtil.release(rs, psmt, conn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return classInfos;
    }

    @Override
    public List<ClassInfo> limit(String likeClassName, int curPage, int pageSize) {
        List<ClassInfo> classInfos = new ArrayList<>();
        String sql = "select * from sys_majorinfo where major_name like ? limit ?,?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + likeClassName + "%");
            ps.setInt(2, curPage);
            ps.setInt(3, pageSize);
            rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                // 将结果转为对象
                classInfos.add(DBConnectionUtil.toClassObject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(rs, ps, connection);
        }
        return classInfos;
    }

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
            // 释放资源
            DBConnectionUtil.release(rs, ps, connection);
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
            while (rs != null && rs.next()) {
                classInfoList.add(DBConnectionUtil.toClassObject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(rs, ps, connection);
        }

        return classInfoList;
    }

    @Override
    public List<ClassInfo> selectByClassName(String className) {
        String sql = "select * from sys_classinfo where major_id like ?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<ClassInfo> classInfoList = new ArrayList<>();
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + className + "%");
            rs = ps.executeQuery();

            // 将结果转为对象
            while (rs != null && rs.next()) {
                classInfoList.add(DBConnectionUtil.toClassObject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(rs, ps, connection);
        }

        return classInfoList;
    }

    @Override
    public int insert(ClassInfo classInfo) throws Exception {
        String sql = "insert into sys_classinfo(" +
                "major_id, " +
                "class_name, " +
                "academic_year, " +
                "semester, " +
                "class_advisor, " +
                "number_students, " +
                "status, " +
                "create_date, " +
                "class_description) values (?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        int row = 0;
        PreparedStatement ps = null;

        // 不允许空对象，直接返回
        if (classInfo == null) {
            return row;
        }
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, classInfo.getMajorId());
            ps.setString(2, classInfo.getClassName());
            ps.setString(3, classInfo.getAcademicYear());

            /* 以下参数可能为NULL */
            if (classInfo.getSemester() == null) {
                ps.setNull(4, Types.VARCHAR);
            } else {
                ps.setString(4, classInfo.getSemester());
            }

            if (classInfo.getClassAdvisor() == null) {
                ps.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(5, classInfo.getClassAdvisor());
            }

            if (classInfo.getNumberStudents() == null) {
                ps.setNull(6, Types.VARCHAR);
            } else {
                ps.setLong(6, classInfo.getNumberStudents());
            }

            if (classInfo.getStatus() == null) {
                ps.setNull(7, Types.VARCHAR);
            } else {
                ps.setString(7, classInfo.getStatus());
            }

            if (classInfo.getCreateDate() == null) {
                ps.setNull(8, Types.VARCHAR);
            } else {
                ps.setString(8, classInfo.getCreateDate());
            }

            if (classInfo.getClassDescription() == null) {
                ps.setNull(9, Types.CHAR);
            } else {
                ps.setString(9, classInfo.getClassDescription());
            }


            row = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(null, ps, connection);
        }
        return row;
    }

    @Override
    public int update(ClassInfo classInfo) throws Exception {
        String sql = "update sys_classinfo " +
                "set major_id=?," +
                "class_name=?, " +
                "academic_year=?, " +
                "semester=?, " +
                "class_advisor=?, " +
                "number_students=?, " +
                "status=?, " +
                "create_date=?, " +
                "class_description=? " +
                "where class_id=?";
        Connection connection = null;
        int row = 0;
        PreparedStatement ps = null;
        // 不允许空对象，直接返回
        if (classInfo == null) {
            return row;
        }
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, classInfo.getMajorId());
            ps.setString(2, classInfo.getClassName());
            ps.setString(3, classInfo.getAcademicYear());
            ps.setString(4, classInfo.getSemester());
            ps.setString(5, classInfo.getClassAdvisor());
            ps.setLong(6, classInfo.getNumberStudents());
            ps.setString(7, classInfo.getStatus());
            ps.setString(8, classInfo.getCreateDate());
            ps.setString(9, classInfo.getClassDescription());
            ps.setLong(10, classInfo.getClassId());
            row = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(null, ps, connection);
        }
        return row;
    }

    @Override
    public int deleteByClassId(Long classId) {
        String sql = "delete from sys_classinfo where class_id=?";
        Connection connection = null;
        int row = 0;
        PreparedStatement ps = null;
        // 判断传入id是否为null
        if (classId == null) {
            return row;
        }
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, classId);
            row = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(null, ps, connection);
        }
        return row;
    }

    @Override
    public int deleteByClassIds(Long[] classIds) {
        int row = 0;
        // 判断无数据直接返回
        if (classIds.length == 0) {
            return row;
        }
        for (Long classId : classIds) {
            row += deleteByClassId(classId);
        }
        return row;
    }


}
