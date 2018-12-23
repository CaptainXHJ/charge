package com.wallimn.iteye.sp.asset.bus.charge.service;

import com.wallimn.iteye.sp.asset.bus.charge.model.User;
import com.wallimn.iteye.sp.asset.common.base.BaseService;

public interface UserService extends BaseService<User, Long> {

	public User selectUserByOpenid(String openid);
	public int deleteUserByOpenid(String openid);
	int updateUserBalance(User entity);
}
