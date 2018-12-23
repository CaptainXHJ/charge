package com.wallimn.iteye.sp.asset.bus.example.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.wallimn.iteye.sp.asset.bus.example.dao.ExampleDao;
import com.wallimn.iteye.sp.asset.bus.example.model.Example;
import com.wallimn.iteye.sp.asset.bus.example.service.ExampleService;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;
import com.wallimn.iteye.sp.asset.common.base.BaseServiceImpl;

public class ExampleServiceImpl extends BaseServiceImpl<Example,String> implements ExampleService {

	@Autowired
	private ExampleDao exampleDao;
	
	@Override
	protected BaseDao<Example, String> getBaseDao() {
		return this.exampleDao;
	}

}
