package com.wallimn.iteye.sp.asset.bus.inform.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wallimn.iteye.sp.asset.bus.weixin.model.Weixiner;

/**
 * 通知
 * @author Lenovo
 *
 */
public class Inform extends Weixiner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2695275731316685911L;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrivateFlag() {
		return privateFlag;
	}
	public void setPrivateFlag(String privateFlag) {
		this.privateFlag = privateFlag;
	}
	public String getAttachFlag() {
		return attachFlag;
	}
	public void setAttachFlag(String attachFlag) {
		this.attachFlag = attachFlag;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getInformDate() {
		return informDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setInformDate(Date informDate) {
		this.informDate = informDate;
	}
	public String getInformTitle() {
		return informTitle;
	}
	public void setInformTitle(String informTitle) {
		this.informTitle = informTitle;
	}
	public String getInformContent() {
		return informContent;
	}
	public void setInformContent(String informContent) {
		this.informContent = informContent;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public Integer getOpenCount() {
		return openCount;
	}
	public void setOpenCount(Integer openCount) {
		this.openCount = openCount;
	}
	public String getRemarkFlag() {
		return remarkFlag;
	}
	public void setRemarkFlag(String remarkFlag) {
		this.remarkFlag = remarkFlag;
	}
	public String getInformHost() {
		return informHost;
	}
	public void setInformHost(String informHost) {
		this.informHost = informHost;
	}
	private String id;
	private String coverUrl;
	private String informTitle;
	private String informContent;
	private String informHost;
	private Date informDate;
	private String privateFlag;
	private String attachFlag;
	private String remarkFlag;
	
	private Integer openCount;
	
	
}
