package com.wallimn.iteye.sp.asset.bus.charge.impl;

import com.wallimn.iteye.sp.asset.bus.charge.dao.UserSpecialDao;
import com.wallimn.iteye.sp.asset.bus.charge.model.UserSpecial;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserSpecialService;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;
import com.wallimn.iteye.sp.asset.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 特殊用户服务类
 *
 * @author chen
 */
public class UserSpecialServiceImpl extends BaseServiceImpl<UserSpecial, Long> implements UserSpecialService {

    @Autowired
    private UserSpecialDao userSpecialDao;

    @Override
    protected BaseDao<UserSpecial, Long> getBaseDao() {
        return userSpecialDao;
    }

    @Override
    public UserSpecial selectByMobile(String mobile) {
        return userSpecialDao.selectByMobile(mobile);
    }
}
