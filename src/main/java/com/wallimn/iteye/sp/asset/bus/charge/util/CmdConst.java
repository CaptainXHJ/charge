package com.wallimn.iteye.sp.asset.bus.charge.util;
/**
 * 用于定义与通信服务器的命令
 * @author wallimn，2018年9月23日 下午3:19:53
 *
 */
public enum CmdConst {
	/**
	 * 10:时钟同步
	 */
	TimeSync("10","时钟同步"),
	/**
	 * 20:通信服务器上报状态
	 */
	ReportState("20","通信服务器上报状态"),
	/**
	 * 21:通信服务器上报信息
	 */
	ReportInformation("21","通信服务器上报信息"),
	/**
	 * 30:通知插座改变状态
	 */
	InformComm("30","通知插座改变状态"),
	/**
	 * 31:通知服务器上报状态
	 */
	InformReport("31","通知服务器上报状态");
	
	private String code;
	private String name;
	
	private CmdConst(String code,String name){
		this.code = code;
		this.name	= name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	
}
