package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.service.iface.ITestService;
import org.springframework.stereotype.Service;

/**
 * Created by Barrie on 15/11/23.
 */
@Service
public class TestService extends BaseService implements ITestService {
    @Override
    public String hello(String name) {
        return "Hello, " + name;
    }


}
