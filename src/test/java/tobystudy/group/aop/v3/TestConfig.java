package tobystudy.group.aop.v3;


import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import tobystudy.group.psa.v6.DummyMailSender;

import javax.sql.DataSource;

@Configuration
public class TestConfig {
    @Bean
    public UserDao userDao() {
        System.out.println("빈리턴");
        return new UserDaoJdbc(dataSource());
    }

    @Bean
    public UserService userServiceImpl() {
        return new UserServiceImpl(userDao(), mockMailSender());
    }

    @Bean
    public UserService userService() {
        ProxyFactoryBean pf = new ProxyFactoryBean();
        pf.setTarget(userServiceImpl());
        pf.addAdvisor(transactionAdvisor());
        return (UserService) pf.getObject();
    }


    @Bean
    public Pointcut transactionPointcut() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("upgrade*");
        return pointcut;
    }

    @Bean
    public TransactionAdvice transactionAdvice() {
        return new TransactionAdvice(transactionManager());
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
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
