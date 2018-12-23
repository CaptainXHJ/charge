package com.wallimn.iteye.sp.asset.bus.charge.util;
/**
 * 控制器上报的状态。
 */
public enum ControlStateConst {

	/**
	 * 正常断开或处于空闲,0
	 */
	Disconnect("0"),
	/**
	 * 正常吸合,W
	 */
	Connect("W"),
	/**
	 * 插头未接入,N
	 */
	Unplug("N"),
	/**
	 * 超过额定功率,U
	 */
	Over("U"),
	/**
	 * 异常拔出,识为完成,A
	 */
	Exception("A"),
	/**
	 * 超时完成，E
	 */
	Overtime("E"),
	/**
	 * 充满完成，F
	 */
	Full("F"),
	/**
	 * 故障，Z
	 */
	Fault("Z");
	
	private String code;
	private ControlStateConst(String code){
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
	/**
	 * 判断硬件上报的状态是否是结束控制字，与硬件控制字的含义相关
	 * @author wallimn，2018年9月29日 下午4:18:24
	 * @param code
	 * @return
	 */
	public static boolean isEndStateOfControl(String controleCode){
		return UserCostStateConst.getEndStateOfCost(controleCode)!=null;
	}
}
