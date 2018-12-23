package com.wallimn.iteye.sp.asset.bus.charge.dao;

import com.wallimn.iteye.sp.asset.bus.charge.model.UserSpecial;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserSpecialDao extends BaseDao<UserSpecial, Long> {
    public UserSpecial selectByMobile(@Param("mobile") String mobile);
}
