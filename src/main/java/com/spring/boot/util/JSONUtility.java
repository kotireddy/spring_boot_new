package com.spring.boot.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtility {

	private static ObjectMapper objectMapper = null;
	private static String jsonResult = "";
	
	static {
		objectMapper = new ObjectMapper();
	}
	
	/**
	 * Convert Java Object to Json
	 * @param object
	 * @return
	 */
	public static String convertJavaToJson(Object object) {
		try {
			jsonResult = objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	/**
	 * Convert JSON String to java Object
	 * @param jsonString
	 * @param clazzzzz
	 * @return
	 */
	public static <T> T convertJsonToJava(String jsonString, Class<T> clazzzzz) {
		T jsonResult = null;
		try {
			jsonResult = objectMapper.readValue(jsonString, clazzzzz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	/**
	 * Converting Map Data To Json String
	 * @param map
	 * @return
	 */
	public static <T,K,V> String convertMapToJson(Map<K, V> map) {
		try {
			jsonResult = objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	
	public static <T, K,V> Map<K, V> convertJSONStringToMap(String jsonString, 
														TypeReference<Map<K, V>> reference){
		Map<K, V> t = null;
		try {
			t = objectMapper.readValue(jsonString, reference);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	
	
	
	
	
	
	
}
