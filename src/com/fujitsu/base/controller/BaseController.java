package com.fujitsu.base.controller;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fujitsu.base.entity.ErrorMsg;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.Const;

/**
 * 
 * @author Barrie
 *
 * @param <T>
 */
public abstract class BaseController extends Const {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handleUnexpectedServerException(RuntimeException ex) {
		logger.error("内部错误", ex);
		ErrorMsg errMsg = new ErrorMsg();
		errMsg.setErrcode("-1");
		errMsg.setErrmsg("内部错误");
		return JSONObject.fromObject(errMsg).toString();
	}
	
	@ExceptionHandler(ConnectionFailedException.class)
	@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public String handleConnectionFailedException(ConnectionFailedException ex) {
		logger.error("链接失败", ex);
		ErrorMsg errMsg = new ErrorMsg();
		errMsg.setErrcode("-2");
		errMsg.setErrmsg("链接失败");
		return JSONObject.fromObject(errMsg).toString();
	}
	
	@ExceptionHandler(AccessTokenException.class)
	@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public String handleAccessTokenException(AccessTokenException ex) {
		logger.error(ex.getMessage());
		return ex.getMessage().toString();
	}

}
