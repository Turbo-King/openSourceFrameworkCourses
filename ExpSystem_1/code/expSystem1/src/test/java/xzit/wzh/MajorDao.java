package xzit.wzh;

import com.xzit.wzh.dao.IMajorDao;
import com.xzit.wzh.dao.impl.MajorDaoImpl;
import com.xzit.wzh.domain.MajorInfo;
import org.junit.Before;
import org.junit.Test;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: MajorDao 测试类
 * \
 */
public class MajorDao {
    IMajorDao majorDao;

    @Before
    public void init() {
        majorDao = new MajorDaoImpl();
    }

    @Test
    public void selectByMajorId() {
        long id = 1;
        MajorInfo majorInfo = majorDao.selectByMajorId(id);
        System.out.println(majorInfo);
    }

    @Test
    public void selectByInnerCode() {
        String innerCode = "090102";
        MajorInfo majorInfo = majorDao.selectByInnerCode(innerCode);
        System.out.println(majorInfo);
    }

    @Test
    public void selectByMajorName() {
        String majorName = "计算机科学与技术";
        MajorInfo majorInfo = majorDao.selectByMajorName(majorName);
        System.out.println(majorInfo);
    }

    @Test
    public void insert() throws Exception {
        MajorInfo majorInfo = new MajorInfo();
        majorInfo.setInnerCode("090103");
        majorInfo.setMajorName("软件工程");
        majorInfo.setUderSchool("信息工程学院（大数据学院）");
        majorInfo.setGenCode("080902");
        int row = majorDao.insert(majorInfo);
        System.out.println("row = " + row);
    }


    @Test
    public void update() throws Exception {
        MajorInfo majorInfo = new MajorInfo();
        majorInfo.setMajorId(4L);
        majorInfo.setInnerCode("090105");
        majorInfo.setMajorName("软件工程");
        majorInfo.setUderSchool("信息工程学院（大数据学院）");
        majorInfo.setGenCode("080902");
        int row = majorDao.update(majorInfo);
        System.out.println("row = " + row);
    }

    @Test
    public void deleteByMajorId() throws Exception {
        long majorId = 4;
        int row = majorDao.deleteByMajorId(majorId);
        System.out.println("row = " + row);
    }


    @Test
    public void deleteByMajorIds() throws Exception {
        Long[] majorIds = {3L, 5L};
        int row = majorDao.deleteByMajorIds(majorIds);
        System.out.println("row = " + row);
    }
}
