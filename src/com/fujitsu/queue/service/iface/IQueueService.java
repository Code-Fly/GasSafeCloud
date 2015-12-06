package com.fujitsu.queue.service.iface;

import javax.jms.JMSException;

/**
 * Created by Barrie on 15/12/6.
 */
public interface IQueueService {
    void connect() throws JMSException;

    void close() throws JMSException;

    void sendText(String queue, String content) throws JMSException;
}
