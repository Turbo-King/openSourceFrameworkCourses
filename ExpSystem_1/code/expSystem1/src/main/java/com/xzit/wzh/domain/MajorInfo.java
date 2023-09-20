package com.xzit.wzh.domain;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: 专业信息
 * \
 */
@Data
public class MajorInfo {
    /**
     * 专业ID
     */
    private Long majorId;

    /**
     * 校内专业代码
     */
    private String innerCode;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 所属学院
     */
    private String uderSchool;

    /**
     * 专业代号
     */
    private String genCode;

    /**
     * 专业简称
     */
    private String shortName;

    /**
     * 专业英文名称
     */
    private String majorEnName;

    /**
     * 学制
     */
    private String eduSystem;

    /**
     * 建立年月
     */
    private String setYm;

    /**
     * 培养层次
     */
    private String culLevel;

    /**
     * 启用状态
     */
    private String state;

    /**
     * 专业简介
     */
    private String memo;

    /**
     * 专业负责人
     */
    private String majorLeader;
}
