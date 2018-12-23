package com.wallimn.iteye.sp.asset.bus.weixin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.wallimn.iteye.sp.asset.bus.charge.model.User;

/**
 * 记录Session的会话数据
 * @author wallimn，2018年10月12日 下午8:13:48
 *
 */
public class SessionData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4906411329378652193L;
	public Date getLoginedTime() {
		return loginedTime;
	}
	public void setLoginedTime(Date loginedTime) {
		this.loginedTime = loginedTime;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public User getUser() {
		return user;
	}
	public void setUserId(User userId) {
		this.user = userId;
	}
	public Map<String,Object> getData() {
		return data;
	}
	public void setData(Map<String,Object> data) {
		this.data = data;
	}
	private Date loginedTime;
	private String sessionId;
	private User user;
	private Map<String,Object> data;
	
}
