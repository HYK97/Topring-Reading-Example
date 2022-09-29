package tobystudy.group.template.v4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    ConnectionMaker c;

    public JdbcContext(ConnectionMaker c) {
        this.c = c;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        PreparedStatement ps = null;
        Connection c1 = null;
        try {
            c1 = c.makeConnection();
            ps = stmt.makePreparedStatement(c1);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c1 != null) {
                try {
                    c1.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void executeUpdate(String sql) throws SQLException {
        workWithStatementStrategy((c) -> {
                    return c.prepareStatement(sql);
                }
        );
    }

}
