package com.wallimn.iteye.sp.asset.bus.math.model;

import java.io.Serializable;

/**
 * 竖式
 * @author Lenovo
 *
 */
public class Shushi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6748328266804873066L;
	private int num1;
	private int num2;
	
	
	private int showOrder;
	private int answer;
	private String id;
	private String testId;
	private String operator;
	
	
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
}
