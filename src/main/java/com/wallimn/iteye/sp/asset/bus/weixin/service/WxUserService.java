package com.wallimn.iteye.sp.asset.bus.weixin.service;

import com.wallimn.iteye.sp.asset.bus.weixin.model.WxUser;

public interface WxUserService {
	WxUser selectUserByOpenid(String openid);
	int insertUser(WxUser entity);
	
	int updateUser(WxUser entity);

}
