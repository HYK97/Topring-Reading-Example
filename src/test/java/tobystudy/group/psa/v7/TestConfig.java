package tobystudy.group.psa.v7;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import tobystudy.group.psa.v6.DummyMailSender;
import tobystudy.group.psa.v6.UserDao;
import tobystudy.group.psa.v6.UserDaoJdbc;
import tobystudy.group.psa.v6.UserService;

import javax.sql.DataSource;

@Configuration
public class TestConfig {
    @Bean
    public UserDao userDao() {
        System.out.println("빈리턴");
        return new UserDaoJdbc(dataSource());
    }

    @Bean
    public UserService userService() {
        return new UserService(userDao(), mockMailSender(), transactionManager());
    }


    @Bean
    public MockMailSender mockMailSender() {
        return new MockMailSender();
    }

    @Bean
    public MailSender dummyMailSender() {
        return new DummyMailSender();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
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
