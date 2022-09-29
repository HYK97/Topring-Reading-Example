package tobystudy.group.template.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import tobystudy.group.template.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Spring di에 의존적인 테스트
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = TestConfiguration.class)
class UserDaoTestV1 {
    @Autowired
    private UserDao userDao;
    //Bean 타입이 같은게 2개 있기 때문에 이름으로 가져옴.
    @Autowired
    private CountingConnectionMaker countingConnectionMaker;

    @Autowired
    private ConnectionMaker connectionMaker;

    private User user1;
    private User user2;
    private User user3;


    @BeforeEach
    public void setup() {
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
    public void ccmNotNullAndDuplicationObjectTest() {
        //given

        //when
        //then
        assertThat(connectionMaker).isNotNull();
        assertThat(countingConnectionMaker).isNotNull();
        assertThat(countingConnectionMaker).isNotEqualTo(connectionMaker);

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