package com.wallimn.iteye.sp.asset.bus.example.service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wallimn.iteye.sp.asset.bus.example.impl.ExampleServiceImpl;
/**
 * Service的工厂。这个包中的Service由这个工厂定义
 * @author Lenovo
 *
 */
@Configuration
public class ExampleServiceFactory {

	@Bean
	public ExampleService getExampleService(){
		ExampleService bean = new ExampleServiceImpl();
		return bean;
	}
}
