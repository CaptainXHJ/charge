package com.wallimn.iteye.sp.asset.bus.charge.service;

import com.wallimn.iteye.sp.asset.bus.charge.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChargeServiceFactory {
	
	@Bean
	public ChargeService getChargeService(){
		return new ChargeServiceImpl();
	}

	@Bean
	public UserService getUserService(){
		return new UserServiceImpl();
	}

	@Bean
	public UserCostService getUserCostService(){
		return new UserCostServiceImpl();
	}

	@Bean
	public UserSaveService getUserSaveService(){
		return new UserSaveServiceImpl();
	}

	@Bean
	public UserLogService getUserLogService(){
		return new UserLogServiceImpl();
	}
	
	@Bean
	public UnifiedOrderService getUnifiedOrderService(){
		return new UnifiedOrderServiceImpl();
	}
	
	@Bean
	public MoneyCalcService getMoneyCalcService(){
		return new MoneyCalcServiceImpl();
	}

	@Bean
	public UserSpecialService getUserSpecialService(){
		return new UserSpecialServiceImpl();
	}
}
