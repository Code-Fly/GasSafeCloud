/**
 *
 */
package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.keystone.publics.service.iface.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author Barrie
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class CustomerServiceController extends BaseController {
    @Resource
    ICustomerService customerService;

}
