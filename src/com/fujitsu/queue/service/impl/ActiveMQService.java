package com.fujitsu.queue.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.queue.service.iface.IQueueService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.Serializable;
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
    public void sendText(String destination, String content, String type) throws JMSException {
        TextMessage message = session.createTextMessage(content);
        message.setJMSType(type);
        this.send(destination, message);
    }

    @Override
    public void sendMap(String destination, String content) throws JMSException {

    }

    @Override
    public void sendStream(String destination, String content) throws JMSException {

    }

    @Override
    public void sendBytes(String destination, byte[] bytes) throws JMSException {

    }

    @Override
    public void sendObject(String destination, Serializable object) throws JMSException {

    }

    @Override
    public String receiveText(String destination) throws JMSException {
        return ((TextMessage) this.receive(destination)).getText();
    }

    @Override
    public MapMessage receiveMap(String destination) throws JMSException {
        return null;
    }

    @Override
    public StreamMessage receiveStream(String destination) throws JMSException {
        return null;
    }

    @Override
    public BytesMessage receiveBytes(String destination) throws JMSException {
        return null;
    }

    @Override
    public Serializable receiveObject(String destination) throws JMSException {
        return null;
    }

    @Override
    public List<String> browseTextQueue(String destination) throws JMSException {
        Enumeration msgs = this.browse(destination);

        List<String> msgList = new ArrayList<>();
        if (!msgs.hasMoreElements()) {
            logger.info("No messages in queue");
        } else {
            while (msgs.hasMoreElements()) {
                TextMessage tempMsg = (TextMessage) msgs.nextElement();
                logger.info("Message: " + tempMsg.getText());
                msgList.add(tempMsg.getText());
            }
        }
        return msgList;
    }

    private void send(String destination, Message message) throws JMSException {
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

    private Message receive(String destination) throws JMSException {
        Destination dest;
        MessageConsumer consumer;

        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        if (destination.startsWith("topic://")) {
            dest = session.createTopic(destination.replace("topic://", ""));
        } else {
            dest = session.createQueue(destination.replace("queue://", ""));
        }
        consumer = session.createConsumer(dest);

        return consumer.receive();
    }

    private Enumeration browse(String destination) throws JMSException {
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
