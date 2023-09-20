package com.xzit.wzh.dao.impl;

import com.xzit.wzh.dao.IMajorDao;
import com.xzit.wzh.domain.MajorInfo;
import com.xzit.wzh.utils.DBConnectionUtil;

import java.sql.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: MajorDao实现类
 * \
 */
public class StudentDaoImpl implements IMajorDao {
    @Override
    public MajorInfo selectByMajorId(Long majorId) {
        String sql = "select * from sys_majorinfo where major_id=?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        MajorInfo majorInfo;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, majorId);
            rs = ps.executeQuery();

            // 将结果转为对象
            majorInfo = DBConnectionUtil.toMajorObject(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBConnectionUtil.release(rs, ps, connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return majorInfo;
    }

    @Override
    public MajorInfo selectByInnerCode(String innerCode) {
        String sql = "select * from sys_majorinfo where inner_code=?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        MajorInfo majorInfo;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, innerCode);
            rs = ps.executeQuery();

            // 将结果转为对象
            majorInfo = DBConnectionUtil.toMajorObject(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBConnectionUtil.release(rs, ps, connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return majorInfo;
    }

    @Override
    public MajorInfo selectByMajorName(String majorName) {
        String sql = "select * from sys_majorinfo where major_name=?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        MajorInfo majorInfo;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, majorName);
            rs = ps.executeQuery();

            // 将结果转为对象
            majorInfo = DBConnectionUtil.toMajorObject(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBConnectionUtil.release(rs, ps, connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return majorInfo;
    }

    @Override
    public int insert(MajorInfo majorInfo) throws Exception {
        // 判断是否为空数据
        if (majorInfo == null) {
            return 0;
        }

        String sql = "insert into sys_majorinfo(" +
                "inner_code, " +
                "major_name, " +
                "uder_school, " +
                "gen_code, " +
                "short_name, " +
                "major_en_name, " +
                "edu_system, " +
                "set_ym, " +
                "cul_level, " +
                "state, " +
                "memo, " +
                "major_leader) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        int row = 0;
        PreparedStatement ps = null;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, majorInfo.getInnerCode());
            ps.setString(2, majorInfo.getMajorName());
            ps.setString(3, majorInfo.getUderSchool());
            ps.setString(4, majorInfo.getGenCode());

            /* 以下参数可能为NULL */
            if (majorInfo.getShortName() == null) {
                ps.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(5, majorInfo.getShortName());
            }

            if (majorInfo.getMajorEnName() == null) {
                ps.setNull(6, Types.VARCHAR);
            } else {
                ps.setString(6, majorInfo.getMajorEnName());
            }

            if (majorInfo.getEduSystem() == null) {
                ps.setNull(7, Types.VARCHAR);
            } else {
                ps.setString(7, majorInfo.getEduSystem());
            }

            if (majorInfo.getSetYm() == null) {
                ps.setNull(8, Types.VARCHAR);
            } else {
                ps.setString(8, majorInfo.getSetYm());
            }

            if (majorInfo.getCulLevel() == null) {
                ps.setNull(9, Types.VARCHAR);
            } else {
                ps.setString(9, majorInfo.getCulLevel());
            }

            if (majorInfo.getState() == null) {
                ps.setNull(10, Types.CHAR);
            } else {
                ps.setString(10, majorInfo.getState());
            }

            if (majorInfo.getMemo() == null) {
                ps.setNull(11, Types.VARCHAR);
            } else {
                ps.setString(11, majorInfo.getMemo());
            }

            if (majorInfo.getMajorLeader() == null) {
                ps.setNull(12, Types.VARCHAR);
            } else {
                ps.setString(12, majorInfo.getMajorLeader());
            }


            row = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBConnectionUtil.release(null, ps, connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return row;
    }

    @Override
    public int update(MajorInfo majorInfo) throws Exception {
        String sql = "update sys_majorinfo " +
                "set inner_code=?," +
                "major_name=?, " +
                "uder_school=?, " +
                "gen_code=?, " +
                "short_name=?, " +
                "major_en_name=?, " +
                "edu_system=?, " +
                "set_ym=?, " +
                "cul_level=?, " +
                "state=?, " +
                "memo=?, " +
                "major_leader=? " +
                "where major_id=?";
        Connection connection = null;
        int row = 0;
        PreparedStatement ps = null;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, majorInfo.getInnerCode());
            ps.setString(2, majorInfo.getMajorName());
            ps.setString(3, majorInfo.getUderSchool());
            ps.setString(4, majorInfo.getGenCode());
            ps.setString(5, majorInfo.getShortName());
            ps.setString(6, majorInfo.getMajorEnName());
            ps.setString(7, majorInfo.getEduSystem());
            ps.setString(8, majorInfo.getSetYm());
            ps.setString(9, majorInfo.getCulLevel());
            ps.setString(10, majorInfo.getState());
            ps.setString(11, majorInfo.getMemo());
            ps.setString(12, majorInfo.getMajorLeader());
            ps.setLong(13, majorInfo.getMajorId());
            row = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBConnectionUtil.release(null, ps, connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return row;
    }

    @Override
    public int deleteByMajorId(Long majorId) {
        String sql = "delete from sys_majorinfo where major_id=?";
        Connection connection = null;
        int row = 0;
        PreparedStatement ps = null;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, majorId);
            row = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBConnectionUtil.release(null, ps, connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return row;
    }

    @Override
    public int deleteByMajorIds(Long[] majorIds) {
        int row = 0;
        for (Long majorId : majorIds) {
            row += deleteByMajorId(majorId);
        }
        return row;
    }
}
