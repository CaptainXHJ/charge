package com.wallimn.iteye.sp.asset.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 用于在程序端生成GUID。用于在使用数据库的机制不太方便的时候。
	 * @return
	 *<br>作者：wallimn，时间：2018年3月28日 下午4:51:23
	 */
	public String getGuid(){
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
	
	public String getToday(){
		return DATE_FORMAT.format(new Date());
	}
	/**
	 * 用于转化pascal字符串为下划分分隔的字符串，
	 * 也就是showOrder转化为show_order
	 * @param pascalString
	 * @return
	 *<br>作者：wallimn，时间：2018年5月8日 下午10:28:18
	 */
	public String getDbField(String pascalString){
		if(pascalString==null)return null;
		String result = pascalString;
		Pattern pattern = Pattern.compile("([A-Z])");
		Matcher matcher = pattern.matcher(pascalString);
		while(matcher.find()){
			result = result.replaceFirst(matcher.group(1), "_"+matcher.group(1).toLowerCase());
		}	
		return result;
	}
}
