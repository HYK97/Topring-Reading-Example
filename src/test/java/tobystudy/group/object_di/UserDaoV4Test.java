package tobystudy.group.object_di;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobystudy.group.object_di.v4.DaoFactory;
import tobystudy.group.object_di.v4.UserDao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class UserDaoV4Test {

    private ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);

    private UserDao userDao = ac.getBean("userDao", UserDao.class);

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

}