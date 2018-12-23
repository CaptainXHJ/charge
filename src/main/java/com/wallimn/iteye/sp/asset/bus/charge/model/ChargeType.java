package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;

/**
 * 费率功率关系表
 * @author wallimn，2018年9月23日 下午4:43:41
 *
 */
public class ChargeType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5686979252336329983L;
	private String typeId;
	private Long power1;
	private Long power2;
	private Float rate;
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public Long getPower1() {
		return power1;
	}
	public void setPower1(Long power1) {
		this.power1 = power1;
	}
	public Long getPower2() {
		return power2;
	}
	public void setPower2(Long power2) {
		this.power2 = power2;
	}
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	
	
}
