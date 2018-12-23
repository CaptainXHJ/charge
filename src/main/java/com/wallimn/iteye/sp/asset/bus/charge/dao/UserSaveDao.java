package com.wallimn.iteye.sp.asset.bus.charge.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wallimn.iteye.sp.asset.bus.charge.model.UserSave;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;

@Mapper
public interface UserSaveDao extends BaseDao<UserSave, Long> {

}
