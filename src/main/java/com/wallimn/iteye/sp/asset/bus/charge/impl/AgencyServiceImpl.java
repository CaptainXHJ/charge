package com.wallimn.iteye.sp.asset.bus.charge.impl;

import com.wallimn.iteye.sp.asset.bus.charge.dao.AgencyDao;
import com.wallimn.iteye.sp.asset.bus.charge.model.Agency;
import com.wallimn.iteye.sp.asset.bus.charge.service.AgencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 和物业相关的逻辑处理
 * @author Captain J
 * @date 2018/12/22 9:38
 */
@Service
public class AgencyServiceImpl implements AgencyService {

    private static final Logger LOGGER= LoggerFactory.getLogger(AgencyServiceImpl.class);

    @Autowired
    AgencyDao agencyDao;

    /**
     * 根据手机号查询代理商信息
     * @param mobile
     * @return
     */
    @Override
    public Agency selectAgencyByMobile(String mobile) {
        return agencyDao.selectAgencyByMobile(mobile);
    }

    /**
     * 修改用户登录错误次数
     * @param agency
     */
    @Override
    @Transactional
    public void updateAgencyLogincount(Agency agency) {
        //修改登录错误次数
        agencyDao.updateAgencyLogincount(agency);
    }
}
