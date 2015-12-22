/**
 *
 */
package com.fujitsu.base.constants;

import com.fujitsu.base.helper.ConfigUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author Barrie
 */
public class Const {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

    public static final String PATH_SEPARATOR = File.separator;

    /**
     * config file location
     */
    private static final String GLOBAL_CONF = "api/global.properties";
    /**
     * global config
     */

    public static final String WECHART_NAME = ConfigUtil.getProperty(GLOBAL_CONF, "wechat.name");
    public static final String WECHART_DOMAIN = ConfigUtil.getProperty(GLOBAL_CONF, "wechat.domain");
    public static final String WECHART_URL = ConfigUtil.getProperty(GLOBAL_CONF, "wechat.url");
    public static final String WECHART_APP_ID = ConfigUtil.getProperty(GLOBAL_CONF, "wechat.appid");
    public static final String WECHART_APP_SECRET = ConfigUtil.getProperty(GLOBAL_CONF, "wechat.appsecret");
    public static final String WECHART_TOKEN = ConfigUtil.getProperty(GLOBAL_CONF, "wechat.token");
    public static final String WECHART_CACHE_PATH = ConfigUtil.getProperty(GLOBAL_CONF, "wechat.cachepath");
    private static final String PUBLICS_CONF = "api/publics.properties";
    private static final String MERCHANT_CONF = "api/merchant.properties";
    private static final String WEBSOCKET_CONF = "api/webSocket.properties";
    private static final String ACTIVEMQ_CONF = "api/activemq.properties";
    private static final String GASAPI_CONF = "api/gasApi.properties";

