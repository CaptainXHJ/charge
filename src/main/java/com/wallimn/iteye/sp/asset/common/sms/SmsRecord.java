package com.wallimn.iteye.sp.asset.common.sms;

import java.io.Serializable;
import java.util.Date;
/**
 * 短信记录类
 * @author wallimn，2018年9月26日 下午3:05:00
 *
 */
public class SmsRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3755605044708857634L;
	
	private String openid;
	private String mobile;
	private Date sendTime;
	private String code;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
