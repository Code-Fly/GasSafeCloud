package com.fujitsu.base.controller;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.entity.ErrorMsg;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.OAuthException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
public abstract class BaseController extends Const {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleUnexpectedServerException(RuntimeException ex) {
        logger.error("内部错误", ex);
        ErrorMsg errMsg = new ErrorMsg("-1", "Internal Error");
        return JSONObject.fromObject(errMsg).toString();
    }

    @ExceptionHandler(ConnectionFailedException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ResponseBody
    public String handleConnectionFailedException(ConnectionFailedException ex) {
        logger.error("连接失败", ex);
        ErrorMsg errMsg = new ErrorMsg("-2", "Connection Failed");
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

    @ExceptionHandler(OAuthException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public String handleWeChatException(OAuthException ex) {
        logger.error("not authorised", ex);
        ErrorMsg errMsg = new ErrorMsg("-3", "Not Authorised");
        return JSONObject.fromObject(errMsg).toString();
    }

}
