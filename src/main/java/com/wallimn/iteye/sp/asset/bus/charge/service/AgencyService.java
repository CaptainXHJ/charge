package com.wallimn.iteye.sp.asset.bus.charge.service;


import com.wallimn.iteye.sp.asset.bus.charge.model.Agency;

/**
 * @author Captain J
 * @date 2018/12/22 9:37
 */
public interface AgencyService {
    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    Agency selectAgencyByMobile(String mobile);

    /**
     *
     * @param agency
     */
    void updateAgencyLogincount(Agency agency);
}
