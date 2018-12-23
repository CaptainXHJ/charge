package com.wallimn.iteye.sp.asset.bus.charge.service;

import com.wallimn.iteye.sp.asset.bus.charge.model.UserCost;
import com.wallimn.iteye.sp.asset.common.base.BaseService;

import java.util.List;

public interface UserCostService extends BaseService<UserCost, Long> {
	/**
	 * 查询某插座最后一条数据
	 * @author wallimn，2018年9月23日 下午4:13:53
	 * @param plugId
	 * @return
	 */
	UserCost selectLastUserCostByPlugId(Long plugId);

	/**
	 * 返回最后一条消费记录
	 * @author wallimn，2018年9月11日 上午1:06:23
	 * @param userId
	 * @return
	 */
	public UserCost selectLastUserCostByUserId(Long userId);
	
	/**
	 * 用户充电完成，修改充电状态、花费金额，为防止缓存出错，使用这个函数前，请先装载对象
	 * @author wallimn，2018年9月23日 下午2:30:14
	 * @param entity
	 * @return
	 */
	public int updateUserCost(UserCost entity);
	
	/**
	 * 重新定义的，还缓存的方法
	 * @author wallimn，2018年9月23日 下午2:45:31
	 * @param id
	 * @return
	 */
	public UserCost selectUserCostById(Long id);
	/**
	 * 删除缓存，不改变数据库
	 * @author wallimn，2018年9月23日 下午2:49:52
	 * @param id
	 * @return
	 */
	public int deleteUserCostById(Long id);

	/**
	 * 查询充电时间超限的数据20181212 by chen
	 * @return list
	 */
	public List<UserCost> selectOverTimeCharging();
}
