package com.fujitsu.queue.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.queue.service.iface.IQueueService;
import org.apache.qpid.amqp_1_0.jms.impl.ConnectionFactoryImpl;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.apache.qpid.amqp_1_0.jms.impl.TopicImpl;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.Serializable;

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
    public void sendText(String destination, String content) throws JMSException {
        TextMessage message = session.createTextMessage(content);
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

    private void send(String destination, Message message) throws JMSException {
        Destination dest = null;
        if (destination.startsWith("topic://")) {
            dest = new TopicImpl(destination);
        } else {
            dest = new QueueImpl(destination);
        }

        MessageProducer producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(message);
    }

    private Message receive(String destination) throws JMSException {
        Destination dest = null;
        if (destination.startsWith("topic://")) {
            dest = new TopicImpl(destination);
        } else {
            dest = new QueueImpl(destination);
        }
        MessageConsumer consumer = session.createConsumer(dest);

        return consumer.receive();
    }
}
