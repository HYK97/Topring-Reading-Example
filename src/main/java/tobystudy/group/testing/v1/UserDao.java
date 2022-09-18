package tobystudy.group.testing.v1;

import org.springframework.dao.EmptyResultDataAccessException;
import tobystudy.group.testing.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {


    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        System.out.println(" 빈등록 ");
        this.connectionMaker = connectionMaker;
    }

    /*반복되는 Class ~ getConnection 을 메서드로 분리해보자 */
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement stmt = conn.prepareStatement("insert into users(id,name,password) values(?,?,?)");
        try (conn; stmt;) {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
        }
    }

    /*반복되는 Class ~ getConnection 을 메서드로 분리해보자 */
    public void deleteAll() throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement stmt = conn.prepareStatement("delete from users");
        try (conn; stmt;) {
            stmt.executeUpdate();
        }
    }

    public int getCount() throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement stmt = conn.prepareStatement("select count(*) from users");
        ResultSet resultSet = stmt.executeQuery();
        try (conn; stmt; resultSet;) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        }
    }


    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from users where id = ?");
        stmt.setString(1, id);
        ResultSet resultSet = stmt.executeQuery();
        try (conn; stmt; resultSet;) {
            User user = null;
            if (resultSet.next()) {
                resultSet.getString(1);
                user = User.builder().id(resultSet.getString(1)).name(resultSet.getString(2)).password(resultSet.getString(3)).build();
            }
            if (user == null) {
                throw new EmptyResultDataAccessException("해당 유저가 존재하지않음", 0);
            }
            return user;
        }
    }


}
