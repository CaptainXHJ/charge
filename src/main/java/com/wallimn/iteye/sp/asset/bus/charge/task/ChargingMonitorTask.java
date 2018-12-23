package com.wallimn.iteye.sp.asset.bus.charge.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wallimn.iteye.sp.asset.bus.charge.model.Pile;
import com.wallimn.iteye.sp.asset.bus.charge.model.Plug;
import com.wallimn.iteye.sp.asset.bus.charge.model.User;
import com.wallimn.iteye.sp.asset.bus.charge.model.UserCost;
import com.wallimn.iteye.sp.asset.bus.charge.service.ChargeService;
import com.wallimn.iteye.sp.asset.bus.charge.service.MoneyCalcService;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserCostService;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserService;
import com.wallimn.iteye.sp.asset.bus.charge.util.UserCostStateConst;
import com.wallimn.iteye.sp.asset.common.config.GlobalConfig;
import com.wallimn.iteye.sp.asset.common.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充电消费监控任务
 * 规则：
 * 一、河南内乡特殊用户的场合
 * 不做处理（会充满自停）
 * 二、其他用户的场合，出现以下的情况，立即向充电桩发出停止充电指令并更新充电状态为3E（充电时间到，充电结束）
 * （1）用户正在充电，且用户余额不足2元，且充电时间大于或等于4小时且小于24小时的场合
 * （2）用户正在充电，且用户余额大于2元，且充电时间大于或等于8小时且小于24小时的场合
 *
 * @author chenjy，2018年12月11日 下午16:00:08
 */
@Component
public class ChargingMonitorTask {
    private static Logger log = LoggerFactory.getLogger(ChargingMonitorTask.class);

    @Autowired
    private UserCostService userCostService;

    @Autowired
    private UserService userService;

    @Autowired
    ChargeService chargeSerivce;

    @Autowired
    GlobalConfig globalConfig;

    @Autowired
    MoneyCalcService moneyCalcService;

    @Scheduled(cron = "*/59 * * * * *")
    public void monitorCharging() {
        // 查询充电时间超限的数据（规则请参照该类注释）
        log.info("充电消费监控任务：开始执行");
        List<UserCost> userCostList = this.userCostService.selectOverTimeCharging();
        if (userCostList != null && userCostList.size() > 0) {
            for (int i = 0; i < userCostList.size(); i++) {
                long costId = 0L;
                try {
                    UserCost userCost = new UserCost();
                    costId = userCostList.get(i).getCostId().longValue();
                    userCost.setCostId(costId);

                    // 获取用户信息
                    User user = this.userService.selectEntityById(userCostList.get(i).getUserId());
                    // 获取充电桩信息
                    Pile pile = this.chargeSerivce.selectPileById(userCostList.get(i).getPileId());
                    // 获取充电口信息
                    Plug plug = this.chargeSerivce.selectPlugById(userCostList.get(i).getPlugId());
                    if (user != null && pile != null && plug != null) {
                        log.info("充电消费监控任务：消费订单为" + costId + "开始向充电桩发送停止充电命令");
                        // 向充电桩发出停止充电的指令
                        Map<String, Object> ctrlParam = new HashMap<String, Object>();
                        //吸合命令
                        ctrlParam.put("chargeHour", 8);
                        ctrlParam.put("controlType", 0);
                        ctrlParam.put("deviceid", pile.getPileSerial());
                        ctrlParam.put("isLowPower", pile.getIsHighPower());
                        ctrlParam.put("portNo", plug.getSn());

                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

                        String json = objectMapper.writeValueAsString(ctrlParam);
                        log.debug("发送参数：{}", json);
                        String result = HttpUtil.post(this.globalConfig.getChargeService(), json);
                        log.debug("通信服务器返回：{}", result);
                        if (!"OK".equals(result)) {
                            log.error("充电消费监控任务：消费订单为" + costId + "向充电桩发送命令失败");
                            // 重新发送（目前是发送一次失败后再发起一次，如果还是失败则不作处理）
                            result = HttpUtil.post(this.globalConfig.getChargeService(), json);
                            if (!"OK".equals(result)) {
                                log.error("充电消费监控任务：消费订单为" + costId + "第二次向充电桩发送命令失败");
                                continue;
                            }
                        } else {
                            log.info("充电消费监控任务：消费订单为" + costId + "向充电桩发送停止充电命令成功！");
                        }
                    } else {
                        log.error("充电消费监控任务：消费订单为" + costId + "关联的用户信息、充电桩信息、插座信息异常");
                    }

                    // 扣费处理
                    this.moneyCalcService.calcChargeCostMoney(userCostList.get(i), user.getMobile());
                    if (user.getBalance() >= userCostList.get(i).getCostMoney()) {
                        this.chargeSerivce.decreaseUserMoney(user, userCostList.get(i));
                        log.debug("用户金额直接扣款");
                    } else {
                        log.warn("用户金额不足，用户ID：{}，余额：{}，消费：{}", user.getUserId(), user.getBalance(), costId);
                    }

                    // 更新消费数据的状态及结束时间
                    userCost.setChargeEndTime(new Date());
                    userCost.setChargeState(UserCostStateConst.End_TimeOver.getCode());
                    int n = this.userCostService.updateEntity(userCost);
                    if (n == 0) {
                        log.error("充电消费监控任务：消费订单为" + costId + "的状态更新异常");
                    }
                } catch (Exception e) {
                    log.error("充电消费监控任务：消费订单为" + costId + "的出现异常。" + e.getMessage());
                }
            }
        } else {
            log.info("充电消费监控任务：无数据");
        }
    }
}
