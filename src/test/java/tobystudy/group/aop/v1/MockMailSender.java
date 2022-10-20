package tobystudy.group.aop.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MockMailSender implements MailSender {

    private List<String> requests = new ArrayList<>();

    public List<String> getRequest() {
        return requests;
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        requests.add(Objects.requireNonNull(simpleMessage.getTo())[0]);
        log.info("send mail info = {} ", simpleMessage.toString());
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        for (SimpleMailMessage simpleMessage : simpleMessages) {
            requests.add(Objects.requireNonNull(simpleMessage.getTo())[0]);
            log.info("send mail info  = {} ", simpleMessage.toString());
        }
    }
}