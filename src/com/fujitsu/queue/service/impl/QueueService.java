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
public class QueueService extends BaseService implements IQueueService {
    private Connection connection = null;
    private Session session = null;

    @Override
    public void connect() throws JMSException {
        String user = Const.Queue.ACTIVEMQ_USER_NAME;
        String password = Const.Queue.ACTIVEMQ_PASSWORD;
        String host = Const.Queue.ACTIVEMQ_HOST;
        int port = Integer.parseInt(Const.Queue.ACTIVEMQ_PORT);

        ConnectionFactory connectionFactory;

        String connectionURI = "tcp://" + host + ":" + port;

        connectionFactory = new ActiveMQConnectionFactory(
                user,
                password,
                connectionURI);

        connection = connectionFactory.createConnection();
        connection.start();
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
        MessageConsumer consumer = this.getConsumer(destination, null);
        int count = 0;
        while (null != this.doReceive(consumer)) {
            count++;
        }
        logger.info(count + " messages have been deleted from the queue");
    }

    @Override
    public void clear(String destination, String filter) throws JMSException {
        MessageConsumer consumer = this.getConsumer(destination, filter);
        int count = 0;
        while (null != this.doReceive(consumer)) {
            count++;
        }
        logger.info(count + " messages have been deleted from the queue");
    }

    @Override
    public void send(String destination, String content, String type) throws JMSException {
        MessageProducer producer = this.getProducer(destination);
        TextMessage message = session.createTextMessage(content);
        message.setJMSType(type);
        this.doSend(producer, message);
    }

    @Override
    public String receive(String destination, String filter) throws JMSException {
        MessageConsumer consumer = this.getConsumer(destination, filter);
        Message msg = this.doReceive(consumer);

        if (null != msg) {
            return ((TextMessage) msg).getText();
        }
        return null;
    }


    @Override
    public List<String> browse(String destination) throws JMSException {
        QueueBrowser browser = this.getQueueBrowser(destination);
        Enumeration msgs = this.doBrowse(browser);

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


    private void doSend(MessageProducer producer, Message message) throws JMSException {
        producer.send(message);
        session.commit();
        producer.close();
    }

    private Message doReceive(MessageConsumer consumer) throws JMSException {
        return consumer.receive(Long.parseLong(Const.Queue.ACTIVEMQ_RECEIVE_TIMEOUT));
    }

    private Enumeration doBrowse(QueueBrowser browser) throws JMSException {
        return browser.getEnumeration();

    }

    private MessageProducer getProducer(String destination) throws JMSException {
        Destination dest;
        MessageProducer producer;

        session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

        if (destination.startsWith(Const.Queue.ACTIVEMQ_PROTOCAL_TOPIC)) {
            dest = session.createTopic(destination.replace(Const.Queue.ACTIVEMQ_PROTOCAL_TOPIC, ""));
        } else {
            dest = session.createQueue(destination.replace(Const.Queue.ACTIVEMQ_PROTOCAL_QUEUE, ""));
        }

        producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.setTimeToLive(Long.parseLong(Const.Queue.ACTIVEMQ_MSG_TIMETOLIVE));

        return producer;
    }


    private MessageConsumer getConsumer(String destination, String filter) throws JMSException {
        Destination dest;
        MessageConsumer consumer;

        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

        if (destination.startsWith(Const.Queue.ACTIVEMQ_PROTOCAL_TOPIC)) {
            dest = session.createTopic(destination.replace(Const.Queue.ACTIVEMQ_PROTOCAL_TOPIC, ""));
        } else {
            dest = session.createQueue(destination.replace(Const.Queue.ACTIVEMQ_PROTOCAL_QUEUE, ""));
        }

        if (null != filter) {
            consumer = session.createConsumer(dest, filter);
        } else {
            consumer = session.createConsumer(dest);
        }

        return consumer;
    }

    private QueueBrowser getQueueBrowser(String destination) throws JMSException {
        Queue queue = null;

        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

        if (destination.startsWith(Const.Queue.ACTIVEMQ_PROTOCAL_QUEUE)) {
            queue = session.createQueue(destination.replace(Const.Queue.ACTIVEMQ_PROTOCAL_QUEUE, ""));
        } else {
            throw new JMSException("can not browse non-queue messages");
        }

        return session.createBrowser(queue);
    }


}
