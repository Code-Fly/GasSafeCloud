/**
 * 
 */
package com.manicure.keystone.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manicure.base.controller.BaseController;
import com.manicure.base.helper.ConfigUtil;
import com.manicure.keystone.service.iface.ICoreService;
import com.manicure.keystone.service.iface.IDemoService;
import com.manicure.keystone.service.impl.MenuService;

/**
 * 
 * @author Barrie
 *
 */
@Controller
@RequestMapping(value = "/auth")
public class Demo extends BaseController {
	@Resource
	IDemoService demoService;
	@Resource
	MenuService menuMgr;
	@Resource
	ICoreService coreService;

	@RequestMapping(value = "/list/{id}/{name}", method = RequestMethod.GET)
	@ResponseBody
	public String list(@PathVariable String id, @PathVariable String name)
			throws IOException {
		String rst = demoService.selectList();
		rst += "{" + id + "}" + "{" + name + "}";
		logger.info(rst);
		logger.error("sdasdasdas啊实打实的");
		new ConfigUtil();
		logger.info(ConfigUtil.getProperty("jdbc.properties", "jdbc.username"));
		return rst;
	}

}