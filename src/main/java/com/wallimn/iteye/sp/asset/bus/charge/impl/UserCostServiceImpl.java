package com.wallimn.iteye.sp.asset.bus.charge.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.wallimn.iteye.sp.asset.bus.charge.dao.UserCostDao;
import com.wallimn.iteye.sp.asset.bus.charge.model.UserCost;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserCostService;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;
import com.wallimn.iteye.sp.asset.common.base.BaseServiceImpl;

import java.util.List;

/**
 * 用户消费服务类
 * @author Lenovo
 *
 */
public class UserCostServiceImpl extends BaseServiceImpl<UserCost, Long>  implements UserCostService {

	//private static final Logger log = LoggerFactory.getLogger(UserCostServiceImpl.class);
	
	@Autowired
	private UserCostDao userCostDao;
	@Override
	protected BaseDao<UserCost, Long> getBaseDao() {
		return userCostDao;
	}
	
	@Override
	public UserCost selectLastUserCostByUserId(Long userId) {
		return this.userCostDao.selectLastUserCostByUserId(userId);
	}

	@Override
	//@CachePut(value="UserCost", key="#entity.id")
	public int updateUserCost(UserCost entity) {
		return this.userCostDao.updateUserCost(entity);
	}

	@Override
	//@Cacheable(value="UserCost", key="#id")
	public UserCost selectUserCostById(Long id) {
		return this.userCostDao.selectEntityById(id);
	}

	@Override
	//@CacheEvict(value="UserCost", key="#id")
	public int deleteUserCostById(Long id) {
		return 1;
	}

	@Override
	public UserCost selectLastUserCostByPlugId(Long plugId) {
		return this.userCostDao.selectLastUserCostByPlugId(plugId);
	}

	//
	@Override
	public List<UserCost> selectOverTimeCharging() {return this.userCostDao.selectOverTimeCharging();}
}
