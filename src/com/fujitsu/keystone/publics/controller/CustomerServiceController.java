/**
 *
 */
package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.entity.customer.account.KfInfo;
import com.fujitsu.keystone.publics.entity.customer.message.CouponMessage;
import com.fujitsu.keystone.publics.entity.customer.message.WxCard;
import com.fujitsu.keystone.publics.service.iface.ICustomerService;
import com.fujitsu.keystone.publics.service.impl.CustomerService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Barrie
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class CustomerServiceController extends BaseController {
    @Resource
    ICustomerService customerService;

    @RequestMapping(value = "/customerservice/coupon/send", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String send(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "touser", required = true) String touser,
                       @RequestParam(value = "cardId", required = true) String cardId) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        CouponMessage message = new CouponMessage();
        message.setMsgtype(CustomerService.CUSTOMER_SERVICE_MESSAGE_TYPE_COUPON);
        message.setTouser(touser);
        WxCard coupon = new WxCard();
        coupon.setCard_id(cardId);
        message.setWxcard(coupon);

        JSONObject resp = customerService.sendCouponMessage(at, message);

        return resp.toString();
    }

    @RequestMapping(value = "/customerservice/account/list", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAccountList(HttpServletRequest request, HttpServletResponse response
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = customerService.getAccountList(at);

        return resp.toString();
    }

    @RequestMapping(value = "/customerservice/account/online", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAccountListOnline(HttpServletRequest request, HttpServletResponse response
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = customerService.getAccountListOnline(at);

        return resp.toString();
    }

    @RequestMapping(value = "/customerservice/account/create", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAccountAdd(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "kf_account", required = true) String kf_account,
                                @RequestParam(value = "nickname", required = true) String nickname,
                                @RequestParam(value = "password", required = true) String password
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        KfInfo account = new KfInfo();
        account.setKf_account(kf_account);
        account.setNickname(nickname);
        account.setPassword(password);
        JSONObject resp = customerService.getAccountAdd(at, account);

        return resp.toString();
    }

    @RequestMapping(value = "/customerservice/account/delete", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAccountDelete(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "kf_account", required = true) String kf_account
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        KfInfo account = new KfInfo();
        account.setKf_account(kf_account);
        JSONObject resp = customerService.getAccountDelete(at, account);

        return resp.toString();
    }

    @RequestMapping(value = "/customerservice/account/update", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAccountUpdate(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "kf_account", required = true) String kf_account,
                                @RequestParam(value = "nickname", required = true) String nickname,
                                @RequestParam(value = "password", required = true) String password
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        KfInfo account = new KfInfo();
        account.setKf_account(kf_account);
        account.setNickname(nickname);
        account.setPassword(password);
        JSONObject resp = customerService.getAccountUpdate(at, account);

        return resp.toString();
    }
}
