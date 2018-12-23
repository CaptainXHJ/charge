package com.wallimn.iteye.sp.asset.bus.inform.model;

import java.io.Serializable;
import java.util.Date;

import com.wallimn.iteye.sp.asset.bus.weixin.model.Weixiner;

/**
 * 通知答复
 * @author Lenovo
 *
 */
public class InformReply extends Weixiner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 581824991040505813L;
	private String id;
	private String informId;
	private String showName;
	private Date replyDate;
	private String replyContent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInformId() {
		return informId;
	}
	public void setInformId(String informId) {
		this.informId = informId;
	}
	public Date getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	
	
}
