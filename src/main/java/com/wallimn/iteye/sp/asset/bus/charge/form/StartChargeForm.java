package com.wallimn.iteye.sp.asset.bus.charge.form;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 用于接收启动充电的参数
 * @author wallimn，2018年10月5日 下午2:21:50
 *
 */
public class StartChargeForm extends OpenidForm {
	/**
	 * 插座ID
	 */
	@NotNull(message="插座标识(id)不能为空")
	@JsonProperty("id")
	private Long id;
	
	
	@NotNull(message="充电时间(time)不能为空")
	private Long time;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getTime() {
		return time;
	}


	public void setTime(Long time) {
		this.time = time;
	}
	
	
}
