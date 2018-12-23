package com.wallimn.iteye.sp.asset.bus.charge.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.wallimn.iteye.sp.asset.bus.charge.dao.UnifiedOrderDao;
import com.wallimn.iteye.sp.asset.bus.charge.model.UnifiedOrder;
import com.wallimn.iteye.sp.asset.bus.charge.service.UnifiedOrderService;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;
import com.wallimn.iteye.sp.asset.common.base.BaseServiceImpl;

/**
 * 统一定单
 * @author Lenovo
 *
 */
public class UnifiedOrderServiceImpl extends BaseServiceImpl<UnifiedOrder, String> implements UnifiedOrderService {

	
	@Autowired
	private UnifiedOrderDao unifiedOrderDao;
	@Override
	protected BaseDao<UnifiedOrder, String> getBaseDao() {
		return unifiedOrderDao;
	}

}
