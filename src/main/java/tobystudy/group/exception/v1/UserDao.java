package tobystudy.group.exception.v1;

import tobystudy.group.template.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    void deleteAll();

    int getCount();

    User get(String id);

    List<User> getAll();
}
