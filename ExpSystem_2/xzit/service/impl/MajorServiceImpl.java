package com.xzit.service.impl;

import com.xzit.dao.MajorDao;
import com.xzit.service.MajorService;

public class MajorServiceImpl implements MajorService {

    private MajorDao majorDao;

    @Override
    public int add(String majorName) {
        return majorDao.add(majorName);
    }

    public void setMajorDao(MajorDao majorDao) {
        this.majorDao = majorDao;
    }
}
