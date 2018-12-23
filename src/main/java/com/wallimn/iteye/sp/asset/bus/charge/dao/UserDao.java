package com.wallimn.iteye.sp.asset.bus.charge.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wallimn.iteye.sp.asset.bus.charge.model.User;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;

@Mapper
public interface UserDao extends BaseDao<User, Long> {
	public User selectUserByOpenid(@Param("openid") String openid);
	int updateUserBalance(User entity);
}
