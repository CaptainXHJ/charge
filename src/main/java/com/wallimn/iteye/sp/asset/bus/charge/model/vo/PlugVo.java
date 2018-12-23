package com.wallimn.iteye.sp.asset.bus.charge.model.vo;

import com.wallimn.iteye.sp.asset.bus.charge.model.Plug;

public class PlugVo extends Plug {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1882496776235560791L;
	
	private String calcTypeId;
	private String calcTypeName;
	public String getCalcTypeId() {
		return calcTypeId;
	}
	public void setCalcTypeId(String calcTypeId) {
		this.calcTypeId = calcTypeId;
	}
	public String getCalcTypeName() {
		return calcTypeName;
	}
	public void setCalcTypeName(String calcTypeName) {
		this.calcTypeName = calcTypeName;
	}

}
