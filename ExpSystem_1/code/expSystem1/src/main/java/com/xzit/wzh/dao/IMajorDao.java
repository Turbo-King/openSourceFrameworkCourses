package com.xzit.wzh.dao;

import com.xzit.wzh.domain.MajorInfo;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: MajorDao
 * \
 */
public interface IMajorDao {

    /**
     * 查询所有专业信息
     *
     * @return 专业信息列表
     */
    public List<MajorInfo> selectAll();

    /**
     * @param likeMajorName 模糊专业名
     * @param curPage       当前页号
     * @param pageSize      页面显示数据
     * @return 专业信息列表
     */
    public List<MajorInfo> limit(String likeMajorName, int curPage, int pageSize);

    /**
     * 按专业名称模糊查询结果有多少条记录
     *
     * @param likeMajorName 模糊专业名
     * @return 专业数量
     */
    public int count(String likeMajorName);

    /**
     * 根据专业ID查询专业
     * 信息  唯一性
     */
    public MajorInfo selectByMajorId(Long majorId);

    /**
     * 根据校内专业代码查询专业信息   唯一性
     */
    public MajorInfo selectByInnerCode(String innerCode);

    /**
     * 根据专业名称查询专业信息   唯一性
     */
    public MajorInfo selectByMajorName(String majorName);

    /**
     * 添加专业信息
     */
    public int insert(MajorInfo majorInfo) throws Exception;

    /**
     * 修改专业信息
     */
    public int update(MajorInfo majorInfo) throws Exception;

    /**
     * 根据专业ID删除专业信息
     */
    public int deleteByMajorId(Long majorId);

    /**
     * 批量删除专业信息
     */
    public int deleteByMajorIds(Long[] majorIds);
}
