package com.wallimn.iteye.sp.asset.bus.charge.util;
/**
 * 系统类型
 * @author wallimn，2018年10月6日 下午11:43:14
 *
 */
public enum SystemTypeConst {
	
	android("android"),
	ios("ios");
	private String message;
	private SystemTypeConst(String message){
		this.message=message;
	}
	
	public String getMessage(){
		return this.message;
	}
}
