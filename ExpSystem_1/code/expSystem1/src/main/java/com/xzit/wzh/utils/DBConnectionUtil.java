package com.xzit.wzh.utils;

import com.xzit.wzh.domain.*;

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
    public static void release(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Author: wzh
     * @Date: 2023/9/14 15:28
     * @Param: [resultSet]
     * @Return: com.xzit.wzh.domain.MajorInfo
     * @Description: ResultSet转换为Major对象
     */
    public static MajorInfo toMajorObject(ResultSet resultSet) throws SQLException {
        MajorInfo majorInfo = new MajorInfo();
        if (resultSet != null) {
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


    /**
     * @Author: wzh
     * @Date: 2023/9/20 08:37
     * @Param: [resultSet]
     * @Return: com.xzit.wzh.domain.ClassInfo
     * @Description: ResultSet转换为Class对象
     */
    public static ClassInfo toClassObject(ResultSet resultSet) throws SQLException {
        ClassInfo classInfo = new ClassInfo();
        if (resultSet.next()) {
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

    /**
     * @Author: wzh
     * @Date: 2023/9/20 08:39
     * @Param: [resultSet]
     * @Return: com.xzit.wzh.domain.ClassInfo
     * @Description: ResultSet转换为Course对象
     */
    public static CourseInfo toCourseObject(ResultSet resultSet) throws SQLException {
        CourseInfo courseInfo = new CourseInfo();
        if (resultSet != null) {
            courseInfo.setCourseId(resultSet.getLong("course_id"));
            courseInfo.setCourseName(resultSet.getString("course_name"));
            courseInfo.setCourseCode(resultSet.getString("course_code"));
            courseInfo.setTeacherId(resultSet.getLong("teacher_id"));
            courseInfo.setUnderSchool(resultSet.getString("under_school"));
            courseInfo.setCredit(resultSet.getString("credit"));
            courseInfo.setSemester(resultSet.getString("semester"));
            courseInfo.setClassTime(resultSet.getString("class_time"));
            courseInfo.setClassLocation(resultSet.getString("class_location"));
            courseInfo.setCourseDescription(resultSet.getString("course_description"));
        }
        return courseInfo;
    }

    /**
     * @Author: wzh
     * @Date: 2023/9/20 08:39
     * @Param: [resultSet]
     * @Return: com.xzit.wzh.domain.ClassInfo
     * @Description: ResultSet转换为Student对象
     */
    public static StudentInfo toStudentObject(ResultSet resultSet) throws SQLException {
        StudentInfo studentInfo = new StudentInfo();
        if (resultSet != null) {
            studentInfo.setStudentId(resultSet.getLong("student_id"));
            studentInfo.setClassId(resultSet.getLong("class_id"));
            studentInfo.setName(resultSet.getString("name"));
            studentInfo.setGender(resultSet.getString("gender"));
            studentInfo.setUderSchool(resultSet.getString("uder_school"));
            studentInfo.setAdmissionYear(resultSet.getString("admission_year"));
            studentInfo.setDateBirth(resultSet.getString("date_birth"));
            studentInfo.setContactInfo(resultSet.getString("contact_info"));
            studentInfo.setAddress(resultSet.getString("address"));
        }
        return studentInfo;
    }

    /**
     * @Author: wzh
     * @Date: 2023/9/20 08:40
     * @Param: [resultSet]
     * @Return: com.xzit.wzh.domain.ClassInfo
     * @Description: ResultSet转换为Teacher对象
     */
    public static TeacherInfo toTeacherObject(ResultSet resultSet) throws SQLException {
        TeacherInfo teacherInfo = new TeacherInfo();
        if (resultSet != null) {
            teacherInfo.setTeacherId(resultSet.getLong("teacher_id"));
            teacherInfo.setName(resultSet.getString("name"));
            teacherInfo.setGender(resultSet.getString("gender"));
            teacherInfo.setDateBirth(resultSet.getString("date_birth"));
            teacherInfo.setUderSchool(resultSet.getString("uder_school"));
            teacherInfo.setTitle(resultSet.getString("title"));
            teacherInfo.setContactInfo(resultSet.getString("contact_info"));
            teacherInfo.setAddress(resultSet.getString("address"));
        }
        return teacherInfo;
    }
}
