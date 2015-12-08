package com.fujitsu.queue.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.entity.ErrorMsg;
import com.fujitsu.keystone.publics.event.Event;
import com.fujitsu.queue.service.iface.IQueueService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barrie on 15/12/7.
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class QueueController extends BaseController {
    @Resource
    IQueueService queueService;

    @RequestMapping(value = "/queue/browse/{prefix}/{queue}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String browse(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable String queue,
                         @PathVariable String prefix,
                         @RequestParam(value = "filter", required = false) String filter) throws JMSException {
        List<String> list;
        List<String> result = new ArrayList<>();
        if ("null".equals(prefix.trim().toLowerCase())) {
            prefix = "";
        } else {
            prefix += ".";
        }

        queueService.connect();
        list = queueService.browse(Queue.ACTIVEMQ_PROTOCAL_QUEUE + prefix + queue);
        queueService.close();
        for (int i = 0; i < list.size(); i++) {
            if (null == filter || filter.isEmpty()) {
                result.add(list.get(i));
            } else {
                String type = JSONObject.fromObject(list.get(i)).getString(Event.MSG_TYPE);
                if (type.equals(filter)) {
                    result.add(list.get(i));
                }
            }
        }

        return JSONArray.fromObject(result).toString();
    }

    @RequestMapping(value = "/queue/clear/{prefix}/{queue}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String clear(HttpServletRequest request, HttpServletResponse response,
                        @PathVariable String queue,
                        @PathVariable String prefix,
                        @RequestParam(value = "filter", required = false) String filter) throws JMSException {
        if ("null".equals(prefix.trim().toLowerCase())) {
            prefix = "";
        } else {
            prefix += ".";
        }

        queueService.connect();
        if (null == filter || filter.isEmpty()) {
            queueService.clear(Queue.ACTIVEMQ_PROTOCAL_QUEUE + prefix + queue);
        } else {
            queueService.clear(Queue.ACTIVEMQ_PROTOCAL_QUEUE + prefix + queue, filter);
        }

        queueService.close();
        return JSONObject.fromObject(new ErrorMsg("0", "ok")).toString();
    }
}
