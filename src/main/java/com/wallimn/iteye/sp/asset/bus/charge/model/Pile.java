package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 充电桩
 * 20180926增加pileName/pileSerial两个字段
 * @author wallimn，2018年9月22日 下午3:53:57
 *
 */
public class Pile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3701450324727400066L;

	private Long pileId;
	private String pileName;
	private String pileSerial;
	private Long stationId;//` bigint(20) DEFAULT NULL COMMENT '充电站ID',
	private String pileBarcode;//` varchar(100) DEFAULT NULL COMMENT '二维码',
	private String batch;// varchar(45) DEFAULT NULL COMMENT '批次',
	private String manufacturer;// varchar(100) DEFAULT NULL COMMENT '生产厂商',
	private Long plugs;// int(11) DEFAULT NULL COMMENT '充电插座数量',
	private Long isHighPower;// int(11) DEFAULT NULL COMMENT '是否高电压',
	private Long status;//` int(11);// DEFAULT NULL COMMENT '状态',
	private String ip;// varchar(45) DEFAULT NULL COMMENT 'ip',
	private Long unitType;// varchar(45) DEFAULT NULL,
	private Date createTime;// datetime DEFAULT NULL,
	private Long createUserId;// bigint(20) DEFAULT NULL,
	private Float longitude;// float DEFAULT '0' COMMENT '经度',
	private Float latitude;// float DEFAULT '0' COMMENT '纬度',
	public Long getPileId() {
		return pileId;
	}
	public void setPileId(Long pileId) {
		this.pileId = pileId;
	}
	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public String getPileBarcode() {
		return pileBarcode;
	}
	public void setPileBarcode(String pileBarcode) {
		this.pileBarcode = pileBarcode;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Long getPlugs() {
		return plugs;
	}
	public void setPlugs(Long plugs) {
		this.plugs = plugs;
	}
	public Long getIsHighPower() {
		return isHighPower;
	}
	public void setIsHighPower(Long isHighPower) {
		this.isHighPower = isHighPower;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getUnitType() {
		return unitType;
	}
	public void setUnitType(Long unitType) {
		this.unitType = unitType;
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
	public String getPileName() {
		return pileName;
	}
	public void setPileName(String pileName) {
		this.pileName = pileName;
	}
	public String getPileSerial() {
		return pileSerial;
	}
	public void setPileSerial(String pileSerial) {
		this.pileSerial = pileSerial;
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
	
}
