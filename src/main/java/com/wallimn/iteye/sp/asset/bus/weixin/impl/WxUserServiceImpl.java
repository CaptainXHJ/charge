package com.wallimn.iteye.sp.asset.bus.weixin.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.wallimn.iteye.sp.asset.bus.weixin.dao.WxUserDao;
import com.wallimn.iteye.sp.asset.bus.weixin.model.WxUser;
import com.wallimn.iteye.sp.asset.bus.weixin.service.WxUserService;

public class WxUserServiceImpl implements WxUserService {

	@Autowired
	WxUserDao wxUserDao;
	
	@Override
	public WxUser selectUserByOpenid(String openid) {
		return this.wxUserDao.selectUserByOpenid(openid);
	}

	@Override
	public int insertUser(WxUser entity) {
		return this.wxUserDao.insertUser(entity);
	}

	@Override
	public int updateUser(WxUser entity) {
		return this.wxUserDao.updateUser(entity);
	}

}
