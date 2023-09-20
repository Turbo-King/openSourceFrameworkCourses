package com.xzit.wzh.domain;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/15
 * \* TODO
 * \* Description: 班级信息
 * \
 */

@Data
public class ClassInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 专业ID
     */
    private Long majorId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学年
     */
    private String academicYear;

    /**
     * 学期
     */
    private String semester;

    /**
     * 辅导员
     */
    private String classAdvisor;

    /**
     * 班级人数
     */
    private Long numberStudents;

    /**
     * 启用状态
     */
    private String status;

    /**
     * 创建日期
     */
    private String createDate;

    /**
     * 班级简介
     */
    private String classDescription;

}
