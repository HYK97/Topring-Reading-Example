package tobystudy.group.psa.v3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = DaoFactory.class)
class UserServiceTest {
    @Autowired
    UserService userService;


    @Autowired
    private UserDao userDao;
    private List<User> users;


    @BeforeEach
    public void setup() {
        users = List.of(new User("user1", "name1", "password1", Level.BASIC, 49, 0),
                new User("user2", "name2", "password2", Level.BASIC, 50, 0),
                new User("user3", "name3", "password3", Level.SILVER, 60, 29),
                new User("user4", "name4", "password4", Level.SILVER, 60, 30),
                new User("user5", "name5", "password5", Level.GOLD, 100, 100));
    }

    @AfterEach
    public void clear() {
        userDao.deleteAll();
    }

    @Test
    public void upgradeLevel() {
        //given
        for (User user : users) {
            userDao.add(user);
        }
        userService.upgradeLevels();
        //when
        //then
        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    private void checkLevelUpgraded(User user, boolean result) {
        User updateUser = userDao.get(user.getId());
        if (result) {
            assertThat(updateUser.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(updateUser.getLevel()).isEqualTo(user.getLevel());
        }
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

}