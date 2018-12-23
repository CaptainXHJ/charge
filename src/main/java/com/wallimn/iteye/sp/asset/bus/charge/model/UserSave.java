package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;
import java.util.Date;

public class UserSave implements Serializable {
	public Long getSaveId() {
		return saveId;
	}
	public void setSaveId(Long saveId) {
		this.saveId = saveId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getSoftwareType() {
		return softwareType;
	}
	public void setSoftwareType(String softwareType) {
		this.softwareType = softwareType;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public Long getSaveMoney() {
		return saveMoney;
	}
	public void setSaveMoney(Long saveMoney) {
		this.saveMoney = saveMoney;
	}
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public Long getAwardMoney() {
		return awardMoney;
	}
	public void setAwardMoney(Long awardMoney) {
		this.awardMoney = awardMoney;
	}
	public Date getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -45433158507435520L;
	private Long saveId;// ` bigint(20) NOT NULL AUTO_INCREMENT,
	private Long userId;// bigint(20) NOT NULL COMMENT '用户ID',
	private String billNo;// ` varchar(32) DEFAULT NULL COMMENT '充值单号',
	private String softwareType;// ` varchar(32) DEFAULT NULL COMMENT '终端软件类型',
	private String systemType;// ` varchar(32) DEFAULT NULL COMMENT '终端系统类型',
	private String saveType;// ` varchar(32) DEFAULT NULL COMMENT '充值类型',
	private Long saveMoney;// ` decimal(8,2) DEFAULT NULL COMMENT '充值金额',
	private Long actionId;// ` bigint(20) DEFAULT NULL COMMENT '活动ID',
	private Long awardMoney;// ` decimal(8,2) DEFAULT '0.00' COMMENT '返现金额',
	private Date saveTime;// ` datetime DEFAULT CURRENT_TIMESTAMP COMMENT
							// '充值时间',
}
