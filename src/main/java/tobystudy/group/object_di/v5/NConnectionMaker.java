package tobystudy.group.object_di.v5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker {

    private final String URL = "jdbc:h2:tcp://localhost/~/test";
    private final String USERNAME = "sa";
    private final String PASSWORD = "";

    @Override
    public Connection makeConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
    }
}
