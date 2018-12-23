package com.wallimn.iteye.sp.asset.bus.charge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ConvertUtil {
	
	private static ObjectMapper objectMapper;
	static{
		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	public static Long toLong(Object value){
		if(value==null)return null;
		else if (value instanceof Long)return (Long)value;
		else{
			return Long.valueOf(value.toString());
		}
	}
	public static Integer toInteger(Object value){
		if(value==null)return null;
		else if (value instanceof Integer)return (Integer)value;
		else{
			return Integer.valueOf(value.toString());
		}
	}
	public static String toString(Object value){
		if(value==null)return null;
		else return value.toString();
	}
	
	public static String objectToJSON(Object value){
		//Assert.notNull(value,"值为空");
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T jsonToObject(String json,Class<T> valueType){
		try {
			return objectMapper.readValue(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ascToString(Object value){
		if(value instanceof Long)return String.valueOf((char)(long)value);
		else if (value instanceof Integer)return String.valueOf((char)(int)value);
		else if (value instanceof Character)return String.valueOf((char)value);
		else{
			int v = Integer.parseInt(value.toString());
			return String.valueOf((char)v);
		}
	}
}
