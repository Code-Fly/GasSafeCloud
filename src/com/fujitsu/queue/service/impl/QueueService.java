package com.fujitsu.queue.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.queue.service.iface.IQueueService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created by Barrie on 15/12/6.
 */
@Service
public class QueueService extends BaseService implements IQueueService {
    // Connection ：JMS 客户端到JMS Provider 的连接
    private Connection connection = null;
    // Session： 一个发送或接收消息的线程
    private Session session;

    @Override
    public void connect() throws JMSException {
        // ConnectionFactory ：连接工厂，JMS 用它创建连接
        ConnectionFactory connectionFactory;

        // 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
        connectionFactory = new ActiveMQConnectionFactory(
                Const.Queue.QUEUE_USER_NAME,
                Const.Queue.QUEUE_PASSWORD,
                Const.Queue.QUEUE_URL);

        // 构造从工厂得到连接对象
        connection = connectionFactory.createConnection();
        // 启动
        connection.start();
        // 获取操作连接
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
        // 发送消息到目的地方
        System.out.println("发送消息：" + content);
        this.send(queue, message);
    }

    private void send(String queue, Message message) throws JMSException {
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        // MessageProducer：消息发送者
        MessageProducer producer;

        // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
        destination = session.createQueue(queue);
        // 得到消息生成者【发送者】
        producer = session.createProducer(destination);
        // 设置不持久化，此处学习，实际根据项目决定
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        // 构造消息，此处写死，项目就是参数，或者方法获取
        producer.send(message);
        session.commit();
        producer.close();
    }

}
