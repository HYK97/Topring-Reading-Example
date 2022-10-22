package tobystudy.group.aop.v2;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionHandler implements InvocationHandler {

    private UserService target;
    private PlatformTransactionManager transactionManager;
    private String pattern;

    public TransactionHandler(UserService target, PlatformTransactionManager transactionManager, String pattern) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 질문 이건 왜안되는지?
        //TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        if (method.getName().startsWith(pattern)) {
            return startTransaction(method, args);
        } else {
            return method.invoke(target, args);
        }
    }

    private Object startTransaction(Method method, Object[] args) throws Throwable {
        // 질문 이건되고
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object result = method.invoke(target, args);
            transactionManager.commit(status);
            return result;
        } catch (InvocationTargetException e) { // ********  예외가 포장되서 오기때문에 다음과 같이 처리해줘야한다.
            transactionManager.rollback(status);
            throw e.getTargetException();
        }
    }
}
