package com.wallimn.iteye.sp.asset.bus.charge.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wallimn.iteye.sp.asset.bus.charge.model.UserLog;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;

@Mapper
public interface UserLogDao extends BaseDao<UserLog, Long> {

}
