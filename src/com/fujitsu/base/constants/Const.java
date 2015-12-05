/**
 *
 */
package com.fujitsu.base.constants;

import com.fujitsu.base.helper.ConfigUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Barrie
 */
public class Const {
	
	
    /**
     * config file location
     */
    private static final String GLOBAL_CONF = "api/global.properties";

    private static final String PUBLICS_CONF = "api/publics.properties";

    private static final String MERCHANT_CONF = "api/merchant.properties";

    private static final String WEBSOCKET_CONF = "api/webSocket.properties";

    /**
     * global config
     */

    public static final String MERCHANT_NAME = ConfigUtil.getProperty(GLOBAL_CONF, "name");	
    
    public static final String MERCHANT_URL = ConfigUtil.getProperty(GLOBAL_CONF, "url");

    public static final String MERCHANT_DOMAIN = ConfigUtil.getProperty(GLOBAL_CONF, "domain");

    public static final String APP_ID = ConfigUtil.getProperty(GLOBAL_CONF, "appId");

    public static final String APP_SECRET = ConfigUtil.getProperty(GLOBAL_CONF, "appSecret");

    public static final String TOKEN = ConfigUtil.getProperty(GLOBAL_CONF, "token");

    public static class PublicPlatform {
        /**
         * public platform API
         */

        public static final String URL_JSAPI_TICKET = ConfigUtil.getProperty(PUBLICS_CONF, "url.jsapi.ticket");

        public static final String URL_GET_ACCESS_TOKEN = ConfigUtil.getProperty(PUBLICS_CONF, "url.access.token");

        public static final String URL_MENU_CREATE = ConfigUtil.getProperty(PUBLICS_CONF, "url.menu.create");

        public static final String URL_MENU_GET = ConfigUtil.getProperty(PUBLICS_CONF, "url.menu.get");

        public static final String URL_MENU_DELETE = ConfigUtil.getProperty(PUBLICS_CONF, "url.menu.delete");

        public static final String URL_SNS_OAUTH2_REDIRECT = ConfigUtil.getProperty(PUBLICS_CONF, "url.sns.oauth2.redirect");

        public static final String URL_SNS_OAUTH2_TOKEN_GET = ConfigUtil.getProperty(PUBLICS_CONF, "url.sns.oauth2.token.get");

        public static final String URL_SNS_OAUTH2_TOKEN_REFRESH = ConfigUtil.getProperty(PUBLICS_CONF, "url.sns.oauth2.token.refresh");

        public static final String URL_USER_GET_SNS_INFO = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.get.sns.info");

        public static final String URL_USER_GET_INFO = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.get.info");

        public static final String URL_USER_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.get.list");

        public static final String URL_USER_GROUP_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.group.get.list");

        public static final String URL_USER_GROUP_GET_BY_OPENID = ConfigUtil.getProperty(PUBLICS_CONF, "url.user.group.get.by.openid");

        public static final String URL_MATERIAL_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.material.get.list");

        public static final String URL_MATERIAL_GET_DETAIL = ConfigUtil.getProperty(PUBLICS_CONF, "url.material.get.detail");

        public static final String URL_SHOP_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.shop.get.list");

        public static final String URL_SHOP_GET_DETAIL = ConfigUtil.getProperty(PUBLICS_CONF, "url.shop.get.detail");

        public static final String URL_PROGUCT_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.product.get.list");

        public static final String URL_PROGUCT_GET_DETAIL = ConfigUtil.getProperty(PUBLICS_CONF, "url.product.get.detail");

        public static final String URL_PROGUCT_GROUP_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.product.group.get.list");

        public static final String URL_PROGUCT_GROUP_GET_DETAIL = ConfigUtil.getProperty(PUBLICS_CONF, "url.product.group.get.detail");

        public static final String URL_ORDER_GET_LIST = ConfigUtil.getProperty(PUBLICS_CONF, "url.order.get.list");

        public static final String URL_ORDER_GET_DETAIL = ConfigUtil.getProperty(PUBLICS_CONF, "url.order.get.detail");

        public static final String URL_CUSTOMER_SERVICE_MESSAGE_SEND = ConfigUtil.getProperty(PUBLICS_CONF, "url.customer.service.message.send");

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



    public static String getServerPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        path = "/" + path.substring(1, path.indexOf("/classes"));
        return path;
    }

    public static String getServerUrl(HttpServletRequest request) {
        String path = request.getContextPath();
        int port = request.getServerPort();
        String basePath = null;
        if (80 == port) {
            basePath = request.getScheme() + "://" + request.getServerName() + path;
        } else {
            basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        }

        return basePath;
    }
}
