package tobystudy.group.object_di;

import org.junit.jupiter.api.Test;
import tobystudy.group.object_di.v2.ConnectionMaker;
import tobystudy.group.object_di.v2.NConnectionMaker;
import tobystudy.group.object_di.v2.UserDao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDaoV2Test {

    /*직접 주입*/
    private final ConnectionMaker connectionMaker = new NConnectionMaker();
    private final UserDao userDao = new UserDao(connectionMaker);


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