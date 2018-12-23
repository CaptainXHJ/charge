package com.wallimn.iteye.sp.asset.bus.math.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wallimn.iteye.sp.asset.bus.math.impl.MathServiceImpl;

@Configuration
public class MathServiceFactory {
	
	
	@Bean
	MathService getMathService(){
		return new MathServiceImpl();
	}
}
