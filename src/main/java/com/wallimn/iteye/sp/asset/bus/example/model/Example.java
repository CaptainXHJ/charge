package com.wallimn.iteye.sp.asset.bus.example.model;

import java.io.Serializable;

public class Example implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4007685647696393147L;
	private String id;
	private String name;
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
}
