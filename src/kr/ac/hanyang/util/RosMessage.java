package kr.ac.hanyang.util;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class RosMessage {
		
	private Header header;
	private SocialContext social_context;
	private static JSONParser jsonParser = new JSONParser();
	private static ObjectMapper mapper = new ObjectMapper();

	public RosMessage() {
		header = new Header();
		social_context = new SocialContext();
	}
	
	// 로스메시지 객체 생성자
	public RosMessage(Header header, SocialContext social_context) {
		this.header = header;
		this.social_context = social_context;
	}
	
	/**
	 * String 타입의 로스 메시지를 받아 파싱하여 이 객체의 header 와 social_context 를 초기화하는 메서드 
	 * @param msg : String 타입의 로스 메시지
	 */
	public void parse(String msg) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = (JSONObject) jsonParser.parse(msg);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		this.header = new Header((JSONObject) jsonObject.get("header"));
		this.social_context = new SocialContext((JSONObject) jsonObject.get("social_context"));
		
	}
	
	/**
	 * 이 객체의 내용을 보기쉽게 출력하기 위한 메서드 
	 * @return 
	 */
	public String prettify() {
		
		String msg = "";
		try {
			msg = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.toJsonObject());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	
	public Header getHeader() {
		return this.header;
	}
	public SocialContext getSocialContext() {
		return this.social_context;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJsonObject() {
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("header", header);
		jsonObject.put("social_context", social_context);
		
		return jsonObject ;
	}
	
}