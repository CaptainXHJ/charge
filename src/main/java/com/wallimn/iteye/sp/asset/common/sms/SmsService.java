package com.wallimn.iteye.sp.asset.common.sms;
/**
 * 短信服务接口
 * @author wallimn，2018年9月26日 下午3:07:55
 *
 */
public interface SmsService {
	public boolean send(String mobile, String json);
	
	public SmsRecord putSmsRecord(SmsRecord record);
	
	public SmsRecord getSmsRecord(String openid);
	
	public void evictSmsRecode(String openid);
	public void evictSmsRecode();
	
	public String getCheckCode(int len);
}
