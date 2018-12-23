package com.wallimn.iteye.sp.asset.bus.charge.util;

/**
 * 定单状态
 * @author wallimn，2018年9月25日 上午10:10:01
 *
 */
public enum OrderStateConst {

	/**
	 * 新建状态，1
	 */
	New("1"),
	/**
	 * 生成成功,2
	 */
	Success("2"),
	/**
	 * 付款成功,3
	 */
	Paid("3"),
	/**
	 * 付款失败,0
	 */
	Fail("0");
	private String code;
	private OrderStateConst(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
}
