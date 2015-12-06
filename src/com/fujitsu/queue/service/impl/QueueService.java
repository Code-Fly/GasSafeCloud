package com.fujitsu.queue.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.queue.service.iface.IQueueService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created by Barrie on 15/12/6.
 */
@Service
public class QueueService extends BaseService implements IQueueService {
    private Connection connection = null;
    private Session session = null;

    @Override
    public void connect() throws JMSException {
        ConnectionFactory connectionFactory;

        connectionFactory = new ActiveMQConnectionFactory(
                Const.Queue.QUEUE_USER_NAME,
                Const.Queue.QUEUE_PASSWORD,
                Const.Queue.QUEUE_URL);

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
    public void sendText(String queue, String content) throws JMSException {
        TextMessage message = session.createTextMessage(content);
        this.send(queue, message);
    }

    @Override
    public void sendMap(String queue, String content) throws JMSException {

    }

    @Override
    public void sendStream(String queue, String content) throws JMSException {

    }

    @Override
    public void sendBytes(String queue, byte[] bytes) throws JMSException {
        BytesMessage bytesMessage = session.createBytesMessage();
        bytesMessage.writeBytes(bytes);
        this.send(queue, bytesMessage);
    }

    @Override
    public void sendObject(String queue, Serializable object) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(object);
        this.send(queue, objectMessage);
    }

    @Override
    public String receiveText(String queue) throws JMSException {
        return ((TextMessage) this.receive(queue)).getText();
    }

    @Override
    public MapMessage receiveMap(String queue) throws JMSException {
        return null;
    }

    @Override
    public StreamMessage receiveStream(String queue) throws JMSException {
        return null;
    }

    @Override
    public BytesMessage receiveBytes(String queue) throws JMSException {
        return null;
    }

    @Override
    public Serializable receiveObject(String queue) throws JMSException {
        return null;
    }

    private void send(String queue, Message message) throws JMSException {
        Destination destination;
        MessageProducer producer;

        destination = session.createQueue(queue);
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(message);
        session.commit();
        producer.close();
    }

    private Message receive(String queue) throws JMSException {
        Destination destination;
        MessageConsumer consumer;

        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(queue);
        consumer = session.createConsumer(destination);

        return consumer.receive();
    }

}
