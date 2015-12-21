package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Barrie on 15/12/21.
 */
public interface IMediaService {
    String get(String mediaId, HttpServletResponse httpServletResponse) throws ConnectionFailedException, WeChatException, AccessTokenException;
}
