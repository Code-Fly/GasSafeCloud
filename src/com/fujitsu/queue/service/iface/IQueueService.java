package com.fujitsu.queue.service.iface;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created by Barrie on 15/12/6.
 */
public interface IQueueService {
    void connect() throws JMSException;

    void close() throws JMSException;

    void sendText(String queue, String content) throws JMSException;

    void sendMap(String queue, String content) throws JMSException;

    void sendStream(String queue, String content) throws JMSException;

    void sendBytes(String queue, byte[] bytes) throws JMSException;

    void sendObject(String queue, Serializable object) throws JMSException;

    String receiveText(String queue) throws JMSException;

    MapMessage receiveMap(String queue) throws JMSException;

    StreamMessage receiveStream(String queue) throws JMSException;

    BytesMessage receiveBytes(String queue) throws JMSException;

    Serializable receiveObject(String queue) throws JMSException;
}
