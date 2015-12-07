package com.fujitsu.queue.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.queue.service.iface.IQueueService;
import org.apache.qpid.amqp_1_0.jms.impl.ConnectionFactoryImpl;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.apache.qpid.amqp_1_0.jms.impl.TopicImpl;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Barrie on 15/12/7.
 */
@Service
public class ApolloService extends BaseService implements IQueueService {
    private Session session;
    private Connection connection;

    @Override
    public void connect() throws JMSException {
        String user = Const.Queue.APOLLO_USER_NAME;
        String password = Const.Queue.APOLLO_PASSWORD;
        String host = Const.Queue.APOLLO_HOST;
        int port = Integer.parseInt(Const.Queue.APOLLO_PORT);

        ConnectionFactoryImpl factory = new ConnectionFactoryImpl(host, port, user, password);
        connection = factory.createConnection(user, password);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
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
        MessageProducer producer = this.preSend(destination);
        TextMessage message = session.createTextMessage(content);
        this.doSend(producer, message);
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

    private MessageProducer preSend(String destination) throws JMSException {
        Destination dest = null;
        if (destination.startsWith("topic://")) {
            dest = new TopicImpl(destination);
        } else {
            dest = new QueueImpl(destination);
        }

        MessageProducer producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        return producer;
    }

    private void doSend(MessageProducer producer, Message message) throws JMSException {
        producer.send(message);
    }

    private MessageConsumer preReceive(String destination, String filter) throws JMSException {
        Destination dest = null;
        MessageConsumer consumer;

        if (destination.startsWith("topic://")) {
            dest = new TopicImpl(destination);
        } else {
            dest = new QueueImpl(destination);
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
            queue = new QueueImpl(destination);
        } else {
            throw new JMSException("Not support");
        }

        QueueBrowser browser = session.createBrowser(queue);
        return browser.getEnumeration();

    }
}
