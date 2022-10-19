package tobystudy.group.psa.v4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobystudy.group.psa.v4.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static tobystudy.group.psa.v4.UserService.MIN_RECCOMMEND_FORGOLD;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = DaoFactory.class)
public class UserServiceTest2 {

    @Autowired
    DataSource dataSource;
    @Autowired
    private UserDao userDao;
    private List<User> users = new ArrayList<>();

    @BeforeEach
    public void setup() {
        users = List.of(new User("user1", "name1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0),
                new User("user2", "name2", "password2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("user3", "name3", "password3", Level.SILVER, 60, MIN_RECCOMMEND_FORGOLD - 1),
                new User("user4", "name4", "password4", Level.SILVER, 60, MIN_RECCOMMEND_FORGOLD),
                new User("user5", "name5", "password5", Level.GOLD, 100, Integer.MAX_VALUE));
    }

    @AfterEach
    public void clear() {
        userDao.deleteAll();
    }

    @Test
    public void upgradeAllOrNothing() {
        //given
        UserService userService = new TestUserService(userDao, users.get(3).getId());
        userService.setDataSource(dataSource);
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

    static class TestUserService extends UserService {

        private final String userId;

        public TestUserService(UserDao userDao, String userId) {
            super(userDao);
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
