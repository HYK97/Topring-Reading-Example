package tobystudy.group.template.v3;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        System.out.println("빈리턴");
        return new UserDao(countingConnectionMaker());
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
