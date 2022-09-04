package tobystudy.group.object_di;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobystudy.group.object_di.v5.CountingConnectionMaker;
import tobystudy.group.object_di.v5.DaoFactory;
import tobystudy.group.object_di.v5.UserDao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class UserDaoV5Test {

    private ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
    private UserDao userDao = ac.getBean("userDao", UserDao.class);
    private CountingConnectionMaker ccm = ac.getBean("countingConnectionMaker", CountingConnectionMaker.class);

    @Test
    public void addTest() throws Exception {
        //given
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
        User addUser = User.builder().id("TestId").name("TestName").password("TestPassword").build();
        userDao.add(addUser);
        User findUser = userDao.get(addUser.getId());
        //when
        //then
        assertThat(ccm.getCount()).isEqualTo(2);

    }

}