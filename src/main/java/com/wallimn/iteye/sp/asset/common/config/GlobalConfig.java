package com.wallimn.iteye.sp.asset.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 从配置文件中读取的一些配置参数
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月22日 下午9:29:31<br>
 *
 */
@Component
public class GlobalConfig {
	
	/**
	 * 缺省的分页参数
	 */
	@Value("${defaultPageSize}")
	private Integer defaultPageSize;

	public Integer getDefaultPageSize() {
		return defaultPageSize;
	}
	
	
	/**
	 * 请求时间超阀值，超过这个值会记录日志
	 */
	@Value("${requestThreshold}")
	private long requestThreshold;
	
	public long getRequestThreshold(){
		return this.requestThreshold;
	}
	
	
	@Value("${charge.service}")
	private String chargeService;
	/**
	 * 充电服务器的地址
	 * @author wallimn，2018年9月23日 下午3:28:11
	 * @return
	 */
	public String getChargeService(){
		return this.chargeService;
	}
	
}
