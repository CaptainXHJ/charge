package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;

/**
 * 计算类型Model
 * @author wallimn，2018年10月14日 下午2:33:20
 *
 */
public class CalcType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6985058971482494318L;
	private String id;
	private String name;
	private Float param1;
	private Float param2;
	private Float param3;
	private Float param4;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getParam1() {
		return param1;
	}
	public void setParam1(Float param1) {
		this.param1 = param1;
	}
	public Float getParam2() {
		return param2;
	}
	public void setParam2(Float param2) {
		this.param2 = param2;
	}
	public Float getParam3() {
		return param3;
	}
	public void setParam3(Float param3) {
		this.param3 = param3;
	}
	public Float getParam4() {
		return param4;
	}
	public void setParam4(Float param4) {
		this.param4 = param4;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
