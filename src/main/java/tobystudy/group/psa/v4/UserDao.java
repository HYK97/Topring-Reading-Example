package tobystudy.group.psa.v4;

import java.util.List;

public interface UserDao {
    void add(User user);

    void deleteAll();

    int getCount();

    User get(String id);

    List<User> getAll();

    void update(User user);
}
