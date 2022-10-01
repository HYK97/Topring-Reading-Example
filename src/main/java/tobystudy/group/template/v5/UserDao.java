package tobystudy.group.template.v5;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import tobystudy.group.template.User;

import javax.sql.DataSource;
import java.util.List;

public class UserDao {


    //복잡한 try catch finally
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper = (rs, row) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        this.jdbcTemplate.update("insert into users(id,name,password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id =?"
                , this.userRowMapper
                , id);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userRowMapper);
    }


}
