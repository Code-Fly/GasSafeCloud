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
    public void clear(String destination) {

    }

    @Override
    public void clear(String destination, String filter) {

    }

    @Override
    public void send(String destination, String content, String type) throws JMSException {
        TextMessage message = session.createTextMessage(content);
        this.doSend(destination, message);
    }

    @Override
    public String receive(String destination, String filter) throws JMSException {
        Message msg = this.doReceive(destination, filter);
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

    private Message doReceive(String destination, String filter) throws JMSException {
        Destination dest = null;
        if (destination.startsWith("topic://")) {
            dest = new TopicImpl(destination);
        } else {
            dest = new QueueImpl(destination);
        }
        MessageConsumer consumer = session.createConsumer(dest, filter);

        return consumer.receiveNoWait();
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
