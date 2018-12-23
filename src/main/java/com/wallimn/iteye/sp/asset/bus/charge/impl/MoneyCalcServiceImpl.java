package com.wallimn.iteye.sp.asset.bus.charge.impl;

import com.wallimn.iteye.sp.asset.bus.charge.model.*;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserSpecialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wallimn.iteye.sp.asset.bus.charge.service.ChargeService;
import com.wallimn.iteye.sp.asset.bus.charge.service.MoneyCalcService;

import java.util.Date;

public class MoneyCalcServiceImpl implements MoneyCalcService {
    private static final Logger log = LoggerFactory.getLogger(MoneyCalcServiceImpl.class);
    @Autowired
    ChargeService chargeSerivce;

    /**
     * 导入特殊用户服务类
     * 20181212 by chen
     */
    @Autowired
    UserSpecialService userSpecialService;

    @Override
    public UserCost calcChargeCostMoney(UserCost cost, String mobile) {
        Pile pile = this.chargeSerivce.selectPileById(cost.getPileId());
        Station station = this.chargeSerivce.selectStationById(pile.getStationId());
        CalcType calcType = this.chargeSerivce.selectCalcTypeById(station.getCalcTypeId());
        long minute = (cost.getChargeEndTime().getTime() - cost.getChargeBeginTime().getTime()) / 1000 / 60;
        cost.setChargeMinutes(minute);
        log.debug("电站ID：{}，消费ID：{}，计算类型：{}", station.getStationId(), cost.getCostId(), station.getCalcTypeId());
        if ("0".equals(station.getCalcTypeId())) {//默认的计算方式
            ChargeType ct = chargeSerivce.selectChargeTypeByPlugId(cost.getPlugId(), cost.getChargePower());
            if (ct != null) {
                long money = (long) Math.round(minute * ct.getRate() / 60.0);
                log.debug("功率费率：{}", money);
                cost.setCostMoney(money);
            } else {
                log.error("充电消费类型不存在！！");
            }
        } else if ("1".equals(station.getCalcTypeId())) {//全为6角的计算方式

            /**
             * 加入费率判断
             * 一、特殊用固定为6毛/次，其余普通用户为1元/4小时，2元/8小时来计费
             * 20181213 by chen
             */
            UserSpecial userSpecial = this.userSpecialService.selectByMobile(mobile);
            // 属于河南特殊用户的场合，固定费率6毛/次
            if (userSpecial != null) {
                log.debug("固定费率：{}", calcType.getParam1());
                cost.setCostMoney(calcType.getParam1().longValue());
            } else {
                log.debug("时间费率：{}", calcType.getParam1());
                int n = 1;
                long nowDateTime = new Date().getTime();
                long startTime = cost.getChargeBeginTime().getTime();
                int hours = (int) ((nowDateTime - startTime) / (1000 * 60 * 60));
                if (hours > 4) {
                    n = 2;
                }
                cost.setCostMoney(calcType.getParam2().longValue() * n);
            }
        } else {
            log.error("错误的计算类型：{}，所属电站：{}", station.getCalcTypeId(), station.getStationId());
        }
        return cost;
    }
}
