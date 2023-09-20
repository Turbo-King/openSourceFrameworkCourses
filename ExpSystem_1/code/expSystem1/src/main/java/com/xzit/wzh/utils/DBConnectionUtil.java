package com.xzit.wzh.utils;

import com.xzit.wzh.domain.ClassInfo;
import com.xzit.wzh.domain.MajorInfo;

import java.sql.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: 数据库连接工具类
 * \
 */
public class DBConnectionUtil {
    /**
     * @Author: wzh
     * @Date: 2023/9/14 12:42
     * @Param:
     * @Return: java.sql.Connection
     * @Description: 数据库连接
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/exp_system1";//数据库连接子协议
            String name = "root";
            String password = "wzh";
            Class.forName(driver);   //加载数据库驱动
            con = DriverManager.getConnection(url, name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * @Author: wzh
     * @Date: 2023/9/14 12:46
     * @Param: [resultSet, preparedStatement, connection]
     * @Return: void
     * @Description: 资源释放
     */
    public static void release(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) throws SQLException {
        if (resultSet != null) resultSet.close();
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
    }

    /**
     * @Author: wzh
     * @Date: 2023/9/14 15:28
     * @Param: [resultSet]
     * @Return: com.xzit.wzh.domain.MajorInfo
     * @Description: ResultSet转换为对象
     */
    public static MajorInfo toMajorObject(ResultSet resultSet) throws SQLException {
        MajorInfo majorInfo = new MajorInfo();
        while (resultSet.next()) {
            majorInfo.setMajorId(resultSet.getLong("major_id"));
            majorInfo.setInnerCode(resultSet.getString("inner_code"));
            majorInfo.setMajorName(resultSet.getString("major_name"));
            majorInfo.setUderSchool(resultSet.getString("uder_school"));
            majorInfo.setGenCode(resultSet.getString("gen_code"));
            majorInfo.setShortName(resultSet.getString("short_name"));
            majorInfo.setMajorEnName(resultSet.getString("major_en_name"));
            majorInfo.setEduSystem(resultSet.getString("edu_system"));
            majorInfo.setSetYm(resultSet.getString("set_ym"));
            majorInfo.setCulLevel(resultSet.getString("cul_level"));
            majorInfo.setState(resultSet.getString("state"));
            majorInfo.setMemo(resultSet.getString("memo"));
            majorInfo.setMajorLeader(resultSet.getString("major_leader"));
        }
        return majorInfo;
    }


    public static ClassInfo toClassObject(ResultSet resultSet) throws SQLException {
        ClassInfo classInfo = new ClassInfo();
        while (resultSet.next()) {
            classInfo.setClassId(resultSet.getLong("class_id"));
            classInfo.setMajorId(resultSet.getLong("major_id"));
            classInfo.setClassName(resultSet.getString("class_name"));
            classInfo.setAcademicYear(resultSet.getString("academic_year"));
            classInfo.setSemester(resultSet.getString("semester"));
            classInfo.setClassAdvisor(resultSet.getString("class_advisor"));
            classInfo.setNumberStudents(resultSet.getLong("number_students"));
            classInfo.setStatus(resultSet.getString("status"));
            classInfo.setCreateDate(resultSet.getString("create_date"));
            classInfo.setClassDescription(resultSet.getString("class_description"));
        }
        return classInfo;
    }
}
