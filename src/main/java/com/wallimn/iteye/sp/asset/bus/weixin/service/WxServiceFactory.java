package com.wallimn.iteye.sp.asset.bus.weixin.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wallimn.iteye.sp.asset.bus.weixin.impl.WxUserServiceImpl;

@Configuration
public class WxServiceFactory {

	@Bean
	public WxUserService getWxUserService(){
		return new WxUserServiceImpl();
	}
}
