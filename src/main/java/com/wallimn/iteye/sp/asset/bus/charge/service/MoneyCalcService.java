package com.wallimn.iteye.sp.asset.bus.charge.service;

import com.wallimn.iteye.sp.asset.bus.charge.model.UserCost;

public interface MoneyCalcService {
	/**
	 * 计算充电金额，填写金额和时间字段
	 * @author wallimn，2018年9月29日 下午6:03:56
	 * @param cost
	 * @param plugId
	 * @return
	 */
	public UserCost calcChargeCostMoney(UserCost cost, String mobile);
}
