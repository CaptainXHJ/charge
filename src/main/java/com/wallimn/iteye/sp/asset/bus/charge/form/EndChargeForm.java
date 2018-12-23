package com.wallimn.iteye.sp.asset.bus.charge.form;

/**
 * 结束充电的FORM数据
 * @author wallimn，2018年10月5日 下午4:50:35
 *
 */
public class EndChargeForm extends OpenidForm {
	private Long id;//插座的ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
