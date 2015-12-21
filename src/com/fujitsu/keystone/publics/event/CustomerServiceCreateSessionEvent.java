/**
 *
 */
package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.entity.customer.message.Text;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;
import com.fujitsu.keystone.publics.service.impl.CustomerService;
import net.sf.json.JSONObject;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author Barrie
 */
public class CustomerServiceCreateSessionEvent extends Event {
    public static String KF_ACCOUNT = "KfAccount";

    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException, AccessTokenException, WeChatException, JMSException, UnsupportedEncodingException, GasSafeException {
        super.execute(request, requestJson);

        String at = KeystoneUtil.getAccessToken();

        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(FROM_USER_NAME);

        String kfAccount = requestJson.getString(KF_ACCOUNT);

        TextMessage customerMsg = new TextMessage();
        customerMsg.setMsgtype(CustomerService.CUSTOMER_SERVICE_MESSAGE_TYPE_TEXT);
        customerMsg.setTouser(fromUserName);
        Text t = new Text();
        StringBuffer buffer = new StringBuffer();
        buffer.append(kfAccount + " 正在为您服务。").append(Const.LINE_SEPARATOR);
        t.setContent(buffer.toString());
        customerMsg.setText(t);
        new CustomerService().sendTextMessage(customerMsg);

        return respXml;
    }
}
