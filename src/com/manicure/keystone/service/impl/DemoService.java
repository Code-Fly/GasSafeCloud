/**
 * 
 */
package com.manicure.keystone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manicure.base.service.BaseService;
import com.manicure.keystone.dao.impl.VentorDaoImpl;
import com.manicure.keystone.service.iface.IDemoService;

/**
 * @author Barrie
 *
 */
@Service
public class DemoService extends BaseService implements IDemoService {
	
	@Autowired
	private VentorDaoImpl ven;
	
	public String selectList() {

		return ven.selectList();
	}

}
