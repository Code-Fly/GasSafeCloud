package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.GasHttpClientUtil;
import com.fujitsu.client.entity.*;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MenuService;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.CharEncoding;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScancodeWaitmsgEvent extends Event {

    public static String SCAN_RESULT = "ScanResult";

    public static String SCAN_CODE_INFO = "ScanCodeInfo";

    public static String EVENT_KEY = "EventKey";

    /**
     * @throws AccessTokenException
     * @throws ConnectionFailedException
     * @throws UnsupportedEncodingException
     */
    public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException,
            AccessTokenException, WeChatException, JMSException, UnsupportedEncodingException, GasSafeException {
        super.execute(request, requestJson);

        String respXml = null;

        String fromUserName = requestJson.getString(FROM_USER_NAME);
        String toUserName = requestJson.getString(TO_USER_NAME);
        String eventKey = requestJson.getString(EVENT_KEY);
        JSONObject scanCodeInfo = JSONObject.fromObject(requestJson.get(SCAN_CODE_INFO));
        String scanResult = scanCodeInfo.getString(SCAN_RESULT);

        TextMessage message = new TextMessage();
        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime());
        message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
        /**
         * 处理message 推送给用户的message
         * [QP02001,132020000001,AG,323232,2015年09月,2045年09月]气瓶安全云www.qpsafe.cn
         */
        int beginIndex = scanResult.lastIndexOf("[");
        int lastIndex = scanResult.lastIndexOf("]");
        // QP02001,132020000001,AG,323232,2015年09月,2045年09月
        String tmp = scanResult.substring(beginIndex+1, lastIndex);
        // [QP02001,132020000001,AG,323232,2015年09月,2045年09月]
        String[] messArray = tmp.split(",");
        Map<String, String> params = new HashMap<String, String>();
        params.put("syzbh", messArray[0]);
        params.put("zcdm", messArray[1]);
        params.put("pcode", messArray[2]);
        params.put("pid", messArray[3]);
        params.put("pDate", messArray[4]);
        params.put("bfrq", messArray[5]);

        StringBuffer sengMsg = new StringBuffer();
        // 身份查询
        if (MenuService.QP_SFCX.equals(eventKey)) {

            String response = GasHttpClientUtil.gasPost("ccstWeChatBarcodegetBottle.htm", params, CharEncoding.UTF_8,
                    fromUserName,scanResult);
            if (SocketFailCode.ERR_CODE_LENGTH == response.length()) {
                sengMsg.append("系统请求socket出现异常:").append(response);
            } else {
                BarcodegetBottleResMsg barMsg = new BarcodegetBottleResMsg();
                JSONObject object = JSONObject.fromObject(response);
                if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
                    sengMsg.append("系统请求socket出现异常:").append(object.get(WebSocketResFiled.ERROR_CODE));
                } else {
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setRootClass(BarcodegetBottleResMsg.class);
                    Map<String, Class> classMap = new HashMap<String, Class>();
                    classMap.put("result", BarcodegetBottleResult.class);
                    jsonConfig.setClassMap(classMap);
                    barMsg = (BarcodegetBottleResMsg) JSONObject.toBean(object, jsonConfig);
                    sengMsg.append("气瓶使用证编号:").append(barMsg.getResult().get(0).getSyzbh()).append(Const.LINE_SEPARATOR)
                            .append("气瓶注册代码:").append(barMsg.getResult().get(0).getZcdm()).append(Const.LINE_SEPARATOR)
                            //.append("单位自有编号:").append(barMsg.getResult().get(0).getZybh()).append(Const.LINE_SEPARATOR)
                            .append("气瓶充装单位:").append(barMsg.getResult().get(0).getJianname()).append(Const.LINE_SEPARATOR)
                            .append("气瓶制造单位代号:").append(messArray[2]).append(Const.LINE_SEPARATOR).append("气瓶品种:")
                            .append(barMsg.getResult().get(0).getClassName()).append(Const.LINE_SEPARATOR)
                            .append("气瓶型号:").append(barMsg.getResult().get(0).getTypeName())
                            .append(Const.LINE_SEPARATOR).append("气瓶编号:").append(barMsg.getResult().get(0).getPid())
                            .append(Const.LINE_SEPARATOR).append("充装介质:")
                            .append(barMsg.getResult().get(0).getMediumName()).append(Const.LINE_SEPARATOR)
                            .append("出厂日期:").append(barMsg.getResult().get(0).getpDate()).append(Const.LINE_SEPARATOR)
                            .append("上检日期:").append(barMsg.getResult().get(0).getfDate()).append(Const.LINE_SEPARATOR)
                            .append("检验周期:").append(barMsg.getResult().get(0).getJyzq() + "年")
                            .append(Const.LINE_SEPARATOR).append("报废年限:").append(barMsg.getResult().get(0).getBf())
                            .append(Const.LINE_SEPARATOR).append("下检日期:").append(barMsg.getResult().get(0).getXjrq())
                            .append(Const.LINE_SEPARATOR).append("报废日期:").append(barMsg.getResult().get(0).getBfrq())
                            .append(Const.LINE_SEPARATOR);
                    switch (barMsg.getResult().get(0).getStatus()) {
                        case 0:
                            sengMsg.append("气瓶当前状态:").append("正常");
                            break;
                        case 1:
                            sengMsg.append("气瓶当前状态:").append("过期");
                            break;
                        case 2:
                            sengMsg.append("气瓶当前状态:").append("报废");
                            break;
                    }
                }
            }

        } else if (MenuService.QP_GZJL.equals(eventKey)) {
            String response = GasHttpClientUtil.gasPost("ccstWeChatBarcodegetBottleFill.htm", params,
                    CharEncoding.UTF_8, fromUserName,scanResult);
            if (SocketFailCode.ERR_CODE_LENGTH == response.length()) {
                sengMsg.append("系统请求socket出现异常:").append(response);
            } else {
                BarcodegetBottleFillResMsg barMsg = new BarcodegetBottleFillResMsg();
                JSONObject object = JSONObject.fromObject(response);
                if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
                    sengMsg.append("系统请求socket出现异常:").append(object.get(WebSocketResFiled.ERROR_CODE));
                } else {
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setRootClass(BarcodegetBottleFillResMsg.class);
                    Map<String, Class> classMap = new HashMap<String, Class>();
                    classMap.put("result", BarcodegetBottleFillResult.class);
                    jsonConfig.setClassMap(classMap);
                    barMsg = (BarcodegetBottleFillResMsg) JSONObject.toBean(object, jsonConfig);
                    sengMsg.append("气瓶编号 :").append(barMsg.getResult().get(0).getPid()).append(Const.LINE_SEPARATOR)
                            .append("气瓶制造单位:").append(barMsg.getResult().get(0).getpCode()).append(Const.LINE_SEPARATOR)
                            .append("最后一次充装日期:").append(barMsg.getResult().get(0).getCheckDatetimeStart())
                            .append(Const.LINE_SEPARATOR).append("充装时间:").append(barMsg.getResult().get(0).getCzTime())
                            .append(Const.LINE_SEPARATOR).append("气瓶型号:")
                            .append(barMsg.getResult().get(0).getTypeName()).append(Const.LINE_SEPARATOR).append("充装量:")
                            .append(barMsg.getResult().get(0).getFillWeight()).append(Const.LINE_SEPARATOR)
                            .append("充装单位:").append(barMsg.getResult().get(0).getRname()).append(Const.LINE_SEPARATOR)
                            .append("充装单位许可证号:").append(barMsg.getResult().get(0).getQpczLicbh())
                            .append(Const.LINE_SEPARATOR).append("充装工:").append(barMsg.getResult().get(0).getWorkNum())
                            .append(Const.LINE_SEPARATOR);
                    if (barMsg.getResult().size() > 1) {
                        sengMsg.append("最后二次充装日期:").append(barMsg.getResult().get(1).getCheckDatetimeStart())
                                .append(Const.LINE_SEPARATOR).append("充装时间:")
                                .append(barMsg.getResult().get(1).getCzTime()).append(Const.LINE_SEPARATOR)
                                .append("气瓶型号:").append(barMsg.getResult().get(1).getTypeName())
                                .append(Const.LINE_SEPARATOR).append("充装量:")
                                .append(barMsg.getResult().get(1).getFillWeight()).append(Const.LINE_SEPARATOR)
                                .append("充装单位:").append(barMsg.getResult().get(1).getRname())
                                .append(Const.LINE_SEPARATOR).append("充装单位许可证号:")
                                .append(barMsg.getResult().get(1).getQpczLicbh()).append(Const.LINE_SEPARATOR)
                                .append("充装工:").append(barMsg.getResult().get(1).getWorkNum());
                    }
                }
            }

        } else if (MenuService.QP_LZGZ.equals(eventKey)) {

            String response = GasHttpClientUtil.gasPost("ccstWeChatBarcodegetBottlePost.htm", params,
                    CharEncoding.UTF_8, fromUserName,scanResult);
            if (SocketFailCode.ERR_CODE_LENGTH == response.length()) {
                sengMsg.append("系统请求socket出现异常:").append(response);
            } else {
                BarcodegetBottlePostResMsg barMsg = new BarcodegetBottlePostResMsg();
                JSONObject object = JSONObject.fromObject(response);
                if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
                    sengMsg.append("系统请求socket出现异常:").append(object.get(WebSocketResFiled.ERROR_CODE));
                } else {
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setRootClass(BarcodegetBottlePostResMsg.class);
                    Map<String, Class> classMap = new HashMap<String, Class>();
                    classMap.put("result", BarcodegetBottlePostResult.class);
                    jsonConfig.setClassMap(classMap);
                    barMsg = (BarcodegetBottlePostResMsg) JSONObject.toBean(object, jsonConfig);
                    sengMsg.append("气瓶编号 :").append(barMsg.getResult().get(0).getPid()).append(Const.LINE_SEPARATOR)
                            .append("气瓶制造单位:").append(barMsg.getResult().get(0).getpCode())
                            .append(Const.LINE_SEPARATOR);
                    List<BarcodegetBottlePostResult> results = barMsg.getResult();
                    for (BarcodegetBottlePostResult result : results) {
                        sengMsg.append("配送日期:").append(result.getPsStart()).append(Const.LINE_SEPARATOR).append("配送单位:")
                                .append(result.getUnitName()).append(Const.LINE_SEPARATOR).append("用户:")
                                .append(result.getUserName()).append(Const.LINE_SEPARATOR).append("用户位置:")
                                .append(result.getUserinfo()).append(Const.LINE_SEPARATOR).append("灌装量:")
                                .append(result.getFillWeight()).append(Const.LINE_SEPARATOR);
                    }
                }
            }
        } else if (MenuService.QP_SMSY.equals(eventKey)) {

            Map<String, String> params_SMSY = new HashMap<String, String>();
            params_SMSY.put("syzbh", messArray[0]);
            params_SMSY.put("zcdm", messArray[1]);
            params_SMSY.put("pcode", messArray[2]);
            params_SMSY.put("pid", messArray[3]);
            params_SMSY.put("pDate", messArray[4]);
            params_SMSY.put("bfrq", messArray[5]);
            params_SMSY.put("openId", fromUserName);

            String response = GasHttpClientUtil.gasPost("ccstWeChatBarcodeUPBottleInfo.htm", params_SMSY,
                    CharEncoding.UTF_8, fromUserName,scanResult);
            if (SocketFailCode.ERR_CODE_LENGTH == response.length()) {
                sengMsg.append("系统请求socket出现异常:").append(response);
            } else {
            	
                JSONObject object = JSONObject.fromObject(response);
               
                if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
                    sengMsg.append("系统请求socket出现异常:").append(object.get(WebSocketResFiled.ERROR_CODE));
                } else {
                	BarcodeUPBottleInfoResMsg 	barMsg = new BarcodeUPBottleInfoResMsg();
                	barMsg = (BarcodeUPBottleInfoResMsg) JSONObject.toBean(JSONObject.fromObject(response), BarcodeUPBottleInfoResMsg.class);
                    sengMsg.append("气瓶编号 :").append(barMsg.getResult().getPid()).append(Const.LINE_SEPARATOR).append("气瓶制造单位代号 :")
                            .append(barMsg.getResult().getPcode()).append(Const.LINE_SEPARATOR).append("气瓶使用登记证编号:").append(barMsg.getResult().getSyzbh())
                            .append(Const.LINE_SEPARATOR).append("气瓶使用登记代码 :").append(barMsg.getResult().getZcdm())
                            .append(Const.LINE_SEPARATOR).append("出厂日期:").append(barMsg.getResult().getPdate()).append(Const.LINE_SEPARATOR)
                            .append("报废日期:").append( messArray[5]).append(Const.LINE_SEPARATOR).append("操作日期时间:")
                            .append(barMsg.getResult().getOpdateTime()).append(Const.LINE_SEPARATOR)
                        	.append("send_lat:").append(Const.LINE_SEPARATOR)
                        	.append("send_lng:").append(Const.LINE_SEPARATOR)
                        	.append("用户编号:").append(Const.LINE_SEPARATOR)
                            .append("用户的唯一标识openid:").append(fromUserName);
                }
            }
        } else if (MenuService.FW_RSQP.equals(eventKey)) {
            String response = GasHttpClientUtil.gasPost("ccstWeChatGetGasQPReadingURL.htm", params,
                    CharEncoding.UTF_8, fromUserName,scanResult);
            if (SocketFailCode.ERR_CODE_LENGTH == response.length()) {
                sengMsg.append("系统请求socket出现异常:").append(response);
            } else {
                JSONObject object = JSONObject.fromObject(response);
                if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
                    sengMsg.append("系统请求socket出现异常:").append(object.get(WebSocketResFiled.ERROR_CODE));
                } else {
                    GasQPReadingURLResMsg messageObject = new GasQPReadingURLResMsg();
                    messageObject = (GasQPReadingURLResMsg) JSONObject.toBean(JSONObject.fromObject(response), GasQPReadingURLResMsg.class);
                    sengMsg.append("请点击下面的链接进入我们的网站 :").append(Const.LINE_SEPARATOR);
                    sengMsg.append(messageObject.getResult().getGasUrl()).append(Const.LINE_SEPARATOR);
                }
            }

        } else if (MenuService.FW_YQAQ.equals(eventKey)) {
            String response = GasHttpClientUtil.gasPost("ccstWeChatGetGasQPReadingURL.htm", params,
                    CharEncoding.UTF_8, fromUserName,scanResult);
            if (SocketFailCode.ERR_CODE_LENGTH == response.length()) {
                sengMsg.append("系统请求socket出现异常:").append(response);
            } else {
                GasQPReadingURLResMsg barMsg = new GasQPReadingURLResMsg();
                JSONObject object = JSONObject.fromObject(response);
                if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
                    sengMsg.append("系统请求socket出现异常:").append(object.get(WebSocketResFiled.ERROR_CODE));
                } else {
                    GasQPReadingURLResMsg messageObject = new GasQPReadingURLResMsg();
                    messageObject = (GasQPReadingURLResMsg) JSONObject.toBean(JSONObject.fromObject(message), GasQPReadingURLResMsg.class);
                    sengMsg.append("请点击下面的链接进入我们的网站 :").append(Const.LINE_SEPARATOR);
                    sengMsg.append(messageObject.getResult().getGasUrl()).append(Const.LINE_SEPARATOR);
                }
            }
        } else if (MenuService.QP_AQDW.equals(eventKey)) {

            Map<String, String> params_AQDW = new HashMap<String, String>();
            params_AQDW.put("pCode", messArray[2]);
            params_AQDW.put("pid", messArray[3]);
            params_AQDW.put("pDate", messArray[4]);


            String response = GasHttpClientUtil.gasPost("ccstWeChatBarcodegetBottlePsafe.htm", params_AQDW,
                    CharEncoding.UTF_8, fromUserName,scanResult);
            BarcodegetBottlePsafeResMsg messageObject = new BarcodegetBottlePsafeResMsg();
            JSONObject object = JSONObject.fromObject(response);
            if (SocketFailCode.ERR_CODE_LENGTH == response.length()) {
                sengMsg.append("系统请求socket出现异常:").append(response);
            } else {
                if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
                    sengMsg.append("系统请求socket出现异常:").append(object.get(WebSocketResFiled.ERROR_CODE));
                } else {
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setRootClass(BarcodegetBottlePsafeResMsg.class);
                    Map<String, Class> classMap = new HashMap<String, Class>();
                    classMap.put("result", BarcodegetBottlePsafeResult.class);
                    jsonConfig.setClassMap(classMap);
                    messageObject = (BarcodegetBottlePsafeResMsg) JSONObject.toBean(object, jsonConfig);
                    sengMsg.append("气瓶制造单位代号:").append(messageObject.getResult().get(0).getpCode())
                            .append(Const.LINE_SEPARATOR).append("气瓶使用证编号 :")
                            .append(messageObject.getResult().get(0).getSyzbh()).append(Const.LINE_SEPARATOR)
                            .append("气瓶注册代码:").append(messageObject.getResult().get(0).getZcdm())
                            .append(Const.LINE_SEPARATOR).append("气瓶结构:")
                            .append(messageObject.getResult().get(0).getQpStructureName()).append(Const.LINE_SEPARATOR)
                            .append("气瓶品种:").append(messageObject.getResult().get(0).getClassName())
                            .append(Const.LINE_SEPARATOR).append("气瓶型号:")
                            .append(messageObject.getResult().get(0).getTypeName()).append(Const.LINE_SEPARATOR)
                            .append("气瓶编号:").append(messageObject.getResult().get(0).getPid()).append(Const.LINE_SEPARATOR)
                            .append("充装介质:").append(messageObject.getResult().get(0).getMediumName())
                            .append(Const.LINE_SEPARATOR).append("出厂日期:")
                            .append(messageObject.getResult().get(0).getpDate()).append(Const.LINE_SEPARATOR)
                            .append("上检日期:").append(messageObject.getResult().get(0).getfDate())
                            .append(Const.LINE_SEPARATOR).append("下检日期:").append(messageObject.getResult().get(0).getXjrq())
                            .append(Const.LINE_SEPARATOR).append("报废日期:").append(messageObject.getResult().get(0).getBfrq())
                            .append(Const.LINE_SEPARATOR).append(" 用户:")
                            .append(messageObject.getResult().get(0).getUserName()).append(Const.LINE_SEPARATOR)
                            .append("用户位置:").append(messageObject.getResult().get(0).getUserInfo())
                            .append(Const.LINE_SEPARATOR).append("用户联系方式:")
                            .append(messageObject.getResult().get(0).getPhone());
                }
            }
        }
        sengMsg.append(Const.LINE_SEPARATOR)
		       .append(Const.WECHART_NAME)
		       .append(" ")
		       .append(Const.WECHART_DOMAIN);
        logger.info("setContent:" + sengMsg.toString());
        message.setContent(sengMsg.toString());
        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);

        return respXml;
    }
}
