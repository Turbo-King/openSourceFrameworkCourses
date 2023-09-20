package com.xzit.wzh.domain;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/15
 * \* TODO
 * \* Description: 课程信息
 * \
 */

@Data
public class CourseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程代码
     */
    private String courseCode;

    /**
     * 授课教师ID
     */
    private Long teacherId;

    /**
     * 学院
     */
    private String underSchool;

    /**
     * 学分
     */
    private String credit;

    /**
     * 开课学期
     */
    private String semester;

    /**
     * 上课时间
     */
    private String classTime;

    /**
     * 上课地点
     */
    private String classLocation;

    /**
     * 课程简介
     */
    private String courseDescription;

}
