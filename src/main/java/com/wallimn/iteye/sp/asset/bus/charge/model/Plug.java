package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 插座
 * @author wallimn，2018年9月22日 下午3:54:13
 *
 */
public class Plug implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6377388893548600630L;

	private Long plugId;// bigint(20) NOT NULL AUTO_INCREMENT,
	private Long pileId;// bigint(20) DEFAULT NULL,
	private String plugBarcode;// varchar(100) DEFAULT NULL,
	private Long sn;// int(11) DEFAULT NULL,
	private Date setupTime;// ` datetime DEFAULT NULL,
	private Long status;// int(11) DEFAULT NULL,
	private Date createTime;// datetime DEFAULT NULL,
	public Long getPlugId() {
		return plugId;
	}
	public void setPlugId(Long plugId) {
		this.plugId = plugId;
	}
	public Long getPileId() {
		return pileId;
	}
	public void setPileId(Long pileId) {
		this.pileId = pileId;
	}
	public String getPlugBarcode() {
		return plugBarcode;
	}
	public void setPlugBarcode(String plugBarcode) {
		this.plugBarcode = plugBarcode;
	}
	public Long getSn() {
		return sn;
	}
	public void setSn(Long sn) {
		this.sn = sn;
	}
	public Date getSetupTime() {
		return setupTime;
	}
	public void setSetupTime(Date setupTime) {
		this.setupTime = setupTime;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
