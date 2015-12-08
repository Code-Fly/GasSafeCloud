package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.client.*;
import com.fujitsu.client.entity.*;
import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.GasHttpClientUtil;
import com.fujitsu.base.helper.GasWebSocketUtil;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MenuService;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import net.sf.json.JSONObject;

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
     */
    public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException, AccessTokenException, WeChatException, JMSException {
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
        int lastIndex = scanResult.lastIndexOf("]");
        //QP02001,132020000001,AG,323232,2015年09月,2045年09月
        String tmp = scanResult.substring(1, lastIndex);
        // [QP02001,132020000001,AG,323232,2015年09月,2045年09月]
        String[] messArray = tmp.split(",");
        Map<String, String> params = new HashMap<String,String>();
        params.put("syzbh", messArray[0]);
        params.put("zcdm", messArray[1]);
        params.put("pcode", messArray[2]);
        params.put("pid", messArray[3]);
        params.put("pDate", messArray[4]);
        params.put("bfrq", messArray[5]);
       
        StringBuffer sengMsg = new StringBuffer();
        try {
        // 身份查询
        if (MenuService.QP_SFCX.equals(eventKey)) {
            StringBuffer socketParams = new StringBuffer();
            socketParams.append("syzbh=").append(messArray[0])
                    .append("&zcdm=").append(messArray[1])
                    .append("&token=").append(GasWebSocketClient.SOCKET_TOKEN)
                    .append("&pcode=").append(messArray[2])
                    .append("&pid=").append(messArray[3])
                    .append("&pDate=").append(messArray[4])
                    .append("&bfrq=").append(messArray[5]);
				GasHttpClientUtil.post("ccstWeChatBarcodegetBottle.htm", params, "UTF-8", fromUserName);
			
            BarcodegetBottleResMsg barMsg = getBarResMsg(socketParams.toString(), 0);
            if (0 == barMsg.getErrorCode()) {
                sengMsg.append("气瓶使用证编号:").append(barMsg.getResult().get(0).getSyzbh()).append(Const.LINE_SEPARATOR)
                        .append("气瓶注册代码:").append(barMsg.getResult().get(0).getZcdm()).append(Const.LINE_SEPARATOR)
                        .append("单位自有编号:").append(barMsg.getResult().get(0).getZybh()).append(Const.LINE_SEPARATOR)
                        .append("气瓶充装单位编号:").append(barMsg.getResult().get(0).getRno()).append(Const.LINE_SEPARATOR)
                        .append("气瓶制造单位代号:").append(messArray[2]).append(Const.LINE_SEPARATOR)
                        .append("气瓶品种:").append(barMsg.getResult().get(0).getClassName()).append(Const.LINE_SEPARATOR)
                        .append("气瓶型号:").append(barMsg.getResult().get(0).getTypeName()).append(Const.LINE_SEPARATOR)
                        .append("气瓶编号:").append(barMsg.getResult().get(0).getPid()).append(Const.LINE_SEPARATOR)
                        .append("充装介质:").append(barMsg.getResult().get(0).getMediumName()).append(Const.LINE_SEPARATOR)
                        .append("出厂日期:").append(barMsg.getResult().get(0).getpDate()).append(Const.LINE_SEPARATOR)
                        .append("上检日期:").append(barMsg.getResult().get(0).getfDate()).append(Const.LINE_SEPARATOR)
                        .append("检验周期:").append(barMsg.getResult().get(0).getJyzq() + "年").append(Const.LINE_SEPARATOR)
                        .append("报废年限:").append(barMsg.getResult().get(0).getBf()).append(Const.LINE_SEPARATOR)
                        .append("下检日期:").append(barMsg.getResult().get(0).getXjrq()).append(Const.LINE_SEPARATOR)
                        .append("报废日期:").append(barMsg.getResult().get(0).getBfrq()).append(Const.LINE_SEPARATOR);
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

            } else {
                sengMsg.append("系统请求socket出现异常:").append(barMsg.getErrorCode());
            }
        } else if (MenuService.QP_GZJL.equals(eventKey)) {
            StringBuffer socketParams = new StringBuffer();
            socketParams.append("syzbh=").append(messArray[0])
                    .append("&zcdm=").append(messArray[1])
                    .append("&token=").append(GasWebSocketClient.SOCKET_TOKEN)
                    .append("&pcode=").append(messArray[2])
                    .append("&pid=").append(messArray[3])
                    .append("&pDate=").append(messArray[4])
                    .append("&bfrq=").append(messArray[5]);
            BarcodegetBottleFillResMsg barMsg = getBottleResMsg(socketParams.toString(), 0);
            if (0 == barMsg.getErrorCode()) {
                sengMsg.append("气瓶编号 :").append(barMsg.getResult().get(0).getPid()).append(Const.LINE_SEPARATOR)
                        .append("气瓶制造单位:").append(barMsg.getResult().get(0).getpCode()).append(Const.LINE_SEPARATOR)
                        .append("最后一次充装日期:").append(barMsg.getResult().get(0).getCheckDatetimeStart()).append(Const.LINE_SEPARATOR)
                        .append("充装时间:").append(barMsg.getResult().get(0).getCzTime()).append(Const.LINE_SEPARATOR)
                        .append("气瓶型号:").append(barMsg.getResult().get(0).getTypeName()).append(Const.LINE_SEPARATOR)
                        .append("充装量:").append(barMsg.getResult().get(0).getFillWeight()).append(Const.LINE_SEPARATOR)
                        .append("充装单位:").append(barMsg.getResult().get(0).getRname()).append(Const.LINE_SEPARATOR)
                        .append("充装单位许可证号:").append(barMsg.getResult().get(0).getQpczLicbh()).append(Const.LINE_SEPARATOR)
                        .append("充装工:").append(barMsg.getResult().get(0).getWorkNum()).append(Const.LINE_SEPARATOR);
                if (barMsg.getResult().size() > 1) {
                    sengMsg.append("最后二次充装日期:").append(barMsg.getResult().get(1).getCheckDatetimeStart()).append(Const.LINE_SEPARATOR)
                            .append("充装时间:").append(barMsg.getResult().get(1).getCzTime()).append(Const.LINE_SEPARATOR)
                            .append("气瓶型号:").append(barMsg.getResult().get(1).getTypeName()).append(Const.LINE_SEPARATOR)
                            .append("充装量:").append(barMsg.getResult().get(1).getFillWeight()).append(Const.LINE_SEPARATOR)
                            .append("充装单位:").append(barMsg.getResult().get(1).getRname()).append(Const.LINE_SEPARATOR)
                            .append("充装单位许可证号:").append(barMsg.getResult().get(1).getQpczLicbh()).append(Const.LINE_SEPARATOR)
                            .append("充装工:").append(barMsg.getResult().get(1).getWorkNum());
                }


            } else {
                sengMsg.append("系统请求socket出现异常:").append(barMsg.getErrorCode());
            }
        } else if (MenuService.QP_LZGZ.equals(eventKey)) {
            StringBuffer socketParams = new StringBuffer();
            socketParams.append("syzbh=").append(messArray[0])
                    .append("&zcdm=").append(messArray[1])
                    .append("&token=").append(GasWebSocketClient.SOCKET_TOKEN)
                    .append("&pcode=").append(messArray[2])
                    .append("&pid=").append(messArray[3])
                    .append("&pDate=").append(messArray[4])
                    .append("&bfrq=").append(messArray[5]);
            BarcodegetBottlePostResMsg barMsg = getBottlePostResMsg(socketParams.toString(), 0);
            if (0 == barMsg.getErrorCode()) {
                sengMsg.append("气瓶编号 :").append(barMsg.getResult().get(0).getPid()).append(Const.LINE_SEPARATOR)
                        .append("气瓶制造单位:").append(barMsg.getResult().get(0).getpCode()).append(Const.LINE_SEPARATOR);
                List<BarcodegetBottlePostResult> results = barMsg.getResult();
                for (BarcodegetBottlePostResult result : results) {
                    sengMsg.append("配送日期:").append(result.getPsStart()).append(Const.LINE_SEPARATOR)
                            .append("配送单位:").append(result.getUnitName()).append(Const.LINE_SEPARATOR)
                            .append("用户:").append(result.getUserName()).append(Const.LINE_SEPARATOR)
                            .append("用户位置:").append(result.getUserinfo()).append(Const.LINE_SEPARATOR)
                            .append("灌装量:").append(result.getFillWeight()).append(Const.LINE_SEPARATOR);
                }
            } else {
                sengMsg.append("系统请求socket出现异常:").append(barMsg.getErrorCode());
            }
        } else if (MenuService.QP_SMSY.equals(eventKey)) {
            StringBuffer socketParams = new StringBuffer();
            socketParams.append("syzbh=").append(messArray[0])
                    .append("&zcdm=").append(messArray[1])
                    .append("&token=").append(GasWebSocketClient.SOCKET_TOKEN)
                    .append("&pcode=").append(messArray[2])
                    .append("&pid=").append(messArray[3])
                    .append("&pDate=").append(messArray[4])
                    .append("&bfrq=").append(messArray[5])
                    .append("&openId=").append(fromUserName);
            WebSocketResponseMessage messageObject = getBarcodeUPBottleInfoResMsg(socketParams.toString(), 0);
            if (0 == messageObject.getErrorCode()) {
                sengMsg.append("气瓶编号 :").append(messArray[3]).append(Const.LINE_SEPARATOR)
                        .append("气瓶制造单位代号 :").append(messArray[2]).append(Const.LINE_SEPARATOR)
                        .append("气瓶使用登记证编号:").append(messArray[0]).append(Const.LINE_SEPARATOR)
                        .append("气瓶使用登记代码 :").append(messArray[1]).append(Const.LINE_SEPARATOR)
                        .append("出厂日期:").append(messArray[3]).append(Const.LINE_SEPARATOR)
                        .append("报废日期:").append(messArray[4]).append(Const.LINE_SEPARATOR)
                        .append("操作日期时间:").append(messArray[5]).append(Const.LINE_SEPARATOR)
                        .append("用户的唯一标识openid:").append(fromUserName);
            } else {
                sengMsg.append("系统请求socket出现异常:").append(messageObject.getErrorCode());
            }
        } else if (MenuService.FW_RSQP.equals(eventKey)) {
            StringBuffer socketParams = new StringBuffer();
            socketParams.append("syzbh=").append(messArray[0])
                    .append("&zcdm=").append(messArray[1])
                    .append("&token=").append(GasWebSocketClient.SOCKET_TOKEN)
                    .append("&pcode=").append(messArray[2])
                    .append("&pid=").append(messArray[3])
                    .append("&pDate=").append(messArray[4])
                    .append("&bfrq=").append(messArray[5]);
            GasQPReadingURLResMsg messageObject = getGasQPReadingURLResMsg(socketParams.toString(), 0);
            if (0 == messageObject.getErrorCode()) {
                sengMsg.append("请点击下面的链接进入我们的网站 :").append(Const.LINE_SEPARATOR);
                sengMsg.append(messageObject.getResult().getQpUrl()).append(Const.LINE_SEPARATOR);
            } else {
                sengMsg.append("系统请求socket出现异常:").append(messageObject.getErrorCode());
            }

        } else if (MenuService.FW_YQAQ.equals(eventKey)) {
            StringBuffer socketParams = new StringBuffer();
            socketParams.append("syzbh=").append(messArray[0])
                    .append("&zcdm=").append(messArray[1])
                    .append("&token=").append(GasWebSocketClient.SOCKET_TOKEN)
                    .append("&pcode=").append(messArray[2])
                    .append("&pid=").append(messArray[3])
                    .append("&pDate=").append(messArray[4])
                    .append("&bfrq=").append(messArray[5]);
            GasQPReadingURLResMsg messageObject = getGasQPReadingURLResMsg(socketParams.toString(), 0);
            if (0 == messageObject.getErrorCode()) {
                sengMsg.append("请点击下面的链接进入我们的网站 :").append(Const.LINE_SEPARATOR);
                sengMsg.append(messageObject.getResult().getGasUrl()).append(Const.LINE_SEPARATOR);
            } else {
                sengMsg.append("系统请求socket出现异常:").append(messageObject.getErrorCode());
            }
        } else if (MenuService.QP_AQDW.equals(eventKey)) {
            StringBuffer socketParams = new StringBuffer();
            socketParams.append("token=").append(GasWebSocketClient.SOCKET_TOKEN)
                    .append("&pCode=").append(messArray[2])
                    .append("&pid=").append(messArray[3])
                    .append("&pDate=").append(messArray[4]);
            BarcodegetBottlePsafeResMsg messageObject = getGasBarcodegetBottlePsafe(socketParams.toString(), 0);
            if (0 == messageObject.getErrorCode()) {
                sengMsg.append("气瓶制造单位代号:").append(messageObject.getResult().get(0).getpCode()).append(Const.LINE_SEPARATOR)
                        .append("气瓶使用证编号 :").append(messageObject.getResult().get(0).getSyzbh()).append(Const.LINE_SEPARATOR)
                        .append("气瓶注册代码:").append(messageObject.getResult().get(0).getZcdm()).append(Const.LINE_SEPARATOR)
                        .append("气瓶结构:").append(messageObject.getResult().get(0).getQpStructureName()).append(Const.LINE_SEPARATOR)
                        .append("气瓶品种:").append(messageObject.getResult().get(0).getClassName()).append(Const.LINE_SEPARATOR)
                        .append("气瓶型号:").append(messageObject.getResult().get(0).getTypeName()).append(Const.LINE_SEPARATOR)
                        .append("气瓶编号:").append(messageObject.getResult().get(0).getPid()).append(Const.LINE_SEPARATOR)
                        .append("充装介质:").append(messageObject.getResult().get(0).getMediumName()).append(Const.LINE_SEPARATOR)
                        .append("出厂日期:").append(messageObject.getResult().get(0).getpDate()).append(Const.LINE_SEPARATOR)
                        .append("上检日期:").append(messageObject.getResult().get(0).getfDate()).append(Const.LINE_SEPARATOR)
                        .append("下检日期:").append(messageObject.getResult().get(0).getXjrq()).append(Const.LINE_SEPARATOR)
                        .append("报废日期:").append(messageObject.getResult().get(0).getBfrq()).append(Const.LINE_SEPARATOR)
                        .append(" 用户:").append(messageObject.getResult().get(0).getUserName()).append(Const.LINE_SEPARATOR)
                        .append("用户位置:").append(messageObject.getResult().get(0).getUserInfo()).append(Const.LINE_SEPARATOR)
                        .append("用户联系方式:").append(messageObject.getResult().get(0).getPhone());

            } else {
                sengMsg.append("系统请求socket出现异常:").append(messageObject.getErrorCode());
            }
        }
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        logger.info("setContent:" + sengMsg.toString());
        message.setContent(sengMsg.toString());
        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);

        super.execute(request, requestJson);
        return respXml;
    }

    /**
     * 当token过期或者错误时 重新获得token，然后发送请求
     *
     * @param socketParams
     * @param times
     * @return
     */
    private BarcodegetBottleResMsg getBarResMsg(String socketParams, int times) {
        logger.info("getBarResMsg times=" + times);
        Long systemTime = System.currentTimeMillis();
        logger.info("system time :=" + systemTime);
        GasBarcodegetBottleConnect.sendMsg(socketParams.toString());
        logger.info("system time :=" + (System.currentTimeMillis() - systemTime));
        BarcodegetBottleResMsg messageObject = GasBarcodegetBottleClient.messageObject;
        if ((times) < 1 && (SocketFailCode.CODE_100001 == messageObject.getErrorCode()
                || SocketFailCode.CODE_100002 == messageObject.getErrorCode())) {
            logger.info("Bar times=" + times);
            GasWebSocketUtil.accessWSToken();
            getBarResMsg(socketParams, times + 1);
        }
        logger.info("getBarResMsg messageObject=" + messageObject.getMessage());
        return messageObject;
    }

    /**
     * 当token过期或者错误时 重新获得token，然后发送请求
     *
     * @param socketParams
     * @param times
     * @return
     */
    private BarcodegetBottleFillResMsg getBottleResMsg(String socketParams, int times) {
        logger.info("getBottleResMsg times=" + times);
        GasBarcodegetBottleFillConnect.sendMsg(socketParams.toString());
        BarcodegetBottleFillResMsg messageObject = GasBarcodegetBottleFillClient.messageObject;
        if ((times) < 1 && (SocketFailCode.CODE_100001 == messageObject.getErrorCode()
                || SocketFailCode.CODE_100002 == messageObject.getErrorCode())) {
            logger.info("Bottle times=" + times);
            GasWebSocketUtil.accessWSToken();
            getBottleResMsg(socketParams, times + 1);
        }
        logger.info("getBottleResMsg messageObject=" + messageObject.getMessage());
        return messageObject;
    }

    /**
     * 流转跟踪
     * 当token过期或者错误时 重新获得token，然后发送请求
     *
     * @param socketParams
     * @param times
     * @return
     */
    private BarcodegetBottlePostResMsg getBottlePostResMsg(String socketParams, int times) {
        logger.info("getBottlePostResMsg times=" + times);
        GasBarcodegetBottlePostConnect.sendMsg(socketParams.toString());
        BarcodegetBottlePostResMsg messageObject = GasBarcodegetBottlePostClient.messageObject;
        if ((times) < 1 && (SocketFailCode.CODE_100001 == messageObject.getErrorCode()
                || SocketFailCode.CODE_100002 == messageObject.getErrorCode())) {
            logger.info("Bottle times=" + times);
            GasWebSocketUtil.accessWSToken();
            getBottlePostResMsg(socketParams, times + 1);
        }
        logger.info("getBottlePostResMsg messageObject=" + messageObject.getMessage());
        return messageObject;
    }

    /**
     * 登记实名使用
     * 当token过期或者错误时 重新获得token，然后发送请求
     *
     * @param socketParams
     * @param times
     * @return
     */
    private WebSocketResponseMessage getBarcodeUPBottleInfoResMsg(String socketParams, int times) {
        logger.info("getBarcodeUPBottleInfoResMsg times=" + times);
        GasBarcodeUPBottleInfoConnect.sendMsg(socketParams.toString());
        WebSocketResponseMessage messageObject = GasBarcodeUPBottleInfoClient.messageObject;
        if ((times) < 1 && (SocketFailCode.CODE_100001 == messageObject.getErrorCode()
                || SocketFailCode.CODE_100002 == messageObject.getErrorCode())) {
            logger.info("Bottle times=" + times);
            GasWebSocketUtil.accessWSToken();
            getBarcodeUPBottleInfoResMsg(socketParams, times + 1);
        }
        logger.info("getBarcodeUPBottleInfoResMsg messageObject=" + messageObject.getMessage());
        return messageObject;
    }

    private GasQPReadingURLResMsg getGasQPReadingURLResMsg(String socketParams, int times) {
        logger.info("getGasQPReadingURLResMsg times=" + times);
        GasQPReadingURLConnect.sendMsg(socketParams.toString());
        GasQPReadingURLResMsg messageObject = GasQPReadingURLClient.messageObject;
        if ((times) < 1 && (SocketFailCode.CODE_100001 == messageObject.getErrorCode()
                || SocketFailCode.CODE_100002 == messageObject.getErrorCode())) {
            logger.info("Bottle times=" + times);
            GasWebSocketUtil.accessWSToken();
            getGasQPReadingURLResMsg(socketParams, times + 1);
        }
        logger.info("getGasQPReadingURLResMsg messageObject=" + messageObject.getMessage());
        return messageObject;
    }


    private BarcodegetBottlePsafeResMsg getGasBarcodegetBottlePsafe(String socketParams, int times) {
        logger.info("getGasBarcodegetBottlePsafe times=" + times);
        GasBarcodegetBottlePsafeConnect.sendMsg(socketParams.toString());
        BarcodegetBottlePsafeResMsg messageObject = GasBarcodegetBottlePsafeClient.messageObject;
        if ((times) < 1 && (SocketFailCode.CODE_100001 == messageObject.getErrorCode()
                || SocketFailCode.CODE_100002 == messageObject.getErrorCode())) {
            logger.info("Bottle times=" + times);
            GasWebSocketUtil.accessWSToken();
            getGasBarcodegetBottlePsafe(socketParams, times + 1);
        }
        logger.info("getGasBarcodegetBottlePsafe messageObject=" + messageObject.getMessage());
        return messageObject;
    }

}

