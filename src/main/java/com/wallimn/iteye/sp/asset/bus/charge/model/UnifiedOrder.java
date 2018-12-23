package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;
import java.util.Date;

public class UnifiedOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1042785608358882771L;

	/**
	 * 	out_trade_no varchar(32) primary key,
	total_fee bigint(20),
	openid varchar(32),
	product_id bigint(32),
	attach varchar(255),
	body varchar(255),
	spbill_create_ip varchar(255),
	order_time datetime,
	finish_time datetime,
	order_state varchar(1) comment '0生成、1支付、2失败',
	remark varchar(255) comment '失败时的原因'

	 */
	
	private String outTradeNo;
	private Long totalFee;
	private String openid;
	private Long productId;
	private String attach;
	private String body;
	private String spbillCreateIp;
	private Date orderTime;
	private Date finishTime;
	private String orderState;
	private String systemType;
	private String payType;
	private String softwareType;
	private String remark;
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Long getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getSoftwareType() {
		return softwareType;
	}
	public void setSoftwareType(String softwareType) {
		this.softwareType = softwareType;
	}
	
	
}
