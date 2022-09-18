package tobystudy.group.testing.v1;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    Connection makeConnection() throws SQLException, ClassNotFoundException;
}
