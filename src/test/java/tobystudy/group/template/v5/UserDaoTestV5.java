package tobystudy.group.template.v5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import tobystudy.group.template.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Spring di에 의존적인 테스트
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = DaoFactory.class)
class UserDaoTestV5 {
    @Autowired
    private UserDao userDao;


    private User user1;
    private User user2;
    private User user3;


    @BeforeEach
    public void setup() {
        user1 = new User("user1", "name1", "password1");
        user2 = new User("user2", "name2", "password2");
        user3 = new User("user3", "name3", "password3");
    }

    @AfterEach
    public void clear() {
        userDao.deleteAll();
    }

    @Test
    public void addTest() {
        //given
        //when
        userDao.add(user1);
        User findUser = userDao.get(user1.getId());

        //then
        assertThat(findUser).isEqualTo(user1);
    }


    @Test
    public void countTest() {
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
    public void getUserFail() {
        //given
        //when
        //then
        assertThatThrownBy(() -> userDao.get("test")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getAll() {


        List<User> all = userDao.getAll();
        assertThat(all.size()).isEqualTo(0);

        userDao.add(user1);
        all = userDao.getAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all).contains(user1);

        userDao.add(user2);
        all = userDao.getAll();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(user1, user2);

        userDao.add(user3);
        all = userDao.getAll();
        assertThat(all.size()).isEqualTo(3);
        assertThat(all).contains(user1, user2, user3);


    }


}