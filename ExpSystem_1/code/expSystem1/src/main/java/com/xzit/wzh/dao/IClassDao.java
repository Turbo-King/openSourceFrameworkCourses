package com.xzit.wzh.dao;

import com.xzit.wzh.domain.ClassInfo;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: ClassDao
 * \
 */
public interface IClassDao {
    /**
     * 查询所有班级信息
     *
     * @return 班级信息列表
     */
    public List<ClassInfo> selectAll();

    /**
     * @param likeClassName 模糊班级名
     * @param curPage       当前页号
     * @param pageSize      页面显示数据
     * @return 班级信息列表
     */
    public List<ClassInfo> limit(String likeClassName, int curPage, int pageSize);

    /**
     * 根据班级ID查询班级信息  唯一性
     */
    public ClassInfo selectByClassId(Long classId);

    /**
     * 根据专业代码查询班级信息
     */
    public List<ClassInfo> selectByMajorId(Long majorId);

    /**
     * 根据班级名称查询班级信息
     */
    public List<ClassInfo> selectByClassName(String className);

    /**
     * 添加班级信息
     */
    public int insert(ClassInfo classInfo) throws Exception;

    /**
     * 修改班级信息
     */
    public int update(ClassInfo classInfo) throws Exception;

    /**
     * 根据班级ID删除班级信息
     */
    public int deleteByClassId(Long classId);

    /**
     * 批量删除班级信息
     */
    public int deleteByClassIds(Long[] classIds);
}
