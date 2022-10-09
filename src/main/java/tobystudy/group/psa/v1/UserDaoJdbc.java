package tobystudy.group.psa.v1;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJdbc implements UserDao {


    //복잡한 try catch finally
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper = (rs, row) -> new User(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("password"),
            Level.valueOf(rs.getInt("level")),
            rs.getInt("login"),
            rs.getInt("recommend"));

    public UserDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(final User user) {
        this.jdbcTemplate.update("insert into users(id,name,password,level,login,recommend) values(?,?,?,?,?,?)", user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    @Override
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id =?", this.userRowMapper, id);
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userRowMapper);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update("update users set name=?,password=?,level=?,login=?,recommend=? where id=?",
                user.getName(),
                user.getPassword(),
                user.getLevel().intValue(),
                user.getLogin(),
                user.getRecommend(),
                user.getId());
    }


}