    public static String getServerPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource(Const.PATH_SEPARATOR).getPath();
        path = Const.PATH_SEPARATOR + path.substring(1, path.indexOf(Const.PATH_SEPARATOR + "classes")) + Const.PATH_SEPARATOR;
        return path;
    }

    public static String getServerUrl(HttpServletRequest request) {
        String path = request.getContextPath() + "/";
        int port = request.getServerPort();
        String basePath = null;
        if (80 == port) {
            basePath = request.getScheme() + "://" + request.getServerName() + path;
        } else {
            basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        }

        return basePath;
    }

    public static class Queue {
        /**
         * Message Queue config
         */
        public static final String ACTIVEMQ_HOST = ConfigUtil.getProperty(ACTIVEMQ_CONF, "activemq.host");

        public static final String ACTIVEMQ_PORT = ConfigUtil.getProperty(ACTIVEMQ_CONF, "activemq.port");

        public static final String ACTIVEMQ_USER_NAME = ConfigUtil.getProperty(ACTIVEMQ_CONF, "activemq.username");

        public static final String ACTIVEMQ_PASSWORD = ConfigUtil.getProperty(ACTIVEMQ_CONF, "activemq.password");

        public static final String ACTIVEMQ_PROTOCAL_QUEUE = "queue://";

        public static final String ACTIVEMQ_PROTOCAL_TOPIC = "topic://";

        public static final String ACTIVEMQ_QUEUE_USER_PREFIX = "USER.";

        public static final String ACTIVEMQ_QUEUE_SYSTEM_PREFIX = "SYS.";

        public static final String ACTIVEMQ_RECEIVE_TIMEOUT = ConfigUtil.getProperty(ACTIVEMQ_CONF, "activemq.receive.timeout");

        public static final String ACTIVEMQ_MSG_TIMETOLIVE = ConfigUtil.getProperty(ACTIVEMQ_CONF, "activemq.msg.timetolive");

    }

    public static class PublicPlatform {
        /**
         * public platform API
         */
        public static final String URL_GET_CALLBACK_IP = ConfigUtil.getProperty(PUBLICS_CONF, "url.get.callbackip");

        public static final String URL_JSAPI_TICKET = ConfigUtil.getProperty(PUBLICS_CONF, "url.jsapi.ticket");

        public static final String URL_GET_ACCESS_TOKEN = ConfigUtil.getProperty(PUBLICS_CONF, "url.access.token");

        public static final String URL_MENU_CREATE_DEFAULT = ConfigUtil.getProperty(PUBLICS_CONF, "url.menu.create.default");

        public static final String URL_MENU_CREATE_CONDITION = ConfigUtil.getProperty(PUBLICS_CONF, "url.menu.create.condition");

        public static final String URL_MENU_GET = ConfigUtil.getProperty(PUBLICS_CONF, "url.menu.get");

        public static final String URL_MENU_DELETE_DEFAULT = ConfigUtil.getProperty(PUBLICS_CONF, "url.menu.delete.default");

        public static final String URL_MENU_DELETE_CONDITION = ConfigUtil.getProperty(PUBLICS_CONF, "url.menu.delete.condition");

        public static final String URL_MENU_TEST = ConfigUtil.getProperty(PUBLICS_CONF, "url.menu.test");

        public static final String URL_SNS_OAUTH2_REDIRECT = ConfigUtil.getProperty(PUBLICS_CONF, "url.sns.oauth2.redirect");

        public static final String URL_SNS_OAUTH2_TOKEN_GET = ConfigUtil.getProperty(PUBLICS_CONF, "url.sns.oauth2.token.get");

        public static final String URL_SNS_OAUTH2_TOKEN_REFRESH = ConfigUtil.getProperty(PUBLICS_CONF, "url.sns.oauth2.token.refresh");

        public static final String URL_USER_GET_SNS_INFO = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.get.sns.info");

        public static final String URL_USER_GET_INFO = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.get.info");

        public static final String URL_USER_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.get.list");

        public static final String URL_USER_GROUP_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.group.get.list");

        public static final String URL_USER_GROUP_GET_BY_OPENID = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.group.get.by.openid");

        public static final String URL_USER_GROUP_RENAME = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.group.rename");

        public static final String URL_USER_GROUP_UPDATE = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.group.update");

        public static final String URL_USER_GROUP_BATCH_UPDATE = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.group.batchupdate");

        public static final String URL_USER_GROUP_DELETE = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.group.delete");

        public static final String URL_MATERIAL_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.material.get.list");

        public static final String URL_MATERIAL_GET_DETAIL = ConfigUtil.getProperty(PUBLICS_CONF, "url.material.get.detail");

        public static final String URL_SHOP_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.shop.get.list");

        public static final String URL_SHOP_GET_DETAIL = ConfigUtil.getProperty(PUBLICS_CONF, "url.shop.get.detail");

        public static final String URL_PRODUCT_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.product.get.list");

        public static final String URL_PRODUCT_GET_DETAIL = ConfigUtil.getProperty(PUBLICS_CONF, "url.product.get.detail");

        public static final String URL_PRODUCT_GROUP_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.product.group.get.list");

        public static final String URL_PRODUCT_GROUP_GET_DETAIL = ConfigUtil.getProperty(PUBLICS_CONF, "url.product.group.get.detail");

        public static final String URL_ORDER_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.order.get.list");

        public static final String URL_ORDER_GET_DETAIL = ConfigUtil.getProperty(PUBLICS_CONF, "url.order.get.detail");

        public static final String URL_CUSTOMER_SERVICE_MESSAGE_SEND = ConfigUtil.getProperty(PUBLICS_CONF, "url.customer.service.message.send");

        public static final String URL_CUSTOMER_SERVICE_KF_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.customer.service.kf.list");

        public static final String URL_CUSTOMER_SERVICE_KF_LIST_ONLINE = ConfigUtil.getProperty(PUBLICS_CONF, "url.customer.service.kf.listOnline");

        public static final String URL_CUSTOMER_SERVICE_KF_ADD = ConfigUtil.getProperty(PUBLICS_CONF, "url.customer.service.kf.add");

        public static final String URL_CUSTOMER_SERVICE_KF_UPDATE = ConfigUtil.getProperty(PUBLICS_CONF, "url.customer.service.kf.update");

        public static final String URL_CUSTOMER_SERVICE_KF_DELETE = ConfigUtil.getProperty(PUBLICS_CONF, "url.customer.service.kf.delete");

        public static final String URL_CUSTOMER_SERVICE_KF_UPLOAD_HEAD_IMG = ConfigUtil.getProperty(PUBLICS_CONF, "url.customer.service.kf.uploadHeadImg");

        public static final String URL_MEDIA_GET = ConfigUtil.getProperty(PUBLICS_CONF, "url.media.get");

        public static final String URL_MEDIA_UPLOAD = ConfigUtil.getProperty(PUBLICS_CONF, "url.media.upload");

        public static final String URL_MEDIA_UPLOADIMG = ConfigUtil.getProperty(PUBLICS_CONF, "url.media.uploadimg");
    }
    
    public static class MerchantPlatform {
        /**
         * merchant platform API
         */

        public static final String MCH_ID = ConfigUtil.getProperty(MERCHANT_CONF, "mchId");

        public static final String MCH_SECRET = ConfigUtil.getProperty(MERCHANT_CONF, "mchSecret");

        public static final String MCH_KEYSTONE = ConfigUtil.getProperty(MERCHANT_CONF, "mchKeystone");

        public static final String MCH_KEYSTONE_SECRET = ConfigUtil.getProperty(MERCHANT_CONF, "mchKeystoneSecret");

        public static final String URL_MERCHANT_COUPON_SEND = ConfigUtil.getProperty(MERCHANT_CONF, "url.merchant.coupon.send");

        public static final String URL_MERCHANT_COUPON_GET_DETAIL = ConfigUtil.getProperty(MERCHANT_CONF, "url.merchant.coupon.get.detail");

        public static final String URL_MERCHANT_REDPACK_SEND = ConfigUtil.getProperty(MERCHANT_CONF, "url.merchant.redpack.send");

        public static final String URL_MERCHANT_PAY_REFUND = ConfigUtil.getProperty(MERCHANT_CONF, "url.merchant.pay.refund");

    }

    public static class WebSocket {

        public static final long WEB_SOCKET_SLEEP = 800;

        public static final String AUTHORIZEID = ConfigUtil.getProperty(WEBSOCKET_CONF, "authorizeID");

        public static final String AUTHORIZETYPE = ConfigUtil.getProperty(WEBSOCKET_CONF, "authorizeType");

        public static final String URL = ConfigUtil.getProperty(WEBSOCKET_CONF, "URL");

    }

    public static class gasApi {

        public static final String AUTHORIZETYPE = ConfigUtil.getProperty(GASAPI_CONF, "authorizeType");

        public static final String URL = ConfigUtil.getProperty(GASAPI_CONF, "URL");

    }
}
