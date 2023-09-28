package com.xzit.wzh.service;

import com.xzit.wzh.domain.MajorInfo;
import com.xzit.wzh.web.Pager;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/28
 * \* TODO
 * \* Description: Major Service层
 * \
 */
public interface MajorInfoService {
    /**
     * 新增专业信息
     */
    public int insert(MajorInfo majorInfo) throws Exception;

    /**
     * 按专业名称实现分页查询
     **/
    public Pager<MajorInfo> findByPage(String likeMajorName, int pageNo, int pageSize);
}
