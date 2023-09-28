package com.xzit.wzh.service.impl;

import com.xzit.wzh.dao.IMajorDao;
import com.xzit.wzh.dao.impl.MajorDaoImpl;
import com.xzit.wzh.domain.MajorInfo;
import com.xzit.wzh.service.MajorInfoService;
import com.xzit.wzh.web.Pager;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/28
 * \* TODO
 * \* Description: Major Service 实现层
 * \
 */
public class MajorInfoServiceImpl implements MajorInfoService {
    //调用数据访问层的对象完成相关的数据操作
    //思考1：紧耦合 --->处理  (如Spring)
    private final IMajorDao majorDao = new MajorDaoImpl();

    @Override
    public int insert(MajorInfo majorInfo) throws Exception {
        //判断待新增的专业信息中的校内代码是否存在
        MajorInfo majorInfoTmp = majorDao.selectByInnerCode(majorInfo.getInnerCode());
        if (majorInfoTmp != null) throw new Exception(majorInfo.getInnerCode()
                + "专业内部代号已存在");
        //判断专业名称是否存在
        MajorInfo majorInfo2 = majorDao.selectByMajorName(majorInfo.getMajorName());
        if (majorInfo2 != null) throw new Exception(majorInfo.getMajorName()
                + "专业名称已存在");
        //保存
        return majorDao.insert(majorInfo);
    }

    @Override
    public Pager<MajorInfo> findByPage(String likeMajorName, int pageNo, int pageSize) {
        //获取记录数
        int count = majorDao.count(likeMajorName);
        //获取当前页数据
        List<MajorInfo> data = majorDao.limit(likeMajorName, pageNo, pageSize);
        //计算总页数--方法1
        int totalPages = (int) Math.ceil(1.0 * count / pageSize);
        //封装为Pager对象并返回
        Pager<MajorInfo> pager = new Pager<>(pageNo, pageSize, count, totalPages, data);
        return pager;
    }
}
