package com.wallimn.iteye.sp.asset.bus.charge.model;

/**
 * 用于Controller返回信息
 * @author wallimn，2018年9月22日 下午9:47:39
 *
 */
public class ActionResultDelete {
	private String code;
	private String msg;
	/**
	 * 对应的数据
	 */
	private Object data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public void succes(Object data){
		this.code = "success";
		//this.msg = msg;
		this.data = data;
	}
	
	public void fail(String msg){
		this.code = "fail";
		this.msg= msg;
	}
	
	
}
