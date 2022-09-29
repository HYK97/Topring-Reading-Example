package tobystudy.group.template.v1;

import org.springframework.dao.EmptyResultDataAccessException;
import tobystudy.group.template.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {


    //복잡한 try catch finally
    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        System.out.println(" 빈등록 ");
        this.connectionMaker = connectionMaker;
    }


    public void add(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionMaker.makeConnection();
            stmt = conn.prepareStatement("insert into users(id,name,password) values(?,?,?)");
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void deleteAll() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connectionMaker.makeConnection();
            stmt = conn.prepareStatement("delete from users");
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public int getCount() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            conn = connectionMaker.makeConnection();
            stmt = conn.prepareStatement("select count(*) from users");
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public User get(String id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            conn = connectionMaker.makeConnection();
            stmt = conn.prepareStatement("select * from users where id = ?");
            stmt.setString(1, id);
            resultSet = stmt.executeQuery();
            User user = null;
            if (resultSet.next()) {
                resultSet.getString(1);
                user = User.builder().id(resultSet.getString(1)).name(resultSet.getString(2)).password(resultSet.getString(3)).build();
            }
            if (user == null) {
                throw new EmptyResultDataAccessException("해당 유저가 존재하지않음", 0);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
