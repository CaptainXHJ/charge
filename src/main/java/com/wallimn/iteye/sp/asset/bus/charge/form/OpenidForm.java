package com.wallimn.iteye.sp.asset.bus.charge.form;

import javax.validation.constraints.NotBlank;

/**
 * 只有openid的Form
 * @author wallimn，2018年10月5日 下午2:14:10
 *
 */
public class OpenidForm {

	//@NotEmpty(message="用户标识不能为空")
	@NotBlank(message="用户标识(openid)不能为空")//不能为null且trim()之后size>0
	private String openid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
}
