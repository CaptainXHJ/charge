package com.wallimn.iteye.sp.asset.bus.weixin.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallimn.iteye.sp.asset.bus.charge.model.User;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserService;
import com.wallimn.iteye.sp.asset.bus.weixin.model.WxUser;
import com.wallimn.iteye.sp.asset.bus.weixin.service.WxUserService;
import com.wallimn.iteye.sp.asset.common.config.GlobalConst;
import com.wallimn.iteye.sp.asset.common.util.AesUtil;
import com.wallimn.iteye.sp.asset.common.util.HttpUtil;
/**
 * 这几个参数有时间要把它们与支付的参数合并。同样的东西写了两个地方。
 * @author wallimn，2018年9月28日 上午12:55:44
 *
 */
@RestController
@RequestMapping("/api/01/wxsys")
public class WeixinController {

	private static Logger log = LoggerFactory.getLogger(WeixinController.class);

	@Value("${wx.appId}")
	private String appId;

	@Value("${wx.appSecret}")
	private String appSecret;

	@Value("${wx.grantType}")
	private String grantType;
	// wx.grantType=authorization_code

	@Value("${wx.requestUrl}")
	private String requestUrl;

	@Autowired
	WxUserService wxUserService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest request;
	// wx.requestUrl=https://api.weixin.qq.com/sns/jscode2session

	/**
	 * 使用code换取openid，换取后 要把openid写入session吗？
	 * 感觉不太保险，还是不写了。会存在session过期的情况。
	 * 
	 * <br>
	 * 
	 * @param code
	 * @return <br>
	 *         时间：2018年7月27日 下午4:51:28，联系：54871876@qq.com
	 */
	@RequestMapping("/session")
	public Map<String, Object> getSession(@RequestParam(required = true) String code) {

		// "TzOTculTkk1bBA1rsXQs2dvFq5NuIEfkJIfsr1YlnuB6xLhWr7ocuBc7IcgeFN0xQ/soPfZGSlo3P33AfkvetmJ8i7lJqLEFIN6R6tAUtqjbFQ9kQwEVfS1enzCjFfLVCL2d6qz+9cqBmx/MmtKCevnyXX+aYKCyqmiQOhzXo1/bb2gPgCa3+HA5mz3lORou28fPH1y/MiFfo4PLuGQKglxlrGiCibdAW6+m4DS+9FJ6XTWwvkK9XnHBxoZHfC63vWyIHxT0oxLWG6cK091l/Jzk3MKSuBvx1gLpxmGBOIuU/DQ2Xtm+uHASQJMhaXD91pB+gqAsfqulhYqwSv355Bb/VHZrYiNy9b5L3+AaAmI9XVqJhs/n1eQuSYgEWmN837IgrOqBoxO74Tv+qWsFL3U6Gdvu0G+mWg32sA6ae2d+vyJgzwexaZej4/htnmgVS9foEMt1I2p0KJetrC6Bt1KfUupAZa+TJf/qqBRebl4="
		Map<String, Object> result = this.getSessionByCode(code);
		String openid = (String) result.get("openid");
		//this.request.getSession().getId()
		//返回用户的SessionID，供前台使用。
		User user = this.userService.selectUserByOpenid(openid);
		this.request.getSession().setAttribute(GlobalConst.loginedUserKey, user);
		result.put("sessionId", this.request.getSession().getId());
		result.put("registered", user!=null);
		//result.put("user", user);
		return result;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getSessionByCode(String code) {
		String url = this.requestUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type="
				+ grantType;
		// 发送请求
		String data = HttpUtil.get(url);
		log.debug("请求地址：{}", url);
		log.debug("请求结果：{}", data);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> json = null;
		try {
			json = mapper.readValue(data, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 形如{"session_key":"6w7Br3JsRQzBiGZwvlZAiA==","openid":"oQO565cXXXXXEvc4Q_YChUE8PqB60Y"}的字符串
		return json;
	}

	/**
	 * 解码，获取unionid，这个函数没有完成。
	 * 
	 * <br>
	 * 
	 * @param encryptedData
	 * @param iv
	 * @param code
	 * @return <br>
	 *         时间：2018年7月27日 下午4:50:54，联系：54871876@qq.com
	 */

	@RequestMapping(value = "/decodeUserInfo", method = RequestMethod.POST)
	public Map<String, Object> decodeUserInfo(String encryptedData, String iv, String code) {

		Map<String, Object> session = this.getSessionByCode(code);
		// 获取会话密钥（session_key）
		String session_key = (String) session.get("session_key");
		// 用户的唯一标识（openid）
		// String openid = (String) session.get("openid");

		//////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
		try {
			String result = AesUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
			if (null != result && result.length() > 0) {
				ObjectMapper mapper = new ObjectMapper();
				@SuppressWarnings("unchecked")
				Map<String, Object> map = mapper.readValue(result, Map.class);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public WxUser userLogin(@RequestBody WxUser user) {
		String openid = user.getOpenid();
		WxUser result = null;
		WxUser myUser = this.wxUserService.selectUserByOpenid(openid);
		if (myUser != null) {
			myUser.setAvatarUrl(user.getAvatarUrl());
			myUser.setNickName(user.getNickName());
			this.wxUserService.updateUser(myUser);
			result = myUser;
			log.debug("用户再次登录：{}", myUser.getNickName());
		} else {
			this.wxUserService.insertUser(user);
			log.debug("用户首次登录：{}", user.getNickName());
			result = user;
		}
		return result;
	}

	@RequestMapping(value = "/notice", method = RequestMethod.POST)
	public String postNotice(@RequestBody Map<String, Object> map) {
		log.debug("title={},openid={}", map.get("title"), map.get("openid"));
		return "ok";
	}

	@RequestMapping(value = "/test")
	public String getTest() {
		String referer = this.request.getHeader("Referer");
		log.debug("请求地址：{}", referer);
		return "the result of test";
	}

	@Value("${wx.uploadPath}")
	private String uploadPath;

	private String getFileType(String fileN) {
		if (StringUtils.isEmpty(fileN))
			return "";
		int pos = fileN.lastIndexOf(".");
		return fileN.substring(pos);
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public List<String> upload(HttpServletRequest request, @RequestParam("file") MultipartFile[] files) {
		log.info("上传测试");
		List<String> result = new LinkedList<String>();
		String fileN = null;
		// 多文件上传
		if (files != null && files.length >= 1) {
			BufferedOutputStream bw = null;
			try {
				String fileName = files[0].getOriginalFilename();
				// 判断是否有文件(实际生产中要判断是否是音频文件)
				if (StringUtils.isNoneBlank(fileName)) {
					// 创建输出文件对象
					fileN = UUID.randomUUID().toString() + this.getFileType(fileName);
					File outFile = new File(uploadPath + fileN);
					// 拷贝文件到输出文件对象
					FileUtils.copyInputStreamToFile(files[0].getInputStream(), outFile);
					result.add(fileN);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (bw != null) {
						bw.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
