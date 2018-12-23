package com.wallimn.iteye.sp.asset.bus.charge.util;
/**
 * 插座的状态
 * @author wallimn，2018年9月23日 下午8:56:16
 *
 */
public enum PlugStateConst {

	/**
	 * 正常断开：0
	 */
	Off("0"),
	/**
	 * 正常吸合：1
	 */
	On("1"),
	/**
	 * 损坏：2
	 */
	Bad("2");
	
	private String code;
	private PlugStateConst(String code){
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
}
