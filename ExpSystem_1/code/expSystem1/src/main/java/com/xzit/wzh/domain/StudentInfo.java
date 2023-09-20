package com.xzit.wzh.domain;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/15
 * \* TODO
 * \* Description: 学生信息
 * \
 */

@Data
public class StudentInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    private Long studentId;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 学院
     */
    private String uderSchool;

    /**
     * 入学年份
     */
    private String admissionYear;

    /**
     * 出生日期
     */
    private String dateBirth;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 地址
     */
    private String address;

}

