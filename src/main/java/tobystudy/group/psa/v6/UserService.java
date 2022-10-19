package tobystudy.group.psa.v6;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMMEND_FORGOLD = 30;
    private final UserDao userDao;

    private final MailSender mailSender;

    private final PlatformTransactionManager transactionManager;

    public UserService(UserDao userDao, MailSender mailSender, PlatformTransactionManager transactionManager) {
        this.userDao = userDao;
        this.mailSender = mailSender;
        this.transactionManager = transactionManager;
    }

    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<User> all = userDao.getAll();
            for (User user : all) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            this.transactionManager.commit(status);
        } catch (Exception e) {
            this.transactionManager.rollback(status);
            throw e;
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("my@naver.com");
        mail.setSubject("등급 업그레이드 안내");
        mail.setText("사용자의 등급이" + user.getLevel().name() + "로 업그레이드되었습니다.");
        this.mailSender.send(mail);

        //javax. mail을 이용한것 추상화 하기힘듦.
       /* Properties props = new Properties();
        props.put("mail.smtp.host", "mail.ksug.org");
        Session s = Session.getInstance(props);
        MimeMessage message = new MimeMessage(s);
        try {
            message.setFrom(new InternetAddress("asdasd@admin.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("등급 업그레이드 안내");
            message.setText("사용자의 등급이" + user.getLevel().name() + "로 업그레이드되었습니다.");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }*/
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
