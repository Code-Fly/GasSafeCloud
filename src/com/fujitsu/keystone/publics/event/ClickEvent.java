/**
 *
 */
package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.query.Query;
import com.fujitsu.keystone.publics.service.impl.MenuService;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import net.sf.json.JSONObject;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author Barrie
 */
public class ClickEvent extends Event {
    public static final String EVENT_KEY = "EventKey";

    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws JMSException, WeChatException, ConnectionFailedException, AccessTokenException, UnsupportedEncodingException, GasSafeException {
        super.execute(request, requestJson);

        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(FROM_USER_NAME);
        // 开发者微信号
        String toUserName = requestJson.getString(TO_USER_NAME);

        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
        // 事件KEY值，与创建菜单时的key值对应
        String eventKey = requestJson.getString(EVENT_KEY);
        // 根据key值判断用户点击的按钮
        // 单位查询
        if (eventKey.equals(MenuService.GL_DWCX)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("单位查询").append(Const.LINE_SEPARATOR);
            buffer.append(Const.LINE_SEPARATOR);
            buffer.append("查询该单位气瓶充装、存储许可信息和本单位作业人员信息。").append(Const.LINE_SEPARATOR);
            buffer.append("查询详细:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_3 + Query.FILLING_STORAGE_DETAIL + Query.SEPARATOR_3 + "单位全名").append(Const.LINE_SEPARATOR);
            buffer.append("查询单位名称:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_3 + Query.FILLING_STORAGE_LIST + Query.SEPARATOR_3 + "查询名").append(Const.LINE_SEPARATOR);
            buffer.append(Const.LINE_SEPARATOR);
            buffer.append("查询该单位燃气配送、运输许可信息和从业人员信息。").append(Const.LINE_SEPARATOR);
            buffer.append("查询详细:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_3 + Query.DISTRIBUTION_TRANSPORTATION_DETAIL + Query.SEPARATOR_3 + "单位全名").append(Const.LINE_SEPARATOR);
            buffer.append("查询单位名称:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_3 + Query.DISTRIBUTION_TRANSPORTATION_LIST + Query.SEPARATOR_3 + "查询名").append(Const.LINE_SEPARATOR);
            buffer.append(Const.LINE_SEPARATOR);
            buffer.append("查询该单位气瓶检验信息和检验人员信息。").append(Const.LINE_SEPARATOR);
            buffer.append("查询详细:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_3 + Query.INSPECTION_TESTING_DETAIL + Query.SEPARATOR_3 + "单位全名").append(Const.LINE_SEPARATOR);
            buffer.append("查询单位名称:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_3 + Query.INSPECTION_TESTING_LIST + Query.SEPARATOR_3 + "查询名").append(Const.LINE_SEPARATOR);
            buffer.append(Const.LINE_SEPARATOR);
            buffer.append("例:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_3 + Query.FILLING_STORAGE_DETAIL + Query.SEPARATOR_3 + "无锡华润燃气有限公司").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_3 + Query.FILLING_STORAGE_LIST + Query.SEPARATOR_3 + "华润").append(Const.LINE_SEPARATOR);

            textMessage.setContent(buffer.toString());
            respXml = MessageService.messageToXml(textMessage);
        }
        // 咨询投诉
        else if (eventKey.equals(MenuService.FW_ZXTS)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("咨询投诉").append(Const.LINE_SEPARATOR);
            buffer.append(Const.LINE_SEPARATOR);
            buffer.append("气瓶用户对任何环节有疑问均可通过微信方式咨询，并提出投诉和建议。").append(Const.LINE_SEPARATOR);
            buffer.append("投诉信息支持文本、图像、语音、视频。").append(Const.LINE_SEPARATOR);
            buffer.append("图像、语音、视频请通过微信直接上传").append(Const.LINE_SEPARATOR);
            buffer.append("文本输入格式:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_2 + "文本信息").append(Const.LINE_SEPARATOR);
            buffer.append("例:" + Query.SEPARATOR_2 + "我想咨询一个问题");
            textMessage.setContent(buffer.toString());
            respXml = MessageService.messageToXml(textMessage);
        }
        // 安全定位
        else if (MenuService.QP_AQDW.equals(eventKey)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("安全定位").append(Const.LINE_SEPARATOR)
                    .append(Const.LINE_SEPARATOR)
                    .append("请输入条件(气瓶编号+生产年度+制造单位代号)，查询气瓶当前位置和当前安全状况信息")
                    .append(Const.LINE_SEPARATOR)
                    .append("输入格式:").append(Const.LINE_SEPARATOR)
                    .append(Query.SEPARATOR_1 + "气瓶编号" + Query.SEPARATOR_1 + "生产年度" + Query.SEPARATOR_1 + "制造单位代号").append(Const.LINE_SEPARATOR)
                    .append("例:" + Query.SEPARATOR_1 + "013264" + Query.SEPARATOR_1 + "2003" + Query.SEPARATOR_1 + "ms");
            textMessage.setContent(buffer.toString());
            respXml = MessageService.messageToXml(textMessage);
        }
        // 云系统
        else if (MenuService.GL_YXT.equals(eventKey)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("云系统").append(Const.LINE_SEPARATOR);
            buffer.append(Const.LINE_SEPARATOR);
            buffer.append("气瓶用户发送微信文本信息实现气瓶安全云系统自助注册审核以及修改登录用户名密码。").append(Const.LINE_SEPARATOR);
            buffer.append("默认用户名:01,密码:000000。").append(Const.LINE_SEPARATOR);
            buffer.append(Const.LINE_SEPARATOR);
            buffer.append("自助注册审核:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_1 + "单位编号").append(Const.LINE_SEPARATOR);
            buffer.append("例:" + Query.SEPARATOR_1 + "CCSTAA000001").append(Const.LINE_SEPARATOR);
            buffer.append(Const.LINE_SEPARATOR);
            buffer.append("修改账号密码:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR_1 + "单位编号" + Query.SEPARATOR_1 + "旧账号" + Query.SEPARATOR_1 + "旧密码" + Query.SEPARATOR_1 + "新账号" + Query.SEPARATOR_1 + "新密码").append(Const.LINE_SEPARATOR);
            buffer.append("例:" + Query.SEPARATOR_1 + "CCSTAA000001" + Query.SEPARATOR_1 + "张三" + Query.SEPARATOR_1 + "pass1" + Query.SEPARATOR_1 + "lisi" + Query.SEPARATOR_1 + "passw2").append(Const.LINE_SEPARATOR);
            ;
            textMessage.setContent(buffer.toString());
            respXml = MessageService.messageToXml(textMessage);

        }
        // 其他按钮
        else {
            textMessage.setContent("功能尚未开放，敬请期待！" + eventKey);
            respXml = MessageService.messageToXml(textMessage);
        }

        return respXml;
    }

}
