package tobystudy.group.testing;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobystudy.group.testing.v1.ConnectionMaker;
import tobystudy.group.testing.v1.CountingConnectionMaker;
import tobystudy.group.testing.v1.NConnectionMaker;
import tobystudy.group.testing.v1.UserDao;

@Configuration
public class TestConfiguration {
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
