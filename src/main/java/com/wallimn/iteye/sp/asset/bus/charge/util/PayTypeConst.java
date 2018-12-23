package com.wallimn.iteye.sp.asset.bus.charge.util;
/**
 * 支付类型常量
 * @author wallimn，2018年10月6日 下午11:28:51
 *
 */
public enum PayTypeConst {
	
	Weixin("微信"),
	Zhifubao("支付宝"),
	Xinyongka("信用卡"),
	Yinhangka("银行卡");
	
	private String message;
	
	private PayTypeConst(String message){
		this.message= message;;
	}
	
	public String getMessage(){
		return this.message;
	}
}
