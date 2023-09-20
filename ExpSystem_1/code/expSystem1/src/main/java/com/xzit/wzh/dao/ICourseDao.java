package com.xzit.wzh.dao;

import com.xzit.wzh.domain.CourseInfo;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: CourseDao
 * \
 */
public interface ICourseDao {
    /**
     * 根据课程ID查询课程信息  唯一性
     */
    public CourseInfo selectByCourseId(Long courseId);

    /**
     * 根据校内专业代码查询课程信息   唯一性
     */
    public CourseInfo selectByCourseCode(String courseCode);

    /**
     * 根据课程名称查询课程信息   唯一性
     */
    public CourseInfo selectByCourseName(String courseName);

    /**
     * 添加课程信息
     */
    public int insert(CourseInfo courseInfo) throws Exception;

    /**
     * 修改课程信息
     */
    public int update(CourseInfo courseInfo) throws Exception;

    /**
     * 根据课程ID删除课程信息
     */
    public int deleteByCourseId(Long CourseId);

    /**
     * 批量删除课程信息
     */
    public int deleteByCourseIds(Long[] CourseIds);
}
