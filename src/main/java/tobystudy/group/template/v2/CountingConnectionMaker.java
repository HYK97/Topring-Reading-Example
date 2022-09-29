package tobystudy.group.template.v2;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    private final ConnectionMaker connectionMaker;
    private long count;

    public CountingConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
        count = 0;
    }

    @Override
    public Connection makeConnection() throws SQLException, ClassNotFoundException {
        count++;
        return connectionMaker.makeConnection();
    }

    public long getCount() {
        return count;
    }
}
