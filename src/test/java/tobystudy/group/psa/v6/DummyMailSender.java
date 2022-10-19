package tobystudy.group.psa.v6;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@Slf4j
public class DummyMailSender implements MailSender {
    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        log.info("simpleMessage = {} ", simpleMessage.toString());
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        for (SimpleMailMessage simpleMessage : simpleMessages) {
            log.info("simpleMessage = {} ", simpleMessage.toString());
        }
    }
}