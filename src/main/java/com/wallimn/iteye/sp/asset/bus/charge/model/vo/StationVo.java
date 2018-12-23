package com.wallimn.iteye.sp.asset.bus.charge.model.vo;

import java.math.BigDecimal;

import com.wallimn.iteye.sp.asset.bus.charge.model.Station;

public class StationVo extends Station {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1299446077783877056L;
	private BigDecimal distance;

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}
}
