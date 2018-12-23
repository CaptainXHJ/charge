package com.wallimn.iteye.sp.asset.bus.charge.service;

import com.wallimn.iteye.sp.asset.bus.charge.model.UserLog;
import com.wallimn.iteye.sp.asset.common.base.BaseService;

public interface UserLogService extends BaseService<UserLog, Long> {

	public UserLog insertUserLog(String openid,String type,String dataid,String information);
}
