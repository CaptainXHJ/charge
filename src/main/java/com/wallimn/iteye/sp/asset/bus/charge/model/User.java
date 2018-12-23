package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 充电用户的基本信息表
 * @author Lenovo
 *
 */
public class User implements Serializable {
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIcCard() {
		return icCard;
	}
	public void setIcCard(String icCard) {
		this.icCard = icCard;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	public String getIcId() {
		return icId;
	}
	public void setIcId(String icId) {
		this.icId = icId;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8071598717299463540L;
	private Long userId;//` bigint(20) NOT NULL AUTO_INCREMENT,
	private String username;//` varchar(50) NOT NULL COMMENT '用户名',
	private String mobile;//` varchar(20) NOT NULL COMMENT '手机号',
	private String password;//` varchar(64) DEFAULT NULL COMMENT '密码',
	private Date createTime;//` datetime DEFAULT NULL COMMENT '创建时间',
	private String icCard;//` varchar(45) DEFAULT NULL COMMENT '身份证',
	private String address;//` varchar(100) DEFAULT NULL COMMENT '常用住址\n',
	private String openid;//` varchar(100) DEFAULT NULL,
	private Long balance;//` int(11) DEFAULT NULL COMMENT '余额',
	private String carType;//` varchar(45) DEFAULT NULL COMMENT '车型',
	private String numberPlate;//` varchar(45) DEFAULT NULL COMMENT '车牌',
	private String icId;//` varchar(45) DEFAULT NULL COMMENT '电子车牌',

}
