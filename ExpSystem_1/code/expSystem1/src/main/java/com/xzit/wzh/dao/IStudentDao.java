package com.xzit.wzh.dao;

import com.xzit.wzh.domain.StudentInfo;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: StudentDao
 * \
 */
public interface IStudentDao {
    /**
     * 根据学生ID查询学生信息  唯一性
     */
    public StudentInfo selectByStudentId(Long studentId);

    /**
     * 根据班级id查询学生信息
     */
    public List<StudentInfo> selectByClassId(String classId);

    /**
     * 根据专业名称查询专业信息
     */
    public List<StudentInfo> selectByStudentName(String studentName);

    /**
     * 添加学生信息
     */
    public int insert(StudentInfo studentInfo) throws Exception;

    /**
     * 修改学生信息
     */
    public int update(StudentInfo studentInfo) throws Exception;

    /**
     * 根据学生ID删除学生信息
     */
    public int deleteByStudentId(Long studentId);

    /**
     * 批量删除学生信息
     */
    public int deleteByStudentIds(Long[] studentIds);
}
