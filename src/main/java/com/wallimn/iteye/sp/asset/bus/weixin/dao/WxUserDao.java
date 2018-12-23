package com.wallimn.iteye.sp.asset.bus.weixin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wallimn.iteye.sp.asset.bus.weixin.model.WxUser;

@Mapper
public interface WxUserDao {

	WxUser selectUserByOpenid(@Param("openid") String openid);
	int insertUser(WxUser entity);
	
	int updateUser(WxUser entity);
}
