package com.wallimn.iteye.sp.asset.bus.charge.dao;

import com.wallimn.iteye.sp.asset.bus.charge.model.Agency;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Captain J
 * @date 2018/12/22 9:50
 */
@Mapper
public interface AgencyDao {
    Agency selectAgencyByMobile(@Param("mobile") String mobile);

    void updateAgencyLogincount(@Param("agency") Agency agency);

    /**
     * 修改登录错误次数为0
     */
    void updateAgencyOrginLogincount();
}
