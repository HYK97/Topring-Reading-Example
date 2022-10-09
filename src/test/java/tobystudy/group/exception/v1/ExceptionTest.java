package tobystudy.group.exception.v1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import tobystudy.group.template.User;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = DaoFactory.class)
public class ExceptionTest {

    @Autowired
    UserDao userDao;
    @Autowired
    DataSource dataSource;


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
    public void duplicateKey() {
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            userDao.add(user1);
            userDao.add(user1);
        }).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    public void sqlTranslate() {
        //given
        //when
        //then
        try {
            userDao.add(user1);
            userDao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlException = (SQLException) e.getRootCause();
            SQLExceptionTranslator sqlExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertThat(sqlExceptionTranslator.translate(null, null, sqlException)).isInstanceOf(DuplicateKeyException.class);
        }
    }

}
