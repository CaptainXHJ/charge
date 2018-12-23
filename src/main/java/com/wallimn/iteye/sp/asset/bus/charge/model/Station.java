package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 充电站
 * @author wallimn，2018年9月22日 下午4:20:49
 *
 */
public class Station implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2903593743951885011L;

	private Long stationId;// bigint(20) NOT NULL,
	private String stationName;// varchar(100) DEFAULT NULL COMMENT '充电站名称',
	private String address;// varchar(100) DEFAULT NULL COMMENT '所在地址',
	private Long agentId;// bigint(20) DEFAULT '0' COMMENT '代理商ID',
	private String agentCode;// varchar(45) DEFAULT '' COMMENT '代理商编码',
	private Long piles;// int(11) DEFAULT '8' COMMENT '充电桩数量',
	private Float longitude;// float DEFAULT '0' COMMENT '纬度',
	private Float latitude;// float DEFAULT '0' COMMENT '纬度',
	private Float rate;// int(11) DEFAULT '0' COMMENT '电费费率',
	private Date createTime;// datetime DEFAULT NULL,
	private Long createUserId;// bigint(20) DEFAULT NULL,
	
	/**
	 * 计算类型ID
	 */
	private String calcTypeId;
	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public Long getPiles() {
		return piles;
	}
	public void setPiles(Long piles) {
		this.piles = piles;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public String getCalcTypeId() {
		return calcTypeId;
	}
	public void setCalcTypeId(String calcTypeId) {
		this.calcTypeId = calcTypeId;
	}

	
}
