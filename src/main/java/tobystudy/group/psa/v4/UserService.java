package tobystudy.group.psa.v4;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMMEND_FORGOLD = 30;
    private final UserDao userDao;
    private DataSource dataSource;


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
     * */

    public void upgradeLevels() throws SQLException {
        TransactionSynchronizationManager.initSynchronization();
        Connection conn = DataSourceUtils.getConnection(dataSource);
        conn.setAutoCommit(false);
        try {
            List<User> all = userDao.getAll();
            for (User user : all) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    private boolean canUpgradeLevel(User user) {
        switch (user.getLevel()) {
            case BASIC:
                return user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER;
            case SILVER:
                return user.getRecommend() >= MIN_RECCOMMEND_FORGOLD;
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("없는레벨 " + user.getLevel());
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }


}
