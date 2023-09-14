package com.xzit.wzh.dao;

import com.xzit.wzh.domain.MajorInfo;

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
