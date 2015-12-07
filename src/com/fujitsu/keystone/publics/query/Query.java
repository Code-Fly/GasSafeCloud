/**
 *
 */
package com.fujitsu.keystone.publics.query;

import com.fujitsu.base.constants.Const;
import com.fujitsu.keystone.publics.event.Event;
import com.fujitsu.queue.service.impl.ActiveMQService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Barrie
 */
public abstract class Query {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static final String FILLING_STORAGE = "A";

    public static final String DISTRIBUTION_TRANSPORTATION = "B";

    public static final String INSPECTION_TESTING = "C";

    public static final Map<String, String> QUERY_CMD_TYPE = new HashMap<String, String>() {
        {
            put(FILLING_STORAGE, "A");
            put(DISTRIBUTION_TRANSPORTATION, "B");
            put(INSPECTION_TESTING, "C");
        }
    };

    public static final String CUSTOMER_SERVICE = "KF";

    public static final String QUERY_LIST = "A";

    public static final String QUERY_DETAIL = "B";

    public static final String SEPARATOR = "#";

    public String execute(HttpServletRequest request, JSONObject requestJson) throws JMSException {
        if (null != Const.Queue.ACTIVEMQ_HOST) {
            String fromUserName = requestJson.getString(Event.FROM_USER_NAME);
            ActiveMQService mq = new ActiveMQService();
            mq.connect();
            mq.sendText("queue://" + fromUserName, requestJson.toString());
            mq.close();
        }
        return null;
    }
}
