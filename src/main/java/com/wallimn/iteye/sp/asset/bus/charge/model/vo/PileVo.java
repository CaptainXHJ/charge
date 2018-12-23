package com.wallimn.iteye.sp.asset.bus.charge.model.vo;

import java.math.BigDecimal;

import com.wallimn.iteye.sp.asset.bus.charge.model.Pile;

public class PileVo extends Pile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7200006861659601548L;

	private BigDecimal distance;
	
	/**
	 * 取电站的地址
	 */
	private String address;

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
