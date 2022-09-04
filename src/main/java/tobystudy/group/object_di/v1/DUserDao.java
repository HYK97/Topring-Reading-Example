package tobystudy.group.object_di.v1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDao extends UserDao{
    private final String URL = "jdbc:h2:tcp://localhost/~/test";
    private final String USERNAME = "sa";
    private final String PASSWORD = "";
    @Override
    protected Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
    }
}
