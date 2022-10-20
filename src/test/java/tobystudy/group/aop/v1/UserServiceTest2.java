package tobystudy.group.aop.v1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = TestConfig.class)
public class UserServiceTest2 {


    @Autowired
    DataSource dataSource;
    @Autowired
    private MailSender dummyMailSender;
    @Autowired
    private UserDao userDao;
    private List<User> users = new ArrayList<>();
    @Autowired
    private PlatformTransactionManager txManager;

    @BeforeEach
    public void setup() {
        users = List.of(new User("user1", "name1", "password1", Level.BASIC, UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER - 1, 0, "test1@naver.com"),
                new User("user2", "name2", "password2", Level.BASIC, UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER, 0, "test2@naver.com"),
                new User("user3", "name3", "password3", Level.SILVER, 60, UserServiceImpl.MIN_RECCOMMEND_FORGOLD - 1, "test3@naver.com"),
                new User("user4", "name4", "password4", Level.SILVER, 60, UserServiceImpl.MIN_RECCOMMEND_FORGOLD, "test4@naver.com"),
                new User("user5", "name5", "password5", Level.GOLD, 100, Integer.MAX_VALUE, "test5@naver.com"));
    }

    @AfterEach
    public void clear() {
        userDao.deleteAll();
    }

    @Test
    public void upgradeAllOrNothing() {
        //given
        UserService userService = new TestUserService(userDao, users.get(3).getId(), this.txManager, this.dummyMailSender);

        //when
        for (User user : users) {
            userService.add(user);
        }
        //then
        assertThatThrownBy(userService::upgradeLevels).isInstanceOf(IllegalArgumentException.class);
        List<User> all = userDao.getAll();
        for (User user : all) {
            System.out.println("user = " + user);
        }
        checkLevelUpgraded(users.get(1), false);


    }


    public void checkLevelUpgraded(User user, boolean result) {
        User updateUser = userDao.get(user.getId());
        if (result) {
            assertThat(updateUser.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(updateUser.getLevel()).isEqualTo(user.getLevel());
        }
    }

    static class TestUserService extends UserServiceImpl {

        private final String userId;

        public TestUserService(UserDao userDao, String userId, PlatformTransactionManager txManager, MailSender mailSender) {
            super(userDao, mailSender, txManager);
            this.userId = userId;
        }


        @Override
        protected void upgradeLevel(User user) {
            if (userId.equals(user.getId())) {
                throw new IllegalArgumentException("test");
            } else {
                super.upgradeLevel(user);
            }
        }

    }


}
