package com.wallimn.iteye.sp.asset.bus.charge.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wallimn.iteye.sp.asset.bus.charge.model.UserCost;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;

import java.util.List;

@Mapper
public interface UserCostDao extends BaseDao<UserCost,Long> {

	UserCost selectLastUserCostByPlugId(@Param("plugId") Long plugId);
	public UserCost selectLastUserCostByUserId(@Param("userId") Long userId);
	
	public int updateUserCost(UserCost entity) ;

	// 查询充电时间超限的数据20181212 by chen
	public List<UserCost> selectOverTimeCharging();
}
