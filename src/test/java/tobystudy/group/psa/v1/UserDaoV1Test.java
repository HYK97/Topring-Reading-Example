package tobystudy.group.psa.v1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Spring di에 의존적인 테스트
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = DaoFactory.class)
class UserDaoV1Test {
    @Autowired
    private UserDao userDao;
    //Bean 타입이 같은게 2개 있기 때문에 이름으로 가져옴.

    private User user1;
    private User user2;
    private User user3;


    @BeforeEach
    public void setup() {
        user1 = new User("user1", "name1", "password1", Level.BASIC, 1, 0);
        user2 = new User("user2", "name2", "password2", Level.SILVER, 55, 10);
        user3 = new User("user3", "name3", "password3", Level.GOLD, 100, 40);
    }

    @AfterEach
    public void clear() {
        userDao.deleteAll();
    }

    @Test
    public void addTest() throws Exception {
        //given
        //when
        userDao.add(user1);
        User findUser = userDao.get(user1.getId());

        //then
        assertThat(findUser).isEqualTo(user1);
    }


    @Test
    public void countTest() throws SQLException, ClassNotFoundException {
        //given
        assertThat(userDao.getCount()).isEqualTo(0);
        //when
        //then
        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);
        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    public void getUserFail() throws Exception {
        //given
        //when
        //then
        assertThatThrownBy(() -> userDao.get("test")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void update() {
        //given
        userDao.add(user1);
        userDao.add(user2);
        //when
        user1.setName("testUpdateName");
        user1.setPassword("testPassword2");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        userDao.update(user1);
        User findUser = userDao.get(user1.getId());
        User findUser2 = userDao.get(user2.getId());
        //then
        assertThat(user1).isEqualTo(findUser);
        assertThat(user2).isEqualTo(findUser2);


    }


}