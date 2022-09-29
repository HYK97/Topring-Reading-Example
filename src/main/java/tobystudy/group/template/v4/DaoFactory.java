package tobystudy.group.template.v4;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() throws SQLException, ClassNotFoundException {
        System.out.println("빈리턴");
        return new UserDao(jdbcContext(), connectionMaker());
    }

    @Bean
    public JdbcContext jdbcContext() throws SQLException, ClassNotFoundException {
        return new JdbcContext(connectionMaker());
    }

    @Bean
    public ConnectionMaker countingConnectionMaker() {
        return new CountingConnectionMaker(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }
}
