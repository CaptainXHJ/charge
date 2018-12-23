package com.wallimn.iteye.sp.asset.bus.inform.model;

import java.io.Serializable;
import java.util.Date;

import com.wallimn.iteye.sp.asset.bus.weixin.model.Weixiner;
/**
 * 通知答复
 * 
 * <br>
 * <br>时间：2018年7月28日 上午10:44:11，作者：wallimn
 */
public class InformReplyRemark extends Weixiner implements Serializable  {

	private static final long serialVersionUID = -6852805135932251439L;
	private String id;
	private String replyId;
	private String remarkContent;
	private Integer remarkScore;
	private Date remarkDate;
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
	public String getRemarkContent() {
		return remarkContent;
	}
	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
	}
	public Integer getRemarkScore() {
		return remarkScore;
	}
	public void setRemarkScore(Integer remarkScore) {
		this.remarkScore = remarkScore;
	}
	public Date getRemarkDate() {
		return remarkDate;
	}
	public void setRemarkDate(Date remarkDate) {
		this.remarkDate = remarkDate;
	}
	
	
}
