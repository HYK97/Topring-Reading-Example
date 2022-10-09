package tobystudy.group.psa.v2;

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
        userService.upgradeLevel();
        //when
        //then
        assertThat(userDao.get(users.get(0).getId()).getLevel()).isEqualTo(Level.BASIC);
        assertThat(userDao.get(users.get(1).getId()).getLevel()).isEqualTo(Level.SILVER);
        assertThat(userDao.get(users.get(2).getId()).getLevel()).isEqualTo(Level.SILVER);
        assertThat(userDao.get(users.get(3).getId()).getLevel()).isEqualTo(Level.GOLD);
        assertThat(userDao.get(users.get(4).getId()).getLevel()).isEqualTo(Level.GOLD);
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