package com.wallimn.iteye.sp.asset.bus.ijpay.controller.wxpay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.IpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.ext.kit.StrKit;
import com.jpay.vo.AjaxResult;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.jpay.weixin.api.WxPayApiConfig.PayModel;
import com.jpay.weixin.api.WxPayApiConfigKit;
import com.wallimn.iteye.sp.asset.bus.charge.model.UnifiedOrder;
import com.wallimn.iteye.sp.asset.bus.charge.service.ChargeService;
import com.wallimn.iteye.sp.asset.bus.charge.service.UnifiedOrderService;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserCostService;
import com.wallimn.iteye.sp.asset.bus.charge.util.OrderStateConst;
import com.wallimn.iteye.sp.asset.bus.charge.util.PayTypeConst;
import com.wallimn.iteye.sp.asset.bus.charge.util.SoftwareTypeConst;
import com.wallimn.iteye.sp.asset.bus.charge.util.SystemTypeConst;
import com.wallimn.iteye.sp.asset.bus.ijpay.entity.WxPayBean;
import com.wallimn.iteye.sp.asset.bus.wechat.config.WechatMpProperties;
import com.wallimn.iteye.sp.asset.common.util.StringUtil;

/**
 * 这个是我改造的、简化的微信小程序支付，仅包括支付的代码
 * 
 * @author wallimn，2018年9月22日 下午10:48:54
 *
 */
