package com.wallimn.iteye.sp.asset.bus.charge.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.wallimn.iteye.sp.asset.bus.charge.dao.UserLogDao;
import com.wallimn.iteye.sp.asset.bus.charge.model.UserLog;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserLogService;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;
import com.wallimn.iteye.sp.asset.common.base.BaseServiceImpl;

/**
 * 用户日志
 * @author Lenovo
 *
 */
public class UserLogServiceImpl extends BaseServiceImpl<UserLog, Long> implements UserLogService {

	
	@Autowired
	private UserLogDao userLogDao;
	@Override
	protected BaseDao<UserLog, Long> getBaseDao() {
		return userLogDao;
	}
	@Override
	public UserLog insertUserLog(String openid, String type, String dataid, String information) {
		// TODO Auto-generated method stub
		UserLog log = new UserLog();
		log.setOpenid(openid);
		log.setLogType(type);
		log.setInformation(information);
		log.setDataId(dataid);
		this.userLogDao.insertEntity(log);
		return log;
	}

}
