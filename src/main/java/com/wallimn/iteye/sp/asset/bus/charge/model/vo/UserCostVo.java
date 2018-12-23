package com.wallimn.iteye.sp.asset.bus.charge.model.vo;

import java.util.Date;

import com.wallimn.iteye.sp.asset.bus.charge.model.UserCost;

public class UserCostVo extends UserCost {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8918372399349010942L;
	
	/**
	 * 计算当前的充电时间，方便用记使用，分为单位。
	 * @author wallimn，2018年10月5日 下午8:26:32
	 * @return
	 */
	public Long getCurrentChargeMinutes(){
		if(this.getChargeBeginTime()==null)return null;
		else{
			return ((new Date()).getTime()-this.getChargeBeginTime().getTime())/1000/60;
		}
	}

}
