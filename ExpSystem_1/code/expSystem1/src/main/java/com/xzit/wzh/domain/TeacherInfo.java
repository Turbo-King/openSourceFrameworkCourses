package com.xzit.wzh.domain;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/15
 * \* TODO
 * \* Description: 教师信息
 * \
 */

@Data
public class TeacherInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 教师ID
     */
    private Long teacherId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private String dateBirth;

    /**
     * 学院
     */
    private String uderSchool;

    /**
     * 职称
     */
    private String title;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 地址
     */
    private String address;

}
