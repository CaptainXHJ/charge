package com.wallimn.iteye.sp.asset.bus.inform.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wallimn.iteye.sp.asset.bus.inform.impl.InformServiceImpl;

@Configuration
public class InformServiceFactory {

	
	@Bean
	public InformService getInformService(){
		return new InformServiceImpl();
	}
}
