package com.wallimn.iteye.sp.asset.bus.charge.util;

public enum SoftwareTypeConst {
	
	WxLP("微信小程序"),
	ZfbLP("支付宝小程序"),
	App("APP");
	private String message;
	private SoftwareTypeConst(String message){
		this.message = message;
	}
	public String getMessage(){
		return this.message;
	}
}
