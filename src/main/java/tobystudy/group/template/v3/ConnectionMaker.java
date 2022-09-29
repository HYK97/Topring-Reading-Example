package tobystudy.group.template.v3;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    Connection makeConnection() throws SQLException, ClassNotFoundException;
}
