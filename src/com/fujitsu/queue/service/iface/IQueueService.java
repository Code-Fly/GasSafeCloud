package com.fujitsu.queue.service.iface;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created by Barrie on 15/12/6.
 */
public interface IQueueService {
    void connect() throws JMSException;

    void close() throws JMSException;

    void sendText(String destination, String content) throws JMSException;

    void sendMap(String destination, String content) throws JMSException;

    void sendStream(String destination, String content) throws JMSException;

    void sendBytes(String destination, byte[] bytes) throws JMSException;

    void sendObject(String destination, Serializable object) throws JMSException;

    String receiveText(String destination) throws JMSException;

    MapMessage receiveMap(String destination) throws JMSException;

    StreamMessage receiveStream(String destination) throws JMSException;

    BytesMessage receiveBytes(String destination) throws JMSException;

    Serializable receiveObject(String destination) throws JMSException;
}
