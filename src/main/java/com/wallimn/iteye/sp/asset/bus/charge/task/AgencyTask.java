package com.wallimn.iteye.sp.asset.bus.charge.task;

import com.wallimn.iteye.sp.asset.bus.charge.dao.AgencyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AgencyTask {

    @Autowired
    AgencyDao agencyDao;

    /**
     * 修改代理商登录错误次数
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateAgencyLogincount(){
        agencyDao.updateAgencyOrginLogincount();
    }
}
