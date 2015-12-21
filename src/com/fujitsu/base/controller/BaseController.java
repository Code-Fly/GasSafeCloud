package com.fujitsu.base.controller;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.entity.ErrorMsg;
import com.fujitsu.base.exception.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.jms.JMSException;

/**
 *
 */
public abstract class BaseController extends Const {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(ConnectionFailedException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public String handleConnectionFailedException(ConnectionFailedException ex) {
        logger.error("Connection Failed", ex);
        ErrorMsg errMsg = new ErrorMsg(HttpStatus.SERVICE_UNAVAILABLE.toString(), "Connection Failed");
        return JSONObject.fromObject(errMsg).toString();
    }

    @ExceptionHandler(JMSException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public String handleJMSException(JMSException ex) {
        logger.error("Message Queue connect error", ex);
        ErrorMsg errMsg = new ErrorMsg(HttpStatus.SERVICE_UNAVAILABLE.toString(), "Message Queue connect error");
        return JSONObject.fromObject(errMsg).toString();
    }

    @ExceptionHandler(OAuthException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String handleOAuthException(OAuthException ex) {
        logger.error("Not Authorised", ex);
        ErrorMsg errMsg = new ErrorMsg(HttpStatus.UNAUTHORIZED.toString(), "Not Authorised");
        return JSONObject.fromObject(errMsg).toString();
    }

    @ExceptionHandler(GasSafeException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public String handleGasSafeException(GasSafeException ex) {
        logger.error("Gas safe cloud api error", ex);
        ErrorMsg errMsg = new ErrorMsg("-6", "Gas safe cloud api error");
        return JSONObject.fromObject(errMsg).toString();
    }

    @ExceptionHandler(AccessTokenException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public String handleAccessTokenException(AccessTokenException ex) {
        logger.error(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(WeChatException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public String handleWeChatException(WeChatException ex) {
        logger.error(ex.getMessage());
        return ex.getMessage();
    }


}
