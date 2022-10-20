package tobystudy.group.aop.v1;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class UserServiceTx implements UserService {
    private final PlatformTransactionManager transactionManager;
    private UserService target;

    public UserServiceTx(UserService target, PlatformTransactionManager transactionManager) {
        this.target = target;
        this.transactionManager = transactionManager;
    }


    @Override
    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            target.upgradeLevels();
            this.transactionManager.commit(status);
        } catch (Exception e) {
            this.transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public void add(User user) {
        target.add(user);
    }


}
