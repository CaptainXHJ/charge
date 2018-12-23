package com.wallimn.iteye.sp.asset.bus.ijpay.controller.wxpay;

import com.jpay.weixin.api.WxPayApiConfig;

public abstract class WxPayApiController{
	public abstract WxPayApiConfig getApiConfig();
}