package com.xzit.wzh.dao;

import com.xzit.wzh.domain.TeacherInfo;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: TeacherDao
 * \
 */
public interface ITeacherDao {
    /**
     * 根据教师ID查询教师信息  唯一性
     */
    public TeacherInfo selectByTeacherId(Long teacherId);

    /**
     * 根据教师名称查询教师信息
     */
    public List<TeacherInfo> selectByTeacherName(String teacherName);

    /**
     * 添加教师信息
     */
    public int insert(TeacherInfo teacherInfo) throws Exception;

    /**
     * 修改教师信息
     */
    public int update(TeacherInfo teacherInfo) throws Exception;

    /**
     * 根据教师ID删除教师信息
     */
    public int deleteByTeacherId(Long teacherId);

    /**
     * 批量删除教师信息
     */
    public int deleteByTeacherIds(Long[] teacherIds);
}
