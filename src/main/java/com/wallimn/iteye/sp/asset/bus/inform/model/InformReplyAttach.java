package com.wallimn.iteye.sp.asset.bus.inform.model;

import java.io.Serializable;
import java.util.Date;

public class InformReplyAttach implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1295642367030699634L;
	private String id;
	private String replyId;
	private String attachUrl;
	private Date attachDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public String getAttachUrl() {
		return attachUrl;
	}
	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}
	public Date getAttachDate() {
		return attachDate;
	}
	public void setAttachDate(Date attachDate) {
		this.attachDate = attachDate;
	}
	
	
}
