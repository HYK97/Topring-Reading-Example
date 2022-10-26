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
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        System.out.println("빈리턴");
        return new UserDaoJdbc(dataSource());
    }

    @Bean
    public UserService userServiceImpl() {
        return new UserServiceImpl(userDao(), mailSender());
    }

    @Bean
    public UserService userService() {
        ProxyFactoryBean pf = new ProxyFactoryBean();
        pf.setTarget(userServiceImpl());
        pf.setInterceptorNames("transactionAdvisor");
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
    public MailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("naver.com");
        return sender;
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
