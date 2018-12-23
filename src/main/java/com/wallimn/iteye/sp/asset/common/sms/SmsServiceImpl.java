package com.wallimn.iteye.sp.asset.common.sms;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 阿里云短信接口
 * @author wallimn，2018年9月26日 下午3:08:20
 *
 */
@Service("SmsService")
@CacheConfig(cacheNames = {"SmsRecord"})
public class SmsServiceImpl implements SmsService {
	private Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
	
	@Value("${sms.accessKeyId}")
	private String accessKeyId;
	
	@Value("${sms.accessKeySecret}")
	private String accessKeySecret;
	
	@Value("${sms.connectTimeout}")
	private String connectTimeout;
	
	@Value("${sms.readTimeout}")
	private String readTimeout;
	
	@Value("${sms.templateId}")
	private String templateId;//内容：验证码${code}，您正注册成为新用户，感谢您的支持！
	
	@Value("${sms.signName}")
	private String signName;

	/**
	 * 发送短消息
	 * @author wallimn，2018年9月26日 下午2:49:38
	 * @param mobile
	 * @param json
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean send(String mobile, String code) {
		// 设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", this.connectTimeout);
		System.setProperty("sun.net.client.defaultReadTimeout", this.readTimeout);
		// 初始化ascClient需要的几个参数
		final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
		// 替换成你的AK
		//final String accessKeyId = "yourAccessKeyId";// 你的accessKeyId,参考本文档步骤2
		//final String accessKeySecret = "yourAccessKeySecret";// 你的accessKeySecret，参考本文档步骤2
		// 初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", this.accessKeyId, this.accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
			request.setPhoneNumbers(mobile);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(this.signName);
			// 必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
			request.setTemplateCode(this.templateId);
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			//request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
			request.setTemplateParam("{\"code\":\""+code+"\"}");
			// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
			// request.setSmsUpExtendCode("90997");
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId(mobile);
			// 请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				// 请求成功
				return true;
			}
			else{
				log.error("发送短信出错：{}",sendSmsResponse.getMessage());
			}
		} catch (Exception e) {
			log.error("发送短信异常：{}",e.getMessage());
			e.printStackTrace();
			
		}
		return false;
	}
	
	private static final String[] availableNumber = new String[]{"2","6","8","9"};
	private static final int numCount = availableNumber.length;
	

	@Override
	//@CachePut(value="SmsRecord", key="#record.openid")
	@CachePut(key="#record.openid")
	public SmsRecord putSmsRecord(SmsRecord record) {
		log.debug("缓存SmsRecord，openid={}",record.getOpenid());
		return record;
	}

	@Override
	@Cacheable(key="#openid")
	public SmsRecord getSmsRecord(String openid) {
		log.debug("读取SmsRecord，openid={}",openid);
		return null;
	}

	@Override
	@CacheEvict(allEntries = true)
	public void evictSmsRecode() {
		log.debug("清除SmsRecord缓存");
	}

	@Override
	@CacheEvict(key="#openid")
	public void evictSmsRecode(String openid) {
		log.debug("清除SmsRecord，openid={}",openid);
	}

	@Override
	public String getCheckCode(int len) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for(int i=0;i<len;i++){
			sb.append(availableNumber[random.nextInt(numCount)]);
		}
		return sb.toString();
	}

}
