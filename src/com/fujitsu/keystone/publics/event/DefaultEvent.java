package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Barrie on 15/12/7.
 */
public class DefaultEvent extends Event {
    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws JMSException, ConnectionFailedException, AccessTokenException, WeChatException,UnsupportedEncodingException {
        String respXml = null;

        super.execute(request, requestJson);
        return respXml;
    }
}
