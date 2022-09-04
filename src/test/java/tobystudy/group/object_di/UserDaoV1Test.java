package tobystudy.group.object_di;

import org.junit.jupiter.api.Test;
import tobystudy.group.object_di.v1.NUserDao;
import tobystudy.group.object_di.v1.UserDao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDaoV1Test {

    private final UserDao userDao=new NUserDao();


    @Test
    public void addTest() throws Exception{
        //given
        User addUser =User.builder().id("TestId").name("TestName").password("TestPassword").build();
        //when
        userDao.add(addUser);
        User findUser = userDao.get(addUser.getId());
        //then
        assertThat(findUser).isEqualTo(addUser);
    }

}