package tobystudy.group.psa.v3;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        System.out.println("빈리턴");
        return new UserDaoJdbc(dataSource());
    }

    @Bean
    public UserService userService() {
        return new UserService(userDao());
    }

    @Bean
    public DataSource dataSource() {
        final String URL = "jdbc:h2:tcp://localhost/~/toby";
        final String USERNAME = "sa";
        final String PASSWORD = "1234";
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }

}
