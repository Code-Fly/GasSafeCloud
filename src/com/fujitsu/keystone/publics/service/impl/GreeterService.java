package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.service.iface.IGreeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * Created by Barrie on 15/11/23.
 */
@Service
public class GreeterService extends BaseService implements IGreeterService {
    @Autowired
    private MessageChannel inputChannel;


    @Autowired
    private QueueChannel outputChannel;

    public void post(String name) {
        logger.info(name);
        inputChannel.send(MessageBuilder.withPayload(name).build());
    }

    public void get() {
        logger.info(outputChannel.receive().toString());
    }


}
