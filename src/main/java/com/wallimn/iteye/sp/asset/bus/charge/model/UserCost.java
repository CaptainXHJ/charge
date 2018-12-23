package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户的消费情况
 * @author Lenovo
 *
 */
public class UserCost implements Serializable {
	public Long getCostId() {
		return costId;
	}
	public void setCostId(Long costId) {
		this.costId = costId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Long getPileId() {
		return pileId;
	}
	public void setPileId(Long pileId) {
		this.pileId = pileId;
	}
	public Long getPlugId() {
		return plugId;
	}
	public void setPlugId(Long plugId) {
		this.plugId = plugId;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public Long getChargeMinutes() {
		return chargeMinutes;
	}
	public void setChargeMinutes(Long chargeMinutes) {
		this.chargeMinutes = chargeMinutes;
	}
	public Long getCostMoney() {
		return costMoney;
	}
	public void setCostMoney(Long costMoney) {
		this.costMoney = costMoney;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public Long getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Long payMoney) {
		this.payMoney = payMoney;
	}
	public Date getChargeBeginTime() {
		return chargeBeginTime;
	}
	public void setChargeBeginTime(Date chargeBeginTime) {
		this.chargeBeginTime = chargeBeginTime;
	}
	public Date getChargeEndTime() {
		return chargeEndTime;
	}
	public void setChargeEndTime(Date chargeEndTime) {
		this.chargeEndTime = chargeEndTime;
	}
	public String getChargeState() {
		return chargeState;
	}
	public void setChargeState(String chargeState) {
		this.chargeState = chargeState;
	}
	public Long getChargePower() {
		return chargePower;
	}
	public void setChargePower(Long chargePower) {
		this.chargePower = chargePower;
	}
	public Long getDemandMinutes() {
		return demandMinutes;
	}
	public void setDemandMinutes(Long demandMinutes) {
		this.demandMinutes = demandMinutes;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 7169185599946466085L;
	private Long costId;// ` bigint(20) NOT NULL AUTO_INCREMENT,
	private Long userId;// ` bigint(20) NOT NULL COMMENT '用户ID',
	private String softwareType;// ` varchar(32) DEFAULT NULL COMMENT '终端软件类型',
	private String systemType;// ` varchar(32) DEFAULT NULL COMMENT '终端系统类型',
	private Long pileId;// ` bigint(20) NOT NULL COMMENT '充电桩ID',
	private Long plugId;// ` bigint(20) NOT NULL COMMENT '充电桩插座ID',
	private String chargeType;// ` varchar(32) DEFAULT NULL COMMENT '充电消费类型',
	private Long chargeMinutes;// ` int(11) DEFAULT NULL COMMENT '充电时长（分）',
	private Long demandMinutes;//要求的充电时间
	private Long chargePower;// ` int COMMENT '充电功率',
	private Long costMoney;// ` decimal(8,2) DEFAULT NULL COMMENT '消费金额',
	private String billNo;// ` varchar(32) DEFAULT NULL COMMENT '消费单号',
	private Long payMoney;// ` decimal(8,2) DEFAULT NULL COMMENT '支付金额',
	private Date chargeBeginTime;// ` datetime DEFAULT NULL COMMENT '充电开始时间',
	private Date chargeEndTime;// ` datetime DEFAULT NULL COMMENT '充电结束时间',
	private String chargeState;// ` varchar(32) DEFAULT NULL COMMENT
								// '充电状态：0继电器断开正常、W继电器吸合正常工作、N充电插头未插入、U超过额定功率、A充电插头异常拔出',

}
