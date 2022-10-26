package tobystudy.group.aop.v1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static tobystudy.group.aop.v1.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static tobystudy.group.aop.v1.UserServiceImpl.MIN_RECCOMMEND_FORGOLD;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = TestConfig.class)
class MockDaoUserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    private MockMailSender mailSender;

    @Autowired
    private UserDao userDao;
    private List<User> users;


    @BeforeEach
    public void setup() {
        users = List.of(
                new User("user1", "name1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "test1@naver.com")
                , new User("user2", "name2", "password2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "test2@naver.com")
                , new User("user3", "name3", "password3", Level.SILVER, 60, MIN_RECCOMMEND_FORGOLD - 1, "test3@naver.com")
                , new User("user4", "name4", "password4", Level.SILVER, 60, MIN_RECCOMMEND_FORGOLD, "test4@naver.com")
                , new User("user5", "name5", "password5", Level.GOLD, 100, Integer.MAX_VALUE, "test5@naver.com"));
    }

    @AfterEach
    public void clear() {
        userDao.deleteAll();
        mailSender.clearRequest();
    }

    @Test
    public void upgradeLevel() throws SQLException {

        MockDao mockDao = new MockDao(users);
        UserServiceImpl userServiceTest = new UserServiceImpl();
        userServiceTest.setUserDao(mockDao);
        userServiceTest.setMailSender(mailSender);
        //given
        userServiceTest.upgradeLevels();
        //when
        //then
        List<User> updatedUser = mockDao.getUpdatedUser();
        assertThat(updatedUser.size()).isEqualTo(2);
        assertThat(updatedUser.get(0)).extracting("id", "level").contains("user2", Level.SILVER);
        assertThat(updatedUser.get(1)).extracting("id", "level").contains("user4", Level.GOLD);

        List<String> request = mailSender.getRequest();
        assertThat(request.size()).isEqualTo(2);
        assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());

    }


    @Test
    public void add() {
        //given
        User levelGold = users.get(4);
        User levelNull = users.get(0);
        levelNull.setLevel(null);
        //when
        userService.add(levelGold);
        userService.add(levelNull);
        //then
        assertThat(userDao.get(levelGold.getId()).getLevel()).isEqualTo(Level.GOLD);
        assertThat(userDao.get(levelNull.getId()).getLevel()).isEqualTo(Level.BASIC);

    }

    static class MockDao implements UserDao {

        private final List<User> updateUser;
        private final List<User> updatedUser;

        public MockDao(List<User> updateUser) {
            this.updatedUser = new ArrayList<>();
            this.updateUser = updateUser;
        }

        public List<User> getUpdateUser() {
            return updateUser;
        }

        public List<User> getUpdatedUser() {
            return updatedUser;
        }

        @Override
        public void add(User user) {

            throw new UnsupportedOperationException("지원하지않는 기능");
        }

        @Override
        public void deleteAll() {

            throw new UnsupportedOperationException("지원하지않는 기능");
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException("지원하지않는 기능");
        }

        @Override
        public User get(String id) {
            throw new UnsupportedOperationException("지원하지않는 기능");
        }

        @Override
        public List<User> getAll() {
            return this.updateUser;
        }

        @Override
        public void update(User user) {
            updatedUser.add(user);
        }
    }

}