package cn.itlym.shoulder.platform.notify.sms.notify.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author lym
 */
@Component
public class EmailConsumer {

    private static final String DESTINATION = "sys.mail";

    @JmsListener(destination = DESTINATION)
    public void processMessage(String content) {

    }

}