@RestController
@RequestMapping("/api/01/wxpay")
public class WxPayController extends WxPayApiController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WxPayBean wxPayBean;

	@Autowired
	WechatMpProperties wechatMpProperties;

	@Autowired
	ChargeService chargeService;

	@Autowired
	UserCostService userCostService;

	@Autowired
	StringUtil stringUtil;
	
	@Autowired
	UnifiedOrderService unifiedOrderService;

	String notifyUrl;

	/**
	 * 这个由拦截器调用，注意拦截器的地址匹配
	 */
	@Override
	public WxPayApiConfig getApiConfig() {
		//this.notifyUrl = wxPayBean.getDomain().concat("/api/01/wxpay/paynotify");
		this.notifyUrl = wxPayBean.getDomain().concat(wxPayBean.getNotifyUrl());
		log.debug("微信回调地址：{}",this.notifyUrl);
		return WxPayApiConfig.New()
				.setAppId(wxPayBean.getAppId())
				.setMchId(wxPayBean.getMchId())
				.setPaternerKey(wxPayBean.getPartnerKey())
				.setPayModel(PayModel.BUSINESSMODEL);
	}

	/**
	 * 显示欢迎信息
	 * 
	 * @author wallimn，2018年9月22日 下午3:34:24
	 * @return 欢迎信息
	 */
	@RequestMapping("")
	public String index() {
		log.info("欢迎使用IJPay,商户模式下微信支付 - by Javen");
		log.info(wxPayBean.toString());
		return ("欢迎使用IJPay 商户模式下微信支付  - by Javen");
	}

	/**
	 * 显示配置参数
	 * 
	 * @author wallimn，2018年9月22日 下午3:34:39
	 * @return
	 */
	@RequestMapping("/paybean")
	public String test() {
		return wxPayBean.toString();
	}

	/**
	 * 显示加密信息
	 * 
	 * @author wallimn，2018年9月22日 下午3:35:14
	 * @return 返回加密结果
	 */
	@RequestMapping("/getkey")
	public String getKey() {
		return WxPayApi.getsignkey(wxPayBean.getAppId(), wxPayBean.getPartnerKey());
	}
	@Autowired
	HttpServletRequest request;

	/**
	 * 生成统一定单，返回小程序可调起支付界面用的参数。
	 * 
	 * @author wallimn，2018年9月22日 下午3:35:26
	 * @param attach
	 *            订单附件
	 * @param body
	 *            订单内容
	 * @param totalFee
	 *            费用，单位为分
	 * @param openid
	 *            用户的openid
	 * @param productId
	 *            产品ID，是系统中未支付定单的ID，如果存钱，保持为空
	 * @return 处理结果
	 */
	private AjaxResult dealLittleProcPay(String attach, 
			String body, 
			String totalFee,
			String openid,
			String productId,String systemType) {
		AjaxResult result = new AjaxResult();
		
		String ip = IpKit.getRealIp(request);
		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}
		log.debug("ip={}",ip);
		String outTradeNo = this.stringUtil.getGuid();
		//WxPayApiConfig config = this.getApiConfig();
		WxPayApiConfig config = WxPayApiConfigKit.getWxPayApiConfig();

		Map<String, String> params = config.setAttach(attach)
				.setBody(body)
				.setSpbillCreateIp(ip)
				.setTotalFee(totalFee)
				.setOpenId(openid)
				.setProductId(productId)
				.setTradeType(WxPayApi.TradeType.JSAPI)
				.setNotifyUrl(this.notifyUrl)
				.setOutTradeNo(outTradeNo)
				.build();

		for (String key : params.keySet()) {
			log.info("{}={}", key, params.get(key));
		}
		UnifiedOrder order = new UnifiedOrder();
		order.setOutTradeNo(outTradeNo);
		order.setOpenid(openid);
		order.setTotalFee(Long.parseLong(totalFee));
		order.setOrderTime(new Date());
		order.setOrderState(OrderStateConst.New.getCode());
		order.setSpbillCreateIp(ip);
		order.setSystemType(systemType);
		order.setSoftwareType(SoftwareTypeConst.WxLP.getMessage());
		order.setPayType(PayTypeConst.Weixin.getMessage());
		order.setBody(body);
		order.setAttach(attach);
		//log.debug("productId={}.",productId);
		if(StringUtils.isNotEmpty(productId) && !"null".equals(productId)){
			order.setProductId(Long.parseLong(productId));
		}
		this.unifiedOrderService.insertEntity(order);

		String xmlResult = WxPayApi.pushOrder(false, params);

		log.info(xmlResult);
		Map<String, String> resultMap = PaymentKit.xmlToMap(xmlResult);

		String return_msg = resultMap.get("return_msg");
		if (!PaymentKit.codeIsOK(resultMap.get("return_code")) ||!PaymentKit.codeIsOK(resultMap.get("result_code")) ) {
			result.addError(return_msg);
			
			order.setRemark(return_msg);
			order.setOrderState(OrderStateConst.Fail.getCode());
			this.unifiedOrderService.updateEntity(order);
			
			log.error("生成统一定单失败：{}",return_msg);

			return result;
		}
		//定单生成成功。
		order.setOrderState(OrderStateConst.Success.getCode());
		this.unifiedOrderService.updateEntity(order);
		
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
		String prepay_id = resultMap.get("prepay_id");
		// 封装调起微信支付的参数https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appId", WxPayApiConfigKit.getWxPayApiConfig().getAppId());
		packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
		packageParams.put("nonceStr", System.currentTimeMillis() + "");
		//packageParams.put("nonceStr", this.stringUtil.getGuid());
		packageParams.put("package", "prepay_id=" + prepay_id);
		packageParams.put("signType", "MD5");
		String packageSign = PaymentKit.createSign(packageParams,
				WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey());
		packageParams.put("paySign", packageSign);

		String jsonStr = JSON.toJSONString(packageParams);
		log.debug("返回小程序支付的参数:" + jsonStr);
		result.success(packageParams);
		return result;
	}

	/**
	 * 生成统一定单，返回小程序可调起支付界面用的参数(测试用)。
	 * 
	 * @author wallimn，2018年9月22日 下午3:15:18
	 * @return 处理结果
	 */
	@RequestMapping(value = "/lppaytest", method = { RequestMethod.POST, RequestMethod.GET })
	public AjaxResult LittleProcPayTest() {
		
		return this.dealLittleProcPay("IJPay 测试  -By Javen", "IJPay 小程序支付测试  -By Javen", "1", "openid", "",SystemTypeConst.android.getMessage());
		
	}


	/**
	 * 测试参数
	 * @author wallimn，2018年9月25日 下午9:35:09
	 * @param attach
	 * @param body
	 * @param money
	 * @param productId
	 * @param openid
	 * @return
	 */
	@RequestMapping(value="/testparam", method = { RequestMethod.POST, RequestMethod.GET })
	public String testParam(
			@RequestParam(defaultValue="",required=false) String attach,
			@RequestParam(defaultValue="",required=false) String body, 
			@RequestParam(required=true) Long money,
			@RequestParam(required=false) Long productId, 
			@RequestParam(required=true) String openid){
		return attach+"---"+body+"---"+money+"--productId="+productId;
	}
	/**
	 * 生成统一定单，返回小程序可调起支付界面用的参数。
	 * 
	 * @param attach
	 *            订单附件
	 * @param body
	 *            订单内容
	 * @param money
	 *            费用，单位为分
	 * @param openid
	 *            用户的openid
	 * @param productId
	 *            产品ID，是系统中未支付定单的ID，如果存钱，保持为空
	 * @return 处理结果，其中code，0表示成功，1表示失败
	 */
	@RequestMapping(value="/lppay", method = { RequestMethod.POST, RequestMethod.GET })
	public AjaxResult LittleProcUserPay(
			@RequestParam(required=true) String attach,
			@RequestParam(required=true) String body, 
			@RequestParam(required=true) Long money,
			@RequestParam(required=false) Long productId, 
			@RequestParam(required=true) String openid,
			@RequestParam(required=false) String systemType) {
		if("undefined".equals(openid)){
			AjaxResult ar = new AjaxResult();
			ar.addError("openid不合法。");
			return ar;
		}
		String sMoney = String.valueOf(money);
		String sProductId = null;
		if(productId!=null){
			sProductId = String.valueOf(productId);
			String check = this.chargeService.isCostCanPay(productId, money);
			if(check!=null){
				AjaxResult ar = new AjaxResult();
				ar.addError(check);
				return ar;
			}
		}
		return this.dealLittleProcPay(attach, body, sMoney, openid, sProductId,systemType);
	}

	/**
	 * 付款成功回调，由微信负责回调
	 * 
	 * @author wallimn，2018年9月22日 下午2:37:06
	 * @param request
	 * @return 返回处理结果
	 */
	@RequestMapping(value = "/paynotify", method = { RequestMethod.POST, RequestMethod.GET })
	public String payNotify() {
		// 支付结果通用通知文档:
		// https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
		log.debug("微信回调：");
		String xmlMsg = HttpKit.readData(request);
		if(StringUtils.isEmpty(xmlMsg)){
			log.error("通知内容为空");
			return "通信内容为空";
		}
		log.debug("回调内容：{}" , xmlMsg);
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
		// String appid = params.get("appid");
		// //商户号
		// String mch_id = params.get("mch_id");
		String result_code = params.get("result_code");
		// String openId = params.get("openid");
		// //交易类型
		// String trade_type = params.get("trade_type");
		// //付款银行
		// String bank_type = params.get("bank_type");
		// //现金支付金额
		// String cash_fee = params.get("cash_fee");
		// // 微信支付订单号
		// String transaction_id = params.get("transaction_id");
		// // 支付完成时间，格式为yyyyMMddHHmmss
		// String time_end = params.get("time_end");
		// 总金额
		String totalFee = params.get("total_fee");
		// 商户订单号
		String outTradeNo = params.get("out_trade_no");
		String openid = params.get("openid");
		// 商品ID
		//商品ID好像不会返回
		
		UnifiedOrder order = this.unifiedOrderService.selectEntityById(outTradeNo);
		log.debug("支付回调，totalFee={},outTradeNo={},productId={}",totalFee,outTradeNo,params.get("product_id"));
		if(order!=null){
			//只处理成功下单的定单
			if(OrderStateConst.Success.getCode().equals(order.getOrderState())){
				Long productId = order.getProductId();
				order.setFinishTime(new Date());
				order.setOrderState(OrderStateConst.Paid.getCode());
				this.unifiedOrderService.updateEntity(order);
				if (productId!=null) {
					log.info("用户支付定单：{}", openid);
					Long costId = productId;
					Long money = Long.parseLong(totalFee);
					if(this.chargeService.isCostCanPay(costId, money)==null	){
						this.chargeService.payUserCost(openid, costId,money , outTradeNo);
					}
					else{
						log.error("支付定单出错，存在入本人账户！");
						this.chargeService.increaseUserMoney(openid, money, outTradeNo);
					}
					// 清除缓存
					this.userCostService.deleteUserCostById(costId);
				} else {
					log.info("用户账号存款：{}", openid);
					this.chargeService.increaseUserMoney(openid, Long.parseLong(totalFee), outTradeNo);
				}
			}
			else{
				log.warn("订单已经回调过，outTradeNo={}",outTradeNo);
			}
		}
		else{
			log.error("统一定单不存在：{}",outTradeNo);
		}
		// 进行支付成功的处理

		///////////////////////////// 以下是附加参数///////////////////////////////////

		String attach = params.get("attach");
		// String fee_type = params.get("fee_type");
		// String is_subscribe = params.get("is_subscribe");
		// String err_code = params.get("err_code");
		// String err_code_des = params.get("err_code_des");
		// 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
		// 避免已经成功、关闭、退款的订单被再次更新
		if (PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())) {
			if (("SUCCESS").equals(result_code)) {
				// 更新订单信息
				log.warn("更新订单信息:" + attach);
				// 发送通知等
				Map<String, String> xml = new HashMap<String, String>();
				xml.put("return_code", "SUCCESS");
				xml.put("return_msg", "OK");
				return PaymentKit.toXml(xml);
			}
			else{
				log.error("微信回调：{}",result_code);
			}
		}

		return null;
	}

}
