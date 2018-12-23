package com.wallimn.iteye.sp.asset.bus.charge.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.wallimn.iteye.sp.asset.bus.charge.dao.UserSaveDao;
import com.wallimn.iteye.sp.asset.bus.charge.model.UserSave;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserSaveService;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;
import com.wallimn.iteye.sp.asset.common.base.BaseServiceImpl;

/**
 * 用户存款服务类
 * @author Lenovo
 *
 */
public class UserSaveServiceImpl extends BaseServiceImpl<UserSave, Long> implements UserSaveService {

	
	@Autowired
	private UserSaveDao userSaveDao;
	@Override
	protected BaseDao<UserSave, Long> getBaseDao() {
		return userSaveDao;
	}

}
