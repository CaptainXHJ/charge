package com.wallimn.iteye.sp.asset.bus.charge.service;

import com.wallimn.iteye.sp.asset.bus.charge.model.UserSpecial;
import com.wallimn.iteye.sp.asset.common.base.BaseService;

/**
 * 20181212 author: chen
 */
public interface UserSpecialService extends BaseService<UserSpecial, Long> {
    public UserSpecial selectByMobile(String mobile);
}
