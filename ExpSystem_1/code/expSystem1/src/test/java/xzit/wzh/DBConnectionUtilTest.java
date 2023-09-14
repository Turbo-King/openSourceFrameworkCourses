package xzit.wzh;

import com.xzit.wzh.utils.DBConnectionUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/14
 * \* TODO
 * \* Description:
 * \
 */
public class DBConnectionUtilTest {
    @Test
    public void DBConnectionUtil() {
        Connection connection = DBConnectionUtil.getConnection();
        Assert.assertNotNull(connection);
        if (connection == null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
