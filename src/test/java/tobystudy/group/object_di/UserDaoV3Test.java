package tobystudy.group.object_di;

import org.junit.jupiter.api.Test;
import tobystudy.group.object_di.v3.DaoFactory;
import tobystudy.group.object_di.v3.UserDao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDaoV3Test {

    /*factory를 통한 주입*/
    private UserDao userDao = new DaoFactory().userDao();


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