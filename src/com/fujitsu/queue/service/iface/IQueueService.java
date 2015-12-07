package com.fujitsu.queue.service.iface;

import javax.jms.JMSException;
import java.util.List;

/**
 * Created by Barrie on 15/12/6.
 */
public interface IQueueService {
    void connect() throws JMSException;

    void close() throws JMSException;

    void clear(String destination) throws JMSException;

    void clear(String destination, String filter) throws JMSException;

    void send(String destination, String content, String type) throws JMSException;

    String receive(String destination, String filter) throws JMSException;

    List<String> browse(String destination) throws JMSException;
}
