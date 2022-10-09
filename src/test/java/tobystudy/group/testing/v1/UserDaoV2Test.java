package tobystudy.group.testing.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import tobystudy.group.exception.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class UserDaoV2Test {


    private UserDao userDao;
    private CountingConnectionMaker ccm;

    private User user1;
    private User user2;
    private User user3;


    @BeforeEach
    public void setup() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.ccm = ac.getBean("countingConnectionMaker", CountingConnectionMaker.class);
        this.userDao = ac.getBean("userDao", UserDao.class);
        user1 = new User("user1", "name1", "password1");
        user2 = new User("user2", "name2", "password2");
        user3 = new User("user3", "name3", "password3");
    }

    @Test
    public void addTest() throws Exception {
        //given
        userDao.deleteAll();
        //when
        userDao.add(user1);
        User findUser = userDao.get(user1.getId());

        //then
        assertThat(findUser).isEqualTo(user1);
    }

    @Test
    public void countingTest() throws Exception {
        //given
        userDao.deleteAll();
        userDao.add(user1);
        User findUser = userDao.get(user1.getId());
        //when
        //then
        assertThat(ccm.getCount()).isEqualTo(3L);
    }

    @Test
    public void countTest() throws SQLException, ClassNotFoundException {
        //given
        userDao.deleteAll();
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
        userDao.deleteAll();
        //when
        //then
        assertThatThrownBy(() -> userDao.get("test")).isInstanceOf(EmptyResultDataAccessException.class);
    }

}