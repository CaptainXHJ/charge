package com.wallimn.iteye.sp.asset.bus.charge.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.wallimn.iteye.sp.asset.bus.charge.dao.UserDao;
import com.wallimn.iteye.sp.asset.bus.charge.model.User;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserService;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;
import com.wallimn.iteye.sp.asset.common.base.BaseServiceImpl;

/**
 * 用户服务类
 * @author Lenovo
 *
 */
public class UserServiceImpl extends BaseServiceImpl<User, Long>  implements UserService {

	
	@Autowired
	private UserDao userDao;
	@Override
	protected BaseDao<User, Long> getBaseDao() {
		return userDao;
	}
	@Override
	public User selectUserByOpenid(String openid) {
		return this.userDao.selectUserByOpenid(openid);
	}
	@Override
	public int updateUserBalance(User entity) {
		return this.userDao.updateUserBalance(entity);
	}
	@Override
	public int deleteUserByOpenid(String openid) {
		return 1;
	}
}
