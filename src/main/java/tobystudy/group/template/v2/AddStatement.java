package tobystudy.group.template.v2;

import tobystudy.group.template.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy {

    private final User user;

    public AddStatement(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {

        PreparedStatement stmt = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
        stmt.setString(1, user.getId());
        stmt.setString(2, user.getName());
        stmt.setString(3, user.getPassword());
        return stmt;

    }
}
