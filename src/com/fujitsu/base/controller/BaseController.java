package com.fujitsu.base.controller;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fujitsu.base.entity.ErrorMsg;
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
	public String handleUnexpectedServerError(RuntimeException ex) {
		logger.error("内部错误", ex);
		ErrorMsg errMsg = new ErrorMsg();
		errMsg.setErrcode("-1");
		errMsg.setErrmsg("内部错误");
		return JSONObject.fromObject(errMsg).toString();
	}

}
