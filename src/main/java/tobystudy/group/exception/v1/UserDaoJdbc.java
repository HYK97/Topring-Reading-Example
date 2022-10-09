package tobystudy.group.exception.v1;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import tobystudy.group.template.User;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJdbc implements UserDao {


    //복잡한 try catch finally
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper = (rs, row) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

    public UserDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(final User user) {
        this.jdbcTemplate.update("insert into users(id,name,password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
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
        return this.jdbcTemplate.queryForObject("select * from users where id =?"
                , this.userRowMapper
                , id);
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userRowMapper);
    }


}
