package tobystudy.group.object_di.v3;


public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    public ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }
}
