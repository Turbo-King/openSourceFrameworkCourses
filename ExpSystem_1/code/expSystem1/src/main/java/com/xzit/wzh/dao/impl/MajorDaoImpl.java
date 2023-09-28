package com.xzit.wzh.dao.impl;

import com.xzit.wzh.dao.IMajorDao;
import com.xzit.wzh.domain.MajorInfo;
import com.xzit.wzh.utils.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description: MajorDao实现类
 * \
 */
public class MajorDaoImpl implements IMajorDao {
    @Override
    public List<MajorInfo> selectAll() {
        List<MajorInfo> majorInfos = new ArrayList<>();
        String sql = "select * from sys_majorinfo ";
        try {
            //2.创建连接对象
            Connection conn = DBConnectionUtil.getConnection();
            //3.创建语句对象
            PreparedStatement psmt = conn.prepareStatement(sql);
            //4.执行SQL命令，获得ResultSet对象
            ResultSet rs = psmt.executeQuery();
            //5.解析结果
            while (rs != null && rs.next()) {
                // 将结果转为对象
                majorInfos.add(DBConnectionUtil.toMajorObject(rs));
            }
            //6.释放资源
            DBConnectionUtil.release(rs, psmt, conn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return majorInfos;
    }

    @Override
    public List<MajorInfo> limit(String likeMajorName, int curPage, int pageSize) {
        List<MajorInfo> majorInfos = new ArrayList<>();
        String sql = "select * from sys_majorinfo where major_name like ? limit ?,?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            if (likeMajorName != null && !likeMajorName.equals("")) {
                ps.setString(1, "%" + likeMajorName + "%");
            } else {
                ps.setString(1, "%");
            }
            ps.setInt(2, ((curPage - 1) * pageSize));
            ps.setInt(3, pageSize);
            rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                // 将结果转为对象
                majorInfos.add(DBConnectionUtil.toMajorObject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(rs, ps, connection);
        }
        return majorInfos;
    }

    @Override
    public int count(String likeMajorName) {
        StringBuffer query = new StringBuffer("");
        query.append(" select count(*) cnt from sys_majorinfo");
        if (likeMajorName != null && !likeMajorName.equals("")) {
            query.append(" where major_name like '%").append(likeMajorName).append("%'");
        }
        int count = 0;
        try {
            //2.创建连接对象
            Connection conn = DBConnectionUtil.getConnection();
            //3.创建语句对象
            PreparedStatement psmt = conn.prepareStatement(query.toString());
            //4.执行SQL命令，获得ResultSet对象
            ResultSet rs = psmt.executeQuery();
            //5.解析结果
            if (rs != null && rs.next()) {
                count = rs.getInt(1);
            }
            //6.释放资源
            DBConnectionUtil.release(rs, psmt, conn);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return count;
    }

    @Override
    public MajorInfo selectByMajorId(Long majorId) {
        String sql = "select * from sys_majorinfo where major_id=?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        MajorInfo majorInfo = null;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, majorId);
            rs = ps.executeQuery();

            if (rs != null) {
                // 将结果转为对象
                majorInfo = DBConnectionUtil.toMajorObject(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(rs, ps, connection);
        }

        return majorInfo;
    }

    @Override
    public MajorInfo selectByInnerCode(String innerCode) {
        String sql = "select * from sys_majorinfo where inner_code=?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        MajorInfo majorInfo = null;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, innerCode);
            rs = ps.executeQuery();

            if (rs != null) {
                // 将结果转为对象
                majorInfo = DBConnectionUtil.toMajorObject(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(rs, ps, connection);
        }
        return majorInfo;
    }

    @Override
    public MajorInfo selectByMajorName(String majorName) {
        String sql = "select * from sys_majorinfo where major_name=?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        MajorInfo majorInfo = null;
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, majorName);
            rs = ps.executeQuery();

            if (rs != null) {
                // 将结果转为对象
                majorInfo = DBConnectionUtil.toMajorObject(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(rs, ps, connection);
        }
        return majorInfo;
    }

    @Override
    public int insert(MajorInfo majorInfo) throws Exception {
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

        // 不允许空对象，直接返回
        if (majorInfo == null) {
            return row;
        }
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
            // 释放资源
            DBConnectionUtil.release(null, ps, connection);
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
        // 不允许空对象，直接返回
        if (majorInfo == null) {
            return row;
        }
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
            // 释放资源
            DBConnectionUtil.release(null, ps, connection);
        }
        return row;
    }

    @Override
    public int deleteByMajorId(Long majorId) {
        String sql = "delete from sys_majorinfo where major_id=?";
        Connection connection = null;
        int row = 0;
        PreparedStatement ps = null;
        // 判断传入id是否为null
        if (majorId == null) {
            return row;
        }
        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, majorId);
            row = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            DBConnectionUtil.release(null, ps, connection);
        }
        return row;
    }

    @Override
    public int deleteByMajorIds(Long[] majorIds) {
        int row = 0;
        // 判断无数据直接返回
        if (majorIds.length == 0) {
            return row;
        }
        for (Long majorId : majorIds) {
            row += deleteByMajorId(majorId);
        }
        return row;
    }
}
