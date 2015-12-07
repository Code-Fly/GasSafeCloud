package com.fujitsu.queue.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.queue.service.iface.IQueueService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Barrie on 15/12/6.
 */
@Service
public class ActiveMQService extends BaseService implements IQueueService {
    private Connection connection = null;
    private Session session = null;

    @Override
    public void connect() throws JMSException {
        ConnectionFactory connectionFactory;

        connectionFactory = new ActiveMQConnectionFactory(
                Const.Queue.ACTIVEMQ_USER_NAME,
                Const.Queue.ACTIVEMQ_PASSWORD,
                Const.Queue.ACTIVEMQ_HOST);

        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    public void close() throws JMSException {
        if (null != session) {
            session.close();
        }
        if (null != connection) {
            connection.close();
        }
    }

    @Override
    public void clear(String destination) throws JMSException {
        MessageConsumer consumer = this.preReceive(destination, null);
        int count = 0;
        while (null != this.doReceive(consumer)) {
            count++;
        }
        logger.info(count + " messages have been received");
    }

    @Override
    public void clear(String destination, String filter) throws JMSException {
        MessageConsumer consumer = this.preReceive(destination, filter);

        int count = 0;
        while (null != this.doReceive(consumer)) {
            count++;
        }
        logger.info(count + " messages have been received");
    }

    @Override
    public void send(String destination, String content, String type) throws JMSException {
        TextMessage message = session.createTextMessage(content);
        message.setJMSType(type);
        this.doSend(destination, message);
    }


    @Override
    public String receive(String destination, String filter) throws JMSException {
        MessageConsumer consumer = this.preReceive(destination, filter);
        Message msg = this.doReceive(consumer);
        if (null != msg) {
            return ((TextMessage) msg).getText();
        }
        return null;
    }


    @Override
    public List<String> browse(String destination) throws JMSException {
        Enumeration msgs = this.doBrowse(destination);

        List<String> msgList = new ArrayList<>();
        if (!msgs.hasMoreElements()) {
            logger.info("No messages in queue");
        } else {
            while (msgs.hasMoreElements()) {
                TextMessage tempMsg = (TextMessage) msgs.nextElement();
                msgList.add(tempMsg.getText());
            }
        }
        return msgList;
    }

    private void doSend(String destination, Message message) throws JMSException {
        Destination dest;
        MessageProducer producer;
        if (destination.startsWith("topic://")) {
            dest = session.createTopic(destination.replace("topic://", ""));
        } else {
            dest = session.createQueue(destination.replace("queue://", ""));
        }

        producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(message);
        session.commit();
        producer.close();
    }

    private MessageConsumer preReceive(String destination, String filter) throws JMSException {
        Destination dest;
        MessageConsumer consumer;

        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        if (destination.startsWith("topic://")) {
            dest = session.createTopic(destination.replace("topic://", ""));
        } else {
            dest = session.createQueue(destination.replace("queue://", ""));
        }
        if (null != filter) {
            consumer = session.createConsumer(dest, filter);
        } else {
            consumer = session.createConsumer(dest);
        }

        return consumer;
    }

    private Message doReceive(MessageConsumer consumer) throws JMSException {
        return consumer.receive(1000);
    }

    private Enumeration doBrowse(String destination) throws JMSException {
        Queue queue = null;
        if (destination.startsWith("queue://")) {
            queue = session.createQueue(destination.replace("queue://", ""));
        } else {
            throw new JMSException("Not support");
        }


        QueueBrowser browser = session.createBrowser(queue);
        return browser.getEnumeration();

    }

}
