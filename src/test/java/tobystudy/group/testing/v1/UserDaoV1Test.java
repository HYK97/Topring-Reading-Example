package tobystudy.group.testing.v1;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import tobystudy.group.testing.User;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class UserDaoV1Test {

    private ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
    private UserDao userDao = ac.getBean("userDao", UserDao.class);
    private CountingConnectionMaker ccm = ac.getBean("countingConnectionMaker", CountingConnectionMaker.class);

    @Test
    public void addTest() throws Exception {
        //given
        userDao.deleteAll();
        User addUser = User.builder().id("TestId").name("TestName").password("TestPassword").build();
        //when
        userDao.add(addUser);
        User findUser = userDao.get(addUser.getId());

        //then
        assertThat(findUser).isEqualTo(addUser);
    }

    @Test
    public void countingTest() throws Exception {
        //given
        userDao.deleteAll();
        User addUser = User.builder().id("TestId").name("TestName").password("TestPassword").build();
        userDao.add(addUser);
        User findUser = userDao.get(addUser.getId());
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
        userDao.add(new User("user1", "name", "password"));
        assertThat(userDao.getCount()).isEqualTo(1);
        userDao.add(new User("user2", "name", "password"));
        assertThat(userDao.getCount()).isEqualTo(2);
        userDao.add(new User("user3", "name", "password"));
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