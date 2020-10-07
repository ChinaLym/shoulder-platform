package cn.itlym.shoulder.platform.notify.sms.notify.producer;

import cn.itlym.shoulder.platform.notify.sms.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author lym
 */
//@Component
public class EmailProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void produce(EmailDTO email) {

    }
}
