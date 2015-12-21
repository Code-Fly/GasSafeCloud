package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.keystone.publics.service.iface.IMediaService;
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Barrie on 15/12/21.
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class MediaController extends BaseController {
    @Resource
    IMediaService mediaService;

    @RequestMapping(value = "/media/get/{mediaId}")
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response,
                      @PathVariable String mediaId
    ) throws ConnectionFailedException, WeChatException, AccessTokenException {

        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);

        return mediaService.get(mediaId, response);

    }
}
