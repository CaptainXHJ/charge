package com.wallimn.iteye.sp.asset.bus.charge.util;

/**
 * 用户消费的状态，其中的字母取了硬件的控制字
 * @author wallimn，2018年9月23日 下午7:45:14
 *
 */
public enum UserCostStateConst {
	/**
	 * 等待充电开始,10
	 */
	Prepare("10","等待充电开始"),
	/**
	 * 开始,20
	 */
	Begin("20","充电开始"),
	/**
	 * 结束-充电时间到,3E
	 */
	End_TimeOver("3E","充电时间到，充电结束"),
	/**
	 * 结束-异常断开,3A
	 */
	End_Exception("3A","异常断开，充电结束"),
	/**
	 * 结束-充满，3F
	 */
	End_Full("3F","充满，充电结束"),
	/**
	 * 已付费,40
	 */
	Paied("40","充电费用已付"),
	/**
	 * 超过额定功率,EU
	 */
	OverPower("EU","超过额定功率，错误"),
	/**
	 * 插座故障，EZ
	 */
	Fault("EZ","插座故障，错误"),
	/**
	 * 插座未接通,EN
	 */
	Unplug("EN","插座未接通，错误"),
	/**
	 * 通信超时,EO
	 */
	Overtime("EO","通信超时，错误");
	
	
	private static final String EndPrefix="3";
	@SuppressWarnings("unused")
	private static final String ErrorPrefix="E";
	private String code;
	private String message;
	private UserCostStateConst(String code,String message){
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return this.code;
	}
	public String getMessage() {
		return this.message;
	}
	/**
	 * 判断用户充电是否结束
	 * @author wallimn，2018年9月29日 下午4:18:39
	 * @param code
	 * @return
	 */
	public static boolean isEndStateOfCost(String code){
		if(code==null)return false;
		return code.startsWith(EndPrefix);
	}
	/**
	 * 根据硬件控制字，返回结束常量。如果不是结束，返回null
	 * @author wallimn，2018年9月29日 下午4:19:07
	 * @param code
	 * @return
	 */
	public static UserCostStateConst getEndStateOfCost(String controleCode){
		String mycode = EndPrefix+controleCode;
		for(UserCostStateConst e:values()){
			if(mycode.equals(e.getCode())){
				return e;
			}
		}
		return null;
	}
}
